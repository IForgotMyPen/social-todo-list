import java.util.Scanner;

public class MainMenu {

  // FIELDS ----------------------------------------------------------------------------------------

  // MAIN METHOD -----------------------------------------------------------------------------------

  // METHODS ---------------------------------------------------------------------------------------

  // The main menu options, will return an int to the main method
  private static int menuOptions(Scanner scnr) {
    // We want these options:
    // 1. View current to-do list
    // 2. Add new items to to-do list
    // 3. Mark items on to-do list as completed
    // 4. View completed itemS
    // 5. Add friend
    // 6. View and interact with friend to-do list
    // 7. Remove friend
    // 8. Log Out

    // Note: different from userInput, which is raw integer input (without checking for valid range)
    int userChoice = -1;
    // For the following do-while loop
    boolean validInput = false;
    do {
      // Options menu
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

      // Checking for a valid input type & integer value
      if (scnr.hasNextInt()) {
        int userInput = scnr.nextInt();
        if (userInput >= 1 && userInput <= 8) {
          validInput = true;
          userChoice = userInput;
        } else {
          System.out.println("Invalid integer value, please try again:");
        }
      } else {
        System.out.println("That input was unrecognized, please try again:");
        scnr.next();
      }
    } while (!validInput);
    return userChoice;
  }
}
