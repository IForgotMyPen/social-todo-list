import java.util.Scanner;

public class MainMenu {

  // FIELDS ----------------------------------------------------------------------------------------

  // To help assign user-input integers to a more readable format
  enum Choice {
    VIEW_LIST,
    ADD_ITEMS_TO_LIST,
    COMPLETE_ITEMS_ON_LIST,
    VIEW_COMPLETED_ITEMS,
    ADD_FRIEND,
    VIEW_FRIEND_LIST,
    REMOVE_FRIEND,
    LOG_OUT
  }
  private static final Choice[] Choices = new Choice[] {
      Choice.VIEW_LIST, Choice.ADD_ITEMS_TO_LIST, Choice.COMPLETE_ITEMS_ON_LIST,
      Choice.VIEW_COMPLETED_ITEMS, Choice.ADD_FRIEND, Choice.VIEW_FRIEND_LIST,
      Choice.REMOVE_FRIEND, Choice.LOG_OUT
  };

  // MAIN METHOD -----------------------------------------------------------------------------------

  // METHODS ---------------------------------------------------------------------------------------

  // The main menu options, will return an int to the main method
  private static Choice menuOptions(Scanner scnr) {
    // Note: different from userInput, which is raw integer input (without checking for valid range)
    int userChoice = -1;
    boolean validChoice = false;
    do {
      System.out.print("""
          1. View List
          2. Add items to List
          3. Complete items on List
          4. View completed List items
          5. Add friend
          6. View friend's List
          7. Remove friend
          8. Log Out
          """);

      if (scnr.hasNextInt()) { // Checking for valid input data type
        userChoice = scnr.nextInt();
        if (userChoice >= 1 && userChoice <= 8) { // Checking for valid integer range
          validChoice = true;
        } else {
          System.out.println("Invalid integer value, please try again:");
        }
      } else {
        System.out.println("That input was unrecognized, please try again:");
        scnr.next();
      }
    } while (!validChoice);
    return Choices[userChoice]; // Note that this will cause an error if userChoice is for some
                                // reason not updated, but I would rather contain that error to this
                                // method, so I'll leave it
  }
}
