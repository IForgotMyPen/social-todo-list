import java.util.Scanner;

public class LoginMenu {
  public static void main(String[] args) {
    Scanner scnr = new Scanner(System.in);

    loginOptions(scnr, true);
  }

  public static void loginOptions(Scanner scnr, boolean withIntroMessage) {
    // Here we want to present users with a menu that contains a few different options:
    //  1. Login to an existing account
    //  2. Create a new account
    //  3. Exit the program
    // Then, based on the user's decision, we redirect to the respective method.
    // Note: I want to contain the code for this in a separate method so that it is easier
    //       to call repeatedly.

    // Introduction message, will only show on occasion
    if (withIntroMessage) {
      System.out.println("Hello, and welcome to The SocialList!");
      System.out.println(
          "Select an option by inserting the respective number and hitting 'Enter':");
    }

    // Declare userChoice
    // Note: different than userInput, which is raw integer input (without checking for valid range)
    int userChoice;

    boolean validInput = false;
    do {
      // Options menu
      System.out.print("""
          1. Log in to an existing account
          2. Create a new account
          3. Exit The SocialList
          """);

      // Checking for a valid input type & integer value
      if (scnr.hasNextInt()) {
        int userInput = scnr.nextInt();
        if (userInput >= 1 && userInput <= 3) {
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
  }
}
