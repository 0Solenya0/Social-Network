package Server.models.Filters;

import Server.models.Exceptions.ConnectionException;
import Server.models.Fields.NotificationType;
import Server.models.Notification;
import Server.models.Relation;
import Server.models.User;

import java.util.ArrayList;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class NotificationFilter extends ModelFilter<Notification> {

    public NotificationFilter() throws ConnectionException {
        super(Notification.class);
    }

    public NotificationFilter getByType(NotificationType t) {
        customFilter(notification -> notification.type == t);
        return this;
    }
    public NotificationFilter getByUser1(int user1) {
        customFilter(notification -> notification.user1 == user1);
        return this;
    }
    public NotificationFilter getByUser2(int user2) {
        customFilter(notification -> notification.user2 == user2);
        return this;
    }
    public Notification getByTwoUser(int user1, int user2) {
        return get(notification -> notification.user1 == user1 && notification.user2 == user2);
    }
    public NotificationFilter getEnabled() {
        customFilter(notification -> {
            try {
                return User.get(notification.user1).isEnabled && User.get(notification.user2).isEnabled;
            }
            catch (Exception e) {
                return false;
            }
        });
        return this;
    }
}
