// Things left to complete in this class:
// - redirection when logging into an account
// - redirection when creating a new account (ask if they want to log in right away)
// - back button for login
// - back button for creating an account

import java.io.FileWriter;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;

public class LoginMenu {

  // FIELDS ----------------------------------------------------------------------------------------

  private static final File userLoginInfo = new File("src/login-info.txt");
  private static ArrayList<User> userList = makeUserList();

  // MAIN METHOD -----------------------------------------------------------------------------------

  public static void main(String[] args) {
    // Scanner object to pass to methods (may just want to make this a field)
    Scanner scnr = new Scanner(System.in);

    // For the following do-while loop
    boolean isRunning = true;
    do {
      int userChoice = loginOptions(scnr, true);
      if (userChoice != -1) {

        // Choice: login to an existing account
        if (userChoice == 1) {
          loginToExistingAccount(scnr);
        }

        // Choice: create a new account
        if (userChoice == 2) {
          createNewAccount(scnr);
        }

        // Choice: exit the program
        if (userChoice == 3) {
          isRunning = false;
          exitProgram();
        }

      }
    } while (isRunning);
  }

  // METHODS ---------------------------------------------------------------------------------------

  private static int loginOptions(Scanner scnr, boolean withIntroMessage) {
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

    // Note: different from userInput, which is raw integer input (without checking for valid range)
    int userChoice = -1;

    // For the following do-while loop
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
    return userChoice;
  }

  // This method correlates to userChoice == 1
  private static void loginToExistingAccount(Scanner scnr) {
    System.out.println("You are attempting to log in to an existing SocialList account.");

    // For the following do-while loop
    boolean successfulLogin = false;
    do {
      System.out.print("Enter username: ");
      String inputUsername = scnr.next();
      System.out.print("Enter password: ");
      String inputPassword = scnr.next();

      // Loop through all the users in the userList, and see if there is a match. If there is, we
      // break the do-while loop, and toggle the user to be logged in.
      for (User user : userList) {
        if (user.getUsername().equals(inputUsername) && user.getPassword().equals(inputPassword)) {
          successfulLogin = true;
          user.toggleLoggedIn();
          System.out.println("You have successfully logged in.");
          System.out.println("Welcome, " + user.getUsername() + ".\nFIX:RETURNING TO MAIN MENU...");
          // TODO: redirect to new class with user options
        }
      }
      // Case: user did not successfully log into an existing account, they now have the choice
      //       to either try again or to go back.
      if (!successfulLogin) {
        System.out.println("That username and/or password is incorrect.");

        // Try again loop, basically declare an empty string, and ask the user y/n until they enter
        // a valid input.
        String userTryAgainInput = "";
        do {
          System.out.print("Would you like to try to login again? (y/n): ");
          String rawUserTryAgainInput = scnr.next();
          if (rawUserTryAgainInput.equalsIgnoreCase("y") ||
          rawUserTryAgainInput.equalsIgnoreCase("n")) {
            userTryAgainInput = rawUserTryAgainInput;
          } else {
            System.out.println("Unrecognized input, try again.");
          }
        } while (userTryAgainInput.isEmpty());
        // Don't do anything in this case, just keep do-while looping
        if (userTryAgainInput.equalsIgnoreCase("y")) {
          continue;
        }
        // Here we break the do-while loop to go back to the main method
        else if (userTryAgainInput.equalsIgnoreCase("n")) {
          System.out.println("Exiting to main menu...");
          break;
        }
      }
    } while (!successfulLogin);
  }

  // This method correlates to userChoice == 2
  private static void createNewAccount(Scanner scnr) {
    System.out.println("You are creating a new SocialList account.");

    // Create a new fileWriter object for modifying login info text document
    try (FileWriter fileOutput = new FileWriter(userLoginInfo, true)) {
      boolean validInput = false;
      // Loop until we get a valid input (not blank)
      do {
        System.out.print("Enter new username: ");
        String username = scnr.next();

        System.out.print("Enter new password: ");
        String password = scnr.next();

        if (!username.isBlank() && !password.isBlank()) {
          System.out.println("Thank you for creating a SocialList account!\nFIX:RETURNING TO MAIN MENU...");
          validInput = true;
          // write the new username and password to the file
          fileOutput.write(username + " " + password + "\n");
          fileOutput.close();
          // we want to update the user list to include the new user, in case they log out and want to log back in
          userList.add(new User(username, password));
          // TODO: redirect to new class with user options
        } else {
          System.out.println("Username and/or password cannot be blank, try again: ");
        }
      } while (!validInput);

    } catch (java.io.IOException e) {
      System.out.println("ERROR in createNewAccount() method: fileWriter failed.");
    }
  }

  // This method correlates to userChoice == 3
  private static void exitProgram() {
    System.out.println("Thank you for visiting The SocialList. Have a great day!");
  }

  // This method is used to create an ArrayList of User objects
  private static ArrayList<User> makeUserList() {
    ArrayList<User> userList = new ArrayList<>();
    try (Scanner fileReader = new Scanner(userLoginInfo)) {
      while(fileReader.hasNext()) {
        String username = fileReader.next();
        String password = fileReader.nextLine().trim();

        User newUser = new User(username, password);

        userList.add(newUser);
      }
    } catch (FileNotFoundException exception) {
      System.out.println("ERROR in makeUserList() method: fileReader failed.");
    }
    return userList;
  }
}
