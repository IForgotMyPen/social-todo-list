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
    // Scanner object to pass to methods
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

  // Checks whether a string is the back command
  private static boolean cmdBack(String input) {
    return input.equalsIgnoreCase("cmd:back");
  }

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
    System.out.println("You may input 'cmd:back' for the username or password to return to the main menu");

    // For the following do-while loop
    boolean successfulLogin = false;
    do {
      System.out.print("Enter username: ");
      String inputUsername = scnr.next();
      if (cmdBack(inputUsername)) {
        System.out.println("Returning to main menu...");
        return;
      }
      System.out.print("Enter password: ");
      String inputPassword = scnr.next();
      if (cmdBack(inputPassword)) {
        System.out.println("Returning to main menu...");
        return;
      }

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

        // See if the user would like to try again
        if (tryAgain(scnr)) {
          continue;
        } else {
          System.out.println("Returning to main menu...");
          break;
        }
      }
    } while (!successfulLogin);
  }

  // This method correlates to userChoice == 2
  private static void createNewAccount(Scanner scnr) {
    System.out.println("You are creating a new SocialList account.");
    System.out.println("You may input 'cmd:back' for the username or password to return to the main menu");

    // Create a new fileWriter object for modifying login info text document
    try (FileWriter fileOutput = new FileWriter(userLoginInfo, true)) {

      boolean successfulAccountCreation = false;
      do {
        System.out.print("Enter new username: ");
        String username = scnr.next();
        if (cmdBack(username)) {
          System.out.println("Returning to main menu...");
          return;
        }
        System.out.print("Enter new password: ");
        String password = scnr.next();
        if (cmdBack(username)) {
          System.out.println("Returning to main menu...");
          return;
        }

        // Check for valid input (not blank)
        if (username.isBlank() || password.isBlank()) {
          System.out.println("Username or password cannot be blank.");
          if (tryAgain(scnr)) {
            continue;
          } else {
            System.out.println("Returning to main menu...");
            break;
          }
        }

        // Check if username is available
        if (!usernameAvailable(username)) {
          System.out.println("That username is already in use.");
          if (tryAgain(scnr)) {
            continue;
          } else {
            System.out.println("Returning to main menu...");
            break;
          }
        }
        successfulAccountCreation = true;
        System.out.println("Thank you for creating an account with The SocialList!");
        // Update the user data text file with the new user info
        fileOutput.write(username + " " + password + "\n");
        // Update the user list to include the new user in case they want to log in right away
        userList.add(new User(username, password));
      } while (!successfulAccountCreation);
    } catch (java.io.IOException e) {
      System.out.println("ERROR in createNewAccount() method: fileWriter failed.");
    }
  }

  // Helper method to determine if a user wants to try a certain action again (y/n choice)
  private static boolean tryAgain(Scanner scnr) {
    boolean validInput = false;
    String userInput;
    do {
      System.out.print("Would you like to try again? (y/n): ");
      userInput = scnr.next();
      if (userInput.equalsIgnoreCase("y") || userInput.equalsIgnoreCase("n")) {
        validInput = true;
      } else {
        System.out.println("Unrecognized input.");
      }
    } while (!validInput);
    // return "true" if the user selected "y", and "false" if the user selected "n"
    return userInput.equalsIgnoreCase("y");
  }

  // Helper method to determine if a username is already taken
  private static boolean usernameAvailable(String username) {
    for (User user : userList) {
      if (username.equals(user.getUsername())) {
        return false;
      }
    }
    return true;
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
