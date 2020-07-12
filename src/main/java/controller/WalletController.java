package controller;

import bank.BankAPI;
import model.Customer;
import model.Person;
import model.Salesperson;

import java.io.IOException;
import java.util.UUID;

public class WalletController {
    private static WalletController single_instance = null;
    private double MIN_BALANCE;

    private WalletController() {
    }

    public static WalletController getInstance() {
        if (single_instance == null)
            single_instance = new WalletController();

        return single_instance;
    }
    public String createAccount(Person person){
        try {
            return BankAPI.SendMessage("create_account "+person.getPersonInfo().get("firstName")+" "+person.getPersonInfo().get("lastName")+" "+person.getUsername()+" "+person.getPassword()+" "+person.getPassword());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getToken(String username,String password){
        try {
           return BankAPI.SendMessage("get_token "+username+" "+password);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void createReceipt(String token,String receiptType,double money,String sourceId,String destId,String description){
        try {
            BankAPI.SendMessage("create_receipt "+token+" "+receiptType+" "+money+" "+sourceId+" "+destId+" "+description);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getTransactions(String token,String type){
        try {
            BankAPI.SendMessage("get_transaction "+token+" "+type);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getBalance(String token){
        try {
            BankAPI.SendMessage("get_balance "+token);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void pay(String receiptId){
        try {
            BankAPI.SendMessage("pay "+receiptId);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        try {
            BankAPI.ConnectToBankServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(getInstance().getToken("sol","1234"));
    }

    public void setMIN_BALANCE(double MIN_BALANCE) {
        this.MIN_BALANCE = MIN_BALANCE;
    }

    public boolean withdraw(Person person, double money){
            Salesperson salesperson = (Salesperson) person;
           if (salesperson.getWallet().getBalance()-money<MIN_BALANCE){
               return false;
           }else {
               salesperson.getWallet().setBalance(salesperson.getWallet().getBalance()-money);
           }
       return true;
    }

    public void increaseBalance(Person person, double money){
        if (person instanceof Customer)
            ((Customer)person).getWallet().setBalance(((Customer)person).getWallet().getBalance()+money);
        else
            ((Salesperson)person).getWallet().setBalance(((Salesperson)person).getWallet().getBalance()+money);
    }

    public void decreaseBalance(Person person, double money){
        ((Customer)person).getWallet().setBalance(((Customer)person).getWallet().getBalance()+money);
    }
}
