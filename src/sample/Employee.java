package sample;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Jose Silvestre-Bautista
 *     <p>This creates user profile for an employee which includeds email, username, and password
 */
public class Employee {

  private final StringBuilder name;
  private String username;
  private final String password;
  private String email;

  /**
   * The hub for accepting user info for creating a profile. It checks if the employee entered the
   * proper personal info. Then create a username and password accordingly.
   *
   * @param name Accepts the name entered by the employee
   * @param password Accepts the password entered by the employee.
   */
  public Employee(String name, String password) {
    this.name = new StringBuilder(name);
    if (checkName(name)) {
      setUsername(name);
      setEmail(name);
    } else {
      username = "default";
      email = "user@oracleacademy.Test";
    }

    if (isValidPassword(password)) {
      this.password = password;
    } else {
      this.password = "pw";
    }
  }

  /**
   * Sets the username of employee using their first and last name.
   *
   * @param name The name entered by the employee.
   */
  private void setUsername(String name) {

    Pattern nameAfterSpace = Pattern.compile("\\s(.*)", Pattern.MULTILINE);
    Matcher nameAfterSpaceMatch = nameAfterSpace.matcher(name);
    String lastName = nameAfterSpaceMatch.group(1);
    String initials = name.substring(0, 1) + lastName;
    this.username = initials.toLowerCase();
  }

  /**
   * Checks of the employee entered their first and last name.
   *
   * @param name Name entered by the employee.
   * @return Returns a true or false response.
   */
  private boolean checkName(String name) {
    Pattern pattern = Pattern.compile("\\s");
    Matcher matcher = pattern.matcher(name);
    return matcher.find();
  }

  /**
   * Creates the employee's email using their first and last name.
   *
   * @param name Name entered by the employee
   */
  private void setEmail(String name) {

    Pattern nameBeforeSpace = Pattern.compile("(.*)\\s", Pattern.MULTILINE);
    Matcher nameBeforeSpaceMatch = nameBeforeSpace.matcher(name);

    String firstName = nameBeforeSpaceMatch.group(1);

    Pattern nameAfterSpace = Pattern.compile("\\s(.*)", Pattern.MULTILINE);
    Matcher nameAfterSpaceMatch = nameAfterSpace.matcher(name);
    String lastName = nameAfterSpaceMatch.group(1);

    this.email = firstName.toLowerCase() + "." + lastName.toLowerCase() + "@oracleacademy.Test";
  }

  /**
   * Checks to see if the employee entered a password that contains a capital letter, lower case
   * letter, and special character. If the criteria is the password is set. Else, pw is set as the
   * password.
   *
   * @param password Password entered by the employee.
   * @return True or false for checking the password criteria.
   */
  private boolean isValidPassword(String password) {

    final Pattern pattern = Pattern.compile("^(?=.*[A-Z])(?!.*[^!a-zA-Z0-9@#$^+=])");
    final Matcher matcher = pattern.matcher(password);
    return matcher.find();
  }

  /**
   * This prints the employee's profile credentials.
   *
   * @return A string with all profile info based on employee input
   */
  public String toString() {
    return "Employee Details\n"
        + "Name : "
        + name
        + "\n"
        + "Username : "
        + username
        + "\n"
        + "Email : "
        + email
        + "\n"
        + "Initial Password : "
        + password;
  }
}
