package apps.tweet.model;

import apps.auth.model.User;
import model.Model;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.TreeSet;

public class Tweet extends Model {
    private static final Logger logger = LogManager.getLogger(Tweet.class);

    private int parentTweet, reTweet;
    private TreeSet<Integer> likes;
    private int author;
    private String content;

    public Tweet(User author, String text) {
        super();
        likes = new TreeSet<>();
        this.author = author.id;
        this.content = text;
    }

    public int getParentTweet() {
        return parentTweet;
    }

    public void setParentTweet(int parentTweet) {
        this.parentTweet = parentTweet;
    }

    public int getReTweet() {
        return reTweet;
    }

    public void setReTweet(int reTweet) {
        this.reTweet = reTweet;
    }

    public int getAuthor() {
        return author;
    }

    public void setAuthor(int author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }
}
