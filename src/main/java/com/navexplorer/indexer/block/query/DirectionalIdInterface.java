package com.navexplorer.indexer.block.query;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;

public interface DirectionalIdInterface {
    default CriteriaDefinition directionalIdQuery(String direction, String offset) {
        try {
            if (direction.equals("from")) {
                return Criteria.where("_id").lt(new ObjectId(offset));
            } else if (direction.equals("to")) {
                return Criteria.where("_id").gt(new ObjectId(offset));
            }
        } catch (IllegalArgumentException e) {
            //
        }

        return null;
    }
}
