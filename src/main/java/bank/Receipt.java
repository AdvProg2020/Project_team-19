package bank;

public class Receipt {
    private Type type;
    private String description;
    private double money;
    private String sourceId;
    private String destId;
    enum Type{
        DEPOSIT,MOVE,WITHDRAW
    }

    public Receipt( String description, double money, String sourceId, String destId) {
        this.type = Type.MOVE;
        this.description = description;
        this.money = money;
        this.sourceId = sourceId;
        this.destId = destId;
    }

    public Receipt( String description, double money, String destId) {
        this.type = Type.DEPOSIT;
        this.description = description;
        this.money = money;
        this.sourceId = "-1";
        this.destId = destId;
    }

    public Receipt(Type type, String description, double money, String destId) {
        this.type = Type.WITHDRAW;
        this.description = description;
        this.money = money;
        this.sourceId = "-1";
        this.destId = destId;
    }
}
