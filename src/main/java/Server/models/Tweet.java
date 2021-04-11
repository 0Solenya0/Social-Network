package Server.models;

import Server.models.Exceptions.ConnectionException;
import Server.models.Exceptions.ValidationException;
import Server.models.Filters.TweetFilter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.TreeSet;

public class Tweet extends Model {
    private static final Logger logger = LogManager.getLogger(Tweet.class);

    private int parentTweet, reTweet;
    private TreeSet<Integer> likes;
    private int author;
    private String content;

    public TreeSet<Integer> getLikes() {
        return likes;
    }

    public int getLikeCount() {
        return likes.size();
    }

    public int getParentTweetId() {
        return parentTweet;
    }
    public void setParentTweetId(int parentTweet) {
        this.parentTweet = parentTweet;
    }

    public int getReTweetId() {
        return reTweet;
    }
    public void setReTweetId(int reTweet) {
        this.reTweet = reTweet;
    }

    public String getContent() throws ConnectionException {
        if (reTweet != 0)
            return content + "\nRetweeted from " + Tweet.get(reTweet).getAuthor().username  + ":\n" + Tweet.get(reTweet).content;
        return content;
    }
    public int getAuthorId() {
        return author;
    }
    public User getAuthor() throws ConnectionException {
        return User.get(author);
    }

    public Tweet(User author, String text) {
        super();
        likes = new TreeSet<>();
        this.author = author.id;
        this.content = text;
    }

    public void like(int user) throws ConnectionException {
        likes.add(user);
        try {
            save();
        }
        catch (ValidationException e) {
            logger.warn("Like failed - " + e.getMessage());
        }
    }
    public void disLike(int user) throws ConnectionException {
        likes.remove(user);
        try {
            save();
        }
        catch (ValidationException e) {
            logger.warn("DisLike failed - " + e.getMessage());
        }
    }

    /** Must be in every model section **/
    public static Tweet get(int id) throws ConnectionException {
        return loadObj(id, Tweet.class);
    }
    public static TweetFilter getFilter() throws ConnectionException {
        return new TweetFilter();
    }

    public void isValid() throws ValidationException {
        if (author == 0)
            throw new ValidationException("Author", "Tweet", "Tweet author is not valid.");
        if (content == null || content.isEmpty())
            throw new ValidationException("Content", "Tweet", "Tweet doesn't have any content.");
    }
}
