package model;

public class AddSalespersonRequest extends Request {
    private String company;
    private String username;
    private String firstName;
    private String lastName;
    private String emailAddress;
    private String phoneNumber;
    private String password;
    

    public AddSalespersonRequest(String company, String username, String firstName, String lastName, String emailAddress, String phoneNumber, String password) {
        this.company = company;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }
}
