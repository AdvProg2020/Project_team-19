package model;

import java.util.UUID;

public class Comment {
    enum  VerifiedState{
        VERIFIED,DECLINED,IN_PROCESS
    }

    boolean isBought;
    Customer commenter;
    String commentString;
    String commentId;

    public Comment(boolean isBought, Customer commenter, String commentString) {
        commentId = UUID.randomUUID().toString();
        this.isBought = isBought;
        this.commenter = commenter;
        this.commentString = commentString;
    }

}
