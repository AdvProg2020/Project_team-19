package model;

public class Comment {
    enum  VerifiedState{
        VERIFIED,DECLINED,IN_PROCESS
    }

    boolean isBought;
    Customer commenter;
    Product product;
    String commentString;

    public Comment(boolean isBought, Customer commenter, Product product, String commentString) {
        this.isBought = isBought;
        this.commenter = commenter;
        this.product = product;
        this.commentString = commentString;
    }
}
