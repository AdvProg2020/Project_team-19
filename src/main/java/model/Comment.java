package model;

import controller.Database;

import java.io.IOException;
import java.util.UUID;

public class Comment {
    enum  VerifiedState{
        VERIFIED,DECLINED,IN_PROCESS
    }

    boolean isBought;
    Customer commenter;
    Product product;
    String commentString;
    String commentId;

    public Comment(boolean isBought, Customer commenter, Product product, String commentString) throws IOException {
        commentId = UUID.randomUUID().toString();
        this.isBought = isBought;
        this.commenter = commenter;
        this.product = product;
        this.commentString = commentString;
        Database.saveToFile(this,Database.createPath("comment",commentId),false);
    }

}
