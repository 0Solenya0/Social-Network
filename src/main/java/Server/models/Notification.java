package Server.models;

import Server.models.Exceptions.ConnectionException;
import Server.models.Exceptions.ValidationException;
import Server.models.Fields.NotificationType;
import Server.models.Fields.RelType;
import Server.models.Filters.NotificationFilter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Notification extends Model {
    private static final Logger logger = LogManager.getLogger(Relation.class);
    public static final String datasrc = "./db/" + Notification.class.getName();

    public String getdatasrc() {
        return datasrc;
    }
    private int user1, user2;
    protected String message;
    private NotificationType type;

    public int getSender() {
        return user1;
    }

    public NotificationType getType() {
        return type;
    }

    public int getReceiver() {
        return user2;
    }

    public Notification(int user1, int user2, String content) {
        super();
        this.user1 = user1;
        this.user2 = user2;
        this.type = NotificationType.INFO;
        this.message = content;
    }
    public Notification(int user1, int user2, NotificationType type) {
        super();
        this.user1 = user1;
        this.user2 = user2;
        this.type = type;
        this.message = "";
    }

    public void accept() throws ConnectionException {
        try {
            (new Relation(user1, user2, RelType.FOLLOW)).save();
            (new Notification(0, user1, User.get(user2).username + " has accepted your request")).save();
            this.delete();
        }
        catch (ValidationException e) {
            logger.warn("Unexpected validation failed while accepting a request - " + e.getMessage());
        }
    }
    public void refuse() throws ConnectionException {
        try {
            (new Notification(0, user1, User.get(user2).username + " has refused your request")).save();
            this.delete();
        }
        catch (ValidationException e) {
            logger.warn("Failed while refusing notification - " + e.getMessage());
        }
    }
    public void silentRefuse() throws ConnectionException {
        this.delete();
    }

    public String getMessage() throws ConnectionException {
        if (type == NotificationType.INFO)
            return message;
        else if (type == NotificationType.REPORT)
            return "You have been reported please BEHAVE...";
        else
            return User.get(user1).username + " has requested to follow you";
    }
    public String getMessageForSender() throws ConnectionException {
        if (type == NotificationType.INFO)
            return message;
        else
            return "your request to " + User.get(user2).username + " is pending";
    }

    /** Must be in every model section **/
    public static Notification get(int id) throws ConnectionException {
        return loadObj(id, Notification.class);
    }
    public static NotificationFilter getFilter() throws ConnectionException {
        return new NotificationFilter();
    }

    public void isValid() throws ValidationException {
        if (user1 > User.getLastId(User.class) && user2 > User.getLastId(User.class))
            throw new ValidationException("user", "User", "User does not exist");
    }
}
