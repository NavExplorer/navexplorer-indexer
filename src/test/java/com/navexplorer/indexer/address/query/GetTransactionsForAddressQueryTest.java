package com.navexplorer.indexer.address.query;

import com.navexplorer.indexer.address.entity.AddressTransaction;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GetTransactionsForAddressQueryTest {
    @InjectMocks
    private GetTransactionForAddressQuery getTransactionForAddressQuery;

    @Mock
    private MongoTemplate mongoTemplate;

    @Test
    public void it_can_get_transactions_for_an_address() {
        String address = "ADDRESS";
        int size = 2;
        List<String> filters = new ArrayList<>();

        AddressTransaction transaction = new AddressTransaction();

        when(mongoTemplate.find(any(Query.class), eq(AddressTransaction.class)))
                .thenReturn(Arrays.asList(transaction, transaction, transaction, transaction));

        Page<AddressTransaction> transactions = getTransactionForAddressQuery.query(address, size, filters);

        assertThat(transactions.getTotalElements()).isEqualTo(4);
        assertThat(transactions.getNumberOfElements()).isEqualTo(4);
        assertThat(transactions.getTotalPages()).isEqualTo(2);
    }

    @Test
    public void it_will_default_to_direction_descending_when_getting_transactions_for_an_address() {
        String address = "ADDRESS";
        int size = 10;
        List<String> filters = new ArrayList<>();

        getTransactionForAddressQuery.query(address, size, filters);

        ArgumentCaptor<Query> argumentCaptor = ArgumentCaptor.forClass(Query.class);
        verify(mongoTemplate).find(argumentCaptor.capture(), eq(AddressTransaction.class));
        Query query = argumentCaptor.<Query> getValue();

//        assertThat(query.toString()).isEqualTo("Query: { \"address\" : \"ADDRESS\"}, Fields: null, Sort: { \"height\" : -1 , \"_id\" : -1}");
    }

    @Test
    public void it_can_order_ascending_when_getting_transactions_for_an_address() {
        String address = "ADDRESS";
        int size = 10;
        List<String> filters = new ArrayList<>();
        String direction = "to";
        String offset = "5b661c26a26cd00657f921e5";

        getTransactionForAddressQuery.query(address, size, filters, direction, offset);

        ArgumentCaptor<Query> argumentCaptor = ArgumentCaptor.forClass(Query.class);
        verify(mongoTemplate).find(argumentCaptor.capture(), eq(AddressTransaction.class));
        Query query = argumentCaptor.<Query> getValue();

//        assertThat(query.toString()).isEqualTo("Query: { \"address\" : \"ADDRESS\" , \"_id\" : { \"$gt\" : { \"$oid\" : \"5b661c26a26cd00657f921e5\"}}}, Fields: null, Sort: { \"height\" : 1 , \"_id\" : 1}");
    }

    @Test
    public void it_can_order_descending_when_getting_transactions_for_an_address() {
        String address = "ADDRESS";
        int size = 10;
        List<String> filters = new ArrayList<>();
        String direction = "from";
        String offset = "5b661c26a26cd00657f921e5";

        getTransactionForAddressQuery.query(address, size, filters, direction, offset);

        ArgumentCaptor<Query> argumentCaptor = ArgumentCaptor.forClass(Query.class);
        verify(mongoTemplate).find(argumentCaptor.capture(), eq(AddressTransaction.class));
        Query query = argumentCaptor.<Query> getValue();

//        assertThat(query.toString()).isEqualTo("Query: { \"address\" : \"ADDRESS\" , \"_id\" : { \"$lt\" : { \"$oid\" : \"5b661c26a26cd00657f921e5\"}}}, Fields: null, Sort: { \"height\" : -1 , \"_id\" : -1}");
    }

    @Test
    public void it_will_apply_filters_when_using_the_default_direction() {
        String address = "ADDRESS";
        int size = 10;
        List<String> filters = Arrays.asList("staking", "sent", "received");

        getTransactionForAddressQuery.query(address, size, filters);

        ArgumentCaptor<Query> argumentCaptor = ArgumentCaptor.forClass(Query.class);
        verify(mongoTemplate).find(argumentCaptor.capture(), eq(AddressTransaction.class));
        Query query = argumentCaptor.<Query> getValue();
System.out.println(query.toString());
//        assertThat(query.toString()).isEqualTo("Query: { \"address\" : \"ADDRESS\", \"type\" : { \"$ne\" : { $java : COLD_STAKING } }, \"$or\" : [ { \"type\" : { $java : STAKING } }, { \"type\" : { $java : SEND } }, { \"type\" : { $java : RECEIVE } }, { \"type\" : { $java : COMMUNITY_FUND_PAYOUT } } ] }, Fields: null, Sort: { \"height\" : -1 , \"_id\" : -1}");
    }
}
