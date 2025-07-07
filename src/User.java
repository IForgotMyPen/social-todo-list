public class User {

  // FIELDS ----------------------------------------------------------------------------------------

  private String username;
  private String password;

  private boolean isLoggedIn;

  // CONSTRUCTOR -----------------------------------------------------------------------------------

  public User(String username, String password) {
    this.username = username;
    this.password = password;

    this.isLoggedIn = false;
  }

  // GETTERS, SETTERS, & TOGGLES -------------------------------------------------------------------

  public String getUsername() {
    return this.username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return this.password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  // Returns whether the user is signed in or not
  public boolean isLoggedIn() {
    return this.isLoggedIn;
  }

  // Simply toggles whether the user is currently logged in
  public void toggleLoggedIn() {
    if (isLoggedIn) {
      isLoggedIn = false;
    } else {
      isLoggedIn = true;
    }
  }
}
