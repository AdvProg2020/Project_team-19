package model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Comment {


    enum VerifiedState {
        VERIFIED, DECLINED, IN_PROCESS
    }

    boolean isBought;
    Customer commenter;
    String title;
    String commentString;
    String commentId;
    LocalDateTime dateTime;
    VerifiedState state;

    public Comment(boolean isBought, Customer commenter, String commentString, String title) {
        commentId = UUID.randomUUID().toString();
        this.isBought = isBought;
        this.commenter = commenter;
        this.title = title;
        this.commentString = commentString;
    }

    public Customer getCommenter() {
        return commenter;
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
}
