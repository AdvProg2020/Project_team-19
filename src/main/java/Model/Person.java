package Model;

abstract public class Person {
    private boolean isLoggedIn;
    private String username;
    private String firstName;
    private String lastName;
    private String emailAddress;
    private String phoneNumber;
    private String password;

    public void setFirstName ( String firstName ) {
        this.firstName = firstName;
    }

    public void setLastName ( String lastName ) {
        this.lastName = lastName;
    }

    public void setEmailAddress ( String emailAddress ) {
        this.emailAddress = emailAddress;
    }

    public void setPhoneNumber ( String phoneNumber ) {
        this.phoneNumber = phoneNumber;
    }

    public void setPassword ( String password ) {
        this.password = password;
    }
}
