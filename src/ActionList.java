import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class ActionList {

  // FIELDS ----------------------------------------------------------------------------------------

  User user;
  ArrayList<Action> list;

  File listInfoFile = new File("src/list-info.txt");

  // CONSTRUCTOR -----------------------------------------------------------------------------------

  public ActionList(User user) {
    this.user = user;
    this.list = makeList();
  }

  // METHODS ---------------------------------------------------------------------------------------

  private ArrayList<Action> makeList() {
    ArrayList<Action> returnArrayList = new ArrayList<>();
    try (Scanner fileReader = new Scanner(listInfoFile)) {
      while (fileReader.hasNextLine()) {
        if (fileReader.next().equals(user.getUsername())) { // Only collect data for current user
          String dateAdded = fileReader.next();
          String dateDue = fileReader.next();
          String dateCompleted = fileReader.next();
          String description = fileReader.nextLine().trim();

          returnArrayList.add(new Action(user, dateAdded, dateDue, dateCompleted, description));
        } else {
          fileReader.nextLine(); // Skip line if data is not for current user
        }
      }
    } catch (FileNotFoundException e) {
      System.out.println("ERROR: fileReader failed");
    }
    return returnArrayList;
  }

  // GETTERS, SETTERS, & TOGGLES -------------------------------------------------------------------
  public String getUser() {
    return this.user.getUsername();
  }

  public ArrayList<Action> getList() {
    return this.list;
  }
}
