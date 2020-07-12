package bank;

public enum PacketType {
    CREATE_ACCOUNT("create_account"),
    TOKEN("get_token"),
    CREATE_RECEIPT("create_receipt"),
    TRANSACTION("get_transaction"),
    PAY("pay"),
    BALANCE("get_balance"),
    EXIT("exit");

    public final String label;

    PacketType (String label) {
        this.label = label;
    }
}
