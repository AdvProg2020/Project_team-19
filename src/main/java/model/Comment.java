package model;

public class Comment {
    boolean isBought;
    Person commenter;
    Product product;
    String commentString;
    enum  VerifiedState{
        VERIFIED,DECLINED,IN_PROCESS
    }
}
