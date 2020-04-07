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
    public static boolean userContinueBoolean = true;

    // Establish directory and file names
    // Should never change, so have them as 'final'
    final static String DIRECTORY = "data";
    final static String FILENAME = "contacts.txt";

    // Establish path to directory and file(s)
    static Path dataDirectory = Paths.get(DIRECTORY);
    static Path contactsPath = Paths.get(DIRECTORY, FILENAME);


    public static void main(String[] args) throws IOException {
        menu();
    }


    // MAIN MENU
    private static void menu() throws IOException {
        int menuSelection = 0;
        do {
            try {
                System.out.println("\n\t- - - - - MAIN MENU - - - - -");
                System.out.println("\t1. View All Contacts");
                System.out.println("\t2. Search Contacts List");
                System.out.println("\t3. Add A Contact");
                System.out.println("\t4. Delete A Contact");
                System.out.println("\tPlease enter an option (1, 2, 3, 4)");
                System.out.println("\t- - - - - - - - - - - - - - - -");
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


    // CONTACTS OUTPUT - FORMATTING
    private static void formatContacts(List<String> input) {
        String nameHeader = "Name";
        String numberHeader = "Phone Number";
        String pipe = "|";

        System.out.println("\n\t+--------------CONTACTS---------------+");
        System.out.printf("\t%s %-20s | %-1s %s%n", pipe, nameHeader, numberHeader, pipe);
        System.out.println("\t|.....................................|");

        for (String contact : input) {
            System.out.printf("\t%s %s%4s%n", pipe, contact, pipe);
        }
        System.out.println("\t+-------------------------------------+\n");
    }


    // VIEW ALL CONTACTS
    private static void viewContacts() throws IOException {
        contacts = Files.readAllLines(contactsPath);
        formatContacts(contacts);
    }


    // SEARCH FOR CONTACT IN LIST
    public static void searchContacts() throws IOException {
        String userSearch;
        List<String> results = new ArrayList<>();
        contacts = Files.readAllLines(contactsPath);

        do {
            System.out.println("\n- - - - - SEARCH - - - - -");
            System.out.println("Enter Full or Partial Name.");
            userSearch = scanner.nextLine().toLowerCase();

            for (String contact : contacts) {
                if (contact.toLowerCase().contains(userSearch)) {
                    results.add(contact);
                }
            }

            if (results.size() >= 1) {
                formatContacts(results);
            } else {
                System.out.println("Sorry, could not find a contact with that name.\n");
            }

            userContinueBoolean = validation("Would you like to search again? [Y/N]");
            results.clear();

        } while (userContinueBoolean);
    }


    // ADD CONTACT TO LIST
    public static void addContact() throws IOException {
        String firstName;
        String lastName;
        String fullName;
        String phoneNum;
        String contact;
        boolean inputCheck = true;


        // Record and format first name
        do {
            System.out.println("Enter first name.");
            firstName = scanner.nextLine().trim();
            firstName = firstName.substring(0, 1).toUpperCase() + firstName.substring(1); // capitalize 1st letter

            // Check if input contains only letters
            if (checkString(firstName)) {
                inputCheck = true;
            } else {
                System.out.println("Invalid entry - please enter a valid name.");
                firstName = "";
                inputCheck = false;
            }
        } while (!inputCheck);
        // Record and format last name
        do {
            System.out.println("Enter last name.");
            lastName = scanner.nextLine().trim();
            lastName = lastName.substring(0, 1).toUpperCase() + lastName.substring(1); // capitalize 1st letter

            // Check if input contains only letters
            if (checkString(lastName)) {
                inputCheck = true;
            } else {
                System.out.println("Invalid entry - please enter a valid name.");
                lastName = "";
                inputCheck = false;
            }
        } while (!inputCheck);


        fullName = firstName + " " + lastName; // After checks, put first and last name together


        // Begin checks for phone number
        do {
            System.out.println("Enter phone number.");
            phoneNum = scanner.nextLine().trim();

            if (phoneNum.matches("[0-9]+") && phoneNum.length() == 7 || phoneNum.length() == 10) {
                inputCheck = true;
            } else {
                System.out.println("Not a valid number. Please enter again.");
                phoneNum = "";
                inputCheck = false;
            }
        } while (!inputCheck);


        // Add new contact to .txt file
        contact = String.format("%-20s | %-7s", fullName, phoneNum);
        Files.write(contactsPath, Arrays.asList(contact), StandardOpenOption.APPEND);
        System.out.printf("%nName: %s%nNumber: %s%nSuccessfully added to list.%n", fullName, phoneNum);
    }

    // DELETE CONTACT FROM LIST
    public static void deleteContact() throws IOException {
        viewContacts();

        // TODO: Format below output, add conditional for full name
        System.out.println("Which contact would you like to delete? (Enter full name)");

        // lambda expression? unsure why below variable necessary
        String finalDeleteContact = scanner.nextLine();

        List<String> updatedContacts = Files.lines(contactsPath).filter(line -> !line.contains(finalDeleteContact)).collect(Collectors.toList());

        Files.write(contactsPath, updatedContacts, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);

        viewContacts();
    }

    // INPUT CHECK FOR LETTERS
    public static boolean checkString(String input) {
        if (input == null) {
            return false;
        }

        for (int i = 0; i < input.length(); i++) {
            if (!Character.isLetter(input.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    // VALIDATION FOR ALL QUESTIONS
    public static boolean validation(String question) {
        String validate = "";
        boolean validateBoolean = true;

        System.out.println(question);
        do {
            validate = scanner.nextLine();
            switch (validate) {
                case "y":
                    validateBoolean = true;
                    break;
                case "n":
                    validateBoolean = false;
                    break;
                default:
                    System.out.println("Invalid input. Please enter [Y]es or [N]o.");
            }
        } while (validate.length() != 1);
        return validateBoolean;
    }

    // PROMPT TO GO BACK TO MAIN MENU
    public static void promptMainMenu() throws IOException {

        do {
            userContinueBoolean = validation("Return to Main Menu? [Y/N]");

            if (userContinueBoolean) {
                main(null);
            } else {
                userContinueBoolean = validation("Exit program? [Y/N]");
            }
        } while (!userContinueBoolean);

        System.out.println("Exiting program. Goodbye.");
    }
}