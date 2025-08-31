import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Action {

  // FIELDS ----------------------------------------------------------------------------------------

  User user;
  String dateAdded;
  String dateDue;
  String dateCompleted;
  boolean isComplete;
  String description;

  // CONSTRUCTOR -----------------------------------------------------------------------------------

  public Action(User user, String dateDue, String description) {
    LocalDateTime currentDateTime = LocalDateTime.now();
    DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("MM-dd-yyyy/HH:mm:ss");

    this.user = user;
    this.dateAdded = currentDateTime.format(dateTimeFormat);
    // Want to make sure the date is in the correct format first, also want to ask if the user wants
    // to set a specific time of day
    this.dateDue = dateDue;
    this.dateCompleted = "incomplete";
    this.isComplete = false;
    this.description = description;
  }

  public Action(
      User user, String dateAdded, String dateDue, String dateCompleted, String description) {
    this.user = user;
    this.dateAdded = dateAdded;
    this.dateDue = dateDue;
    this.dateCompleted = dateCompleted;
    this.isComplete = !this.dateCompleted.equals("incomplete");
    this.description = description;
  }

  // GETTERS, SETTERS, & TOGGLES -------------------------------------------------------------------

  public String getUser() {
    return this.user.getUsername();
  }

  public String getDescription() {
    return this.description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getDateAdded() {
    return this.dateAdded;
  }

  public String getDateDue() {
    return this.dateDue;
  }

  public void setDateDue(String dateDue) {
    this.dateDue = dateDue;
  }

  public String getDateCompleted() {
    return this.dateCompleted;
  }

  public boolean isComplete() {
    return this.isComplete;
  }

  public void markComplete() {
    this.isComplete = true;
  }
}
