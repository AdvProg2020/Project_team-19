import java.util.regex.Pattern;

interface Person {
    String username = null;
    String firstName = null;
    String lastName = null;
    String emailAddress = null;
    Pattern emailPattern = Pattern.compile ( "\\w+@\\w+\\.\\w+" );
    String phoneNumber = null;
    Pattern phonePattern = Pattern.compile ( "\\+?\\d+" );
    String password = null;
}
