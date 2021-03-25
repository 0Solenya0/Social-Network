package Server.models.Filters;

import Server.models.Exceptions.ConnectionException;
import Server.models.Fields.RelType;
import Server.models.Relation;
import Server.models.Tweet;
import Server.models.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class RelationFilter extends ModelFilter<Relation> {

    public RelationFilter() throws ConnectionException {
        super(Relation.class);
    }

    public Relation getByTwoUser(int user1, int user2) {
        return get(relation -> relation.user1 == user1 && relation.user2 == user2);
    }
    public RelationFilter getByUser1(int user1) {
        customFilter(relation -> relation.user1 == user1);
        return this;
    }
    public RelationFilter getByUser2(int user2) {
        customFilter(relation -> relation.user2 == user2);
        return this;
    }
    public RelationFilter getByType(RelType t) {
        customFilter(relation -> relation.type == t);
        return this;
    }
    public RelationFilter getEnabled() {
        customFilter(relation -> {
            try {
                return User.get(relation.user1).isEnabled() && User.get(relation.user2).isEnabled();
            }
            catch (Exception e) {
                return false;
            }
        });
        return this;
    }
}
