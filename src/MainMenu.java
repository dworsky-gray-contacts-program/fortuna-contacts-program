import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class MainMenu {

    // Initialize Scanner
    private static Scanner scanner = new Scanner(System.in);

    // Initialize new ArrayList
    public static List<String> contacts = new ArrayList<>();

    // Establish directory and file names
    // Should never change, so have them as 'final'
    final static String DIRECTORY = "data";
    final static String FILENAME = "contacts.txt";
    public static boolean userContinueBoolean = true;

    // Establish path to directory and file(s)
    static Path dataDirectory = Paths.get(DIRECTORY);
    static Path contactsPath = Paths.get(DIRECTORY, FILENAME);


    // Begin Main
    public static void main(String[] args) throws IOException {

        int menuSelection = 0;
        do {
            try {
                System.out.println("\n- - - - - MAIN MENU - - - - -");
                System.out.println("\t1. View All Contacts");
                System.out.println("\t2. Search Contacts List");
                System.out.println("\t3. Add A Contact");
                System.out.println("\t4. Delete A Contact");
                System.out.println("\tPlease enter an option (1, 2, 3, 4, 5)");
                System.out.println("- - - - - - - - - - - - - - - -");
                menuSelection = Integer.parseInt(scanner.nextLine());
                break;
            } catch (Exception e) {
                System.out.println("Not a valid input - please try again.");
            }
        }
        while (userContinueBoolean);

        switch (menuSelection) {
            case 1:
                viewContacts();
                promptMainMenu();
                break;
            case 2:
                searchContacts();
                promptMainMenu();
                break;
            case 3:
                addContact();
                promptMainMenu();
                break;
            case 4:
                deleteContact();
                promptMainMenu();
                break;
        }
    }

    // VIEW ALL CONTACTS IN LIST
    public static void viewContacts() throws IOException {
        contacts = Files.readAllLines(contactsPath);

        System.out.println("\n- - - - - CONTACTS - - - - -");
        System.out.println("\tName | Phone Number");
        for (String contact : contacts) {
            System.out.println("\t" + contact);
        }
        System.out.println("- - - - - - - - - - - - - - -");
    }

    // SEARCH FOR CONTACT IN LIST
    public static void searchContacts() throws IOException {
        String userSearch;
        String searchAgain;
        String results = "";
        int counter = 0;
        contacts = Files.readAllLines(contactsPath);

        do {
            System.out.println("\n- - - - - SEARCH - - - - -");
            System.out.print("Enter Full or Partial Name: ");
            userSearch = scanner.nextLine().toLowerCase();


            for (String contact : contacts) {
                if (contact.toLowerCase().contains(userSearch)) {
                    results += contact + "\n";
                    counter++;
                }
            }

            // TODO: 1. Reformat searchContacts output
            if (counter >= 1) {
                System.out.println(results);
            } else {
                System.out.println("Sorry, could not find a contact with that name.\n");
            }

            // Ask if user wants to search again - if not, end searchContacts method
            System.out.println("Would you like to search again? [Y/N]");
            searchAgain = scanner.nextLine();
            if (searchAgain.equalsIgnoreCase("y")) {
                searchContacts();
            } else if (searchAgain.equalsIgnoreCase("n")) {
                userContinueBoolean = false;
            } else {
                System.out.println("Invalid input. Please enter [Y]es or [N]o.");
            }
        } while (userContinueBoolean);
    }

    // ADD CONTACT TO LIST
    public static void addContact() throws IOException {
        String name;
        String phoneNum;
        String contact;

        // TODO: Add conditionals in addContact method - firstname, lastName, phoneNumber(all characters are numbers, length is either 7 or 10)
        System.out.println("Please enter a first name.");
        name = scanner.nextLine().trim()+" ";

        System.out.println("Please enter a last name.");
        name += scanner.nextLine().trim() + " | ";

        // testing to say what name looks like
        System.out.println(name);

        System.out.println("Please enter a 10-digit phone number, starting with the area code.");
        phoneNum = scanner.nextLine().trim();

        contact = name + phoneNum;

        System.out.println(contact);

        Files.write(contactsPath, Arrays.asList(contact), StandardOpenOption.APPEND);
    }

    // DELETE CONTACT FROM LIST
    public static void deleteContact() throws IOException {
        String deleteContact = "";

        viewContacts();

        // TODO: Format below output
        System.out.println("Which contact would you like to delete? (Enter full name)");
        deleteContact = scanner.nextLine();

        // lambda expression? unsure why below variable necessary
        String finalDeleteContact = deleteContact;

        List<String> updatedContacts = Files.lines(contactsPath).filter(line -> !line.contains(finalDeleteContact)).collect(Collectors.toList());

        Files.write(contactsPath, updatedContacts, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);

        viewContacts();
    }

    // PROMPT TO GO BACK TO MAIN MENU
    public static void promptMainMenu() throws IOException {
        String goToMain;
        String exit;

        // 1st question
        System.out.println("\nReturn to Main Menu? [Y/N]");
        goToMain = scanner.nextLine();

        if (goToMain.equalsIgnoreCase("y")) {
            main(null);
        } else if (goToMain.equalsIgnoreCase("n")) {

            // 2nd question
            System.out.println("Exit program? [Y/N]");
            exit = scanner.nextLine();

            if (exit.equalsIgnoreCase("y")) {
                System.out.println("Exiting program. Goodbye.");
            } else if (exit.equalsIgnoreCase("n")) {
                promptMainMenu();
            } else {
                System.out.println("Invalid input. Please try again.");
                promptMainMenu();
            }
        } else {
            System.out.println("Invalid entry.");
            promptMainMenu();
        }
    }
}