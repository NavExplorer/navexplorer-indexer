package com.navexplorer.indexer.communityfund.factory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.navexplorer.indexer.communityfund.Dto.StrDZeel;
import com.navexplorer.indexer.communityfund.exception.InvalidAnonDestinationException;
import com.navexplorer.indexer.communityfund.exception.InvalidVersionException;
import com.navexplorer.indexer.communityfund.exception.StrDZeelValidationException;
import com.navexplorer.library.block.entity.BlockTransaction;
import com.navexplorer.library.communityfund.entity.CommunityFundProposal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.io.IOException;
import java.util.Set;

@Service
public class CommunityFundProposalFactory {
    private static final Logger logger = LoggerFactory.getLogger(CommunityFundProposalFactory.class);

    public CommunityFundProposal createProposal(BlockTransaction transaction) {
        if (!transaction.getVersion().equals(4)) {
            throw new InvalidVersionException("Community fund proposals must have a transaction version of 4");
        }

        StrDZeel strDZeel = hydrateStrDZeel(transaction);

        CommunityFundProposal proposal = new CommunityFundProposal();
        proposal.setHeight(transaction.getHeight().longValue());
        proposal.setTransaction(transaction.getHash());
        proposal.setAmount(strDZeel.getAmount());
        proposal.setAddress(strDZeel.getAddress());
        proposal.setDeadline(strDZeel.getDeadline().longValue());
        proposal.setDescription(strDZeel.getDescription());

        return proposal;
    }

    private StrDZeel hydrateStrDZeel(BlockTransaction transaction) {
        StrDZeel strDZeel;

        try {
            ObjectMapper mapper = new ObjectMapper();
            strDZeel = mapper.readValue(transaction.getAnonDestination(), StrDZeel.class);
            validateStrDZeel(strDZeel);

        } catch (IOException|StrDZeelValidationException e) {
            throw new InvalidAnonDestinationException(String.format(
                    "Could not hydrate the strDZeel at height(%s): %s",
                    transaction.getHeight(),
                    transaction.getAnonDestination()
            ), e);
        }

        return strDZeel;
    }

    private void validateStrDZeel(StrDZeel strDZeel) {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<StrDZeel>> violations = validator.validate(strDZeel);
        if (!violations.isEmpty()) {
            violations.forEach(v -> logger.error(String.format("%s %s", v.getPropertyPath(), v.getMessage())));
            throw new StrDZeelValidationException("Invalid StrDZeel Data from anon-destination");
        }
    }
}
