package model;

import controller.PersonController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.UUID;

public class Comment {


    enum VerifiedState {
        VERIFIED, DECLINED, IN_PROCESS
    }
    private HashMap<String, String> replies;
    private boolean isBought;
    private String commenterUsername;
    private String title;
    private String commentString;
    private String commentId;
    private LocalDateTime dateTime;
    private VerifiedState state;

    public Comment(boolean isBought, Customer commenter, String commentString, String title) {
        commentId = UUID.randomUUID().toString();
        this.replies = new HashMap<>();
        this.isBought = isBought;
        this.commenterUsername = commenter.getUsername();
        this.title = title;
        this.commentString = commentString;
    }

    public Customer getCommenter() {
        return (Customer) PersonController.getInstance().getPersonByUsername(commenterUsername);
    }

    public String getCommenterUsername() {
        return commenterUsername;
    }

    public boolean isBought() {
        return isBought;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getCommentString() {
        return commentString;
    }

    public String getTitle() {
        return title;
    }

    public VerifiedState getState() {
        return state;
    }

    public boolean isCommentVerified() {
        return state.equals(VerifiedState.VERIFIED);
    }

    public void setBought(boolean bought) {
        isBought = bought;
    }

    public void setReply(String username, String reply) {
        this.replies.put(username, reply);
        Person person = PersonController.getInstance().getPersonByUsername(commenterUsername);
      //  Database.saveToFile(person, Database.createPath(person.getType(), person.getUsername()));
    }

    public HashMap<String, String> getReply() {
        return replies;
    }
}
