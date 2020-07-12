package bank;

public class Receipt {
    private final String receiptId;
    private final String receiptType;
    private final String description;
    private final double money;
    private final String sourceId;
    private final String destId;
    private boolean paid;

    public Receipt(String receiptId, String receiptType, String description, double money, String sourceId, String destId) {
        this.receiptId = receiptId;
        this.receiptType = receiptType;
        this.description = description;
        this.money = money;
        this.sourceId = receiptType.equals("deposit") ? "-1" : sourceId;
        this.destId = receiptType.equals("withdraw") ? "-1" : destId;
        this.paid = false;
    }

    public void pay() {
        Account source = BankController.getInstance().getAccountByUserName(sourceId);
        Account dest = BankController.getInstance().getAccountByUserName(destId);
        switch (receiptType) {
            case "move":
                BankController.getInstance().move(source, dest, money);
                break;
            case "deposit":
                BankController.getInstance().deposit(dest, money);
                break;
            case "withdraw":
                BankController.getInstance().withdraw(source, money);
                break;
        }
    }

    public boolean isPaid() {
        return paid;
    }

    public String getReceiptId() {
        return receiptId;
    }

    public String getSourceId() {
        return sourceId;
    }

    public String getDestId() {
        return destId;
    }

    public double getMoney() {
        return money;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    @Override
    public String toString() {
        return "{\"receiptType\":\"" + receiptType + "\",\n" +
                "\"description\":\"" + description + "\",\n" +
                "\"money\":\"" + money + "\",\n" +
                "\"id\":\"" + receiptId + "\",\n" +
                "\"sourceId\":\"" + sourceId + "\",\n" +
                "\"destId\":\"" + destId + "\",\n" +
                "\"paid\":\"" + paid + "\"}";
    }
}
