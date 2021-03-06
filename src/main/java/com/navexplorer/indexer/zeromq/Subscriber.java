package com.navexplorer.indexer.zeromq;

import com.navexplorer.indexer.block.indexer.BlockIndexer;
import com.navexplorer.indexer.communityfund.indexer.PaymentRequestIndexer;
import com.navexplorer.indexer.communityfund.indexer.ProposalIndexer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.zeromq.ZFrame;
import org.zeromq.ZMQ;
import org.zeromq.ZMsg;

@Service
public class Subscriber {
    private static final Logger logger = LoggerFactory.getLogger(Subscriber.class);

    @Value("${zeromq.address}")
    private String address;

    @Autowired
    BlockIndexer blockIndexer;

    @Autowired
    ProposalIndexer proposalIndexer;

    @Autowired
    PaymentRequestIndexer paymentRequestIndexer;

    public void run() {
        ZMQ.Context context = ZMQ.context(1);

        ZMQ.Socket subscriber = context.socket(ZMQ.SUB);
        subscriber.connect(address);
        subscriber.subscribe("hashblock".getBytes());

        logger.info(String.format("Subscribed to %s... Waiting for messages.", address));

        while (true) {
            ZMsg zMsg = ZMsg.recvMsg(subscriber);

            int messageNumber = 0;
            String messageType = "";

            for (ZFrame f: zMsg) {
                byte[] bytes = f.getData();

                switch(messageNumber) {
                    case 0:
                        messageType = new String(bytes);
                        break;
                    case 1:
                        if (messageType.equals("hashblock")) {
                            blockIndexer.indexAllBlocks();
                            proposalIndexer.updateAllProposals();
                            paymentRequestIndexer.updateAllPaymentRequests();
                        }
                        break;
                }
                messageNumber++;
            }
        }
    }
}
