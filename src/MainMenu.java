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

    public static List<String> contacts = new ArrayList<>();
    public static boolean userContinueBoolean = true;
    final static String DIRECTORY = "data";
    final static String FILENAME = "contacts.txt";

    // Establish path to directory and file(s)
    static Path dataDirectory = Paths.get(DIRECTORY);
    static Path contactsPath = Paths.get(DIRECTORY, FILENAME);


    public static void main(String[] args) throws IOException {
        int menuSelection = 0;
        do {
            try {
                System.out.println("\n\t- - - - - MAIN MENU - - - - -");
                System.out.println("\t1. View All Contacts");
                System.out.println("\t2. Search Contacts List");
                System.out.println("\t3. Add A Contact");
                System.out.println("\t4. Delete A Contact");
                System.out.println("\t5. Exit Program");
                System.out.println("\tPlease enter an option (1, 2, 3, 4, 5)");
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
            case 5:
                break;
        }
    }


    // CONTACTS OUTPUT - FORMATTING
    private static void formatContacts(List<String> input) {
        String nameHeader = "Name";
        String numberHeader = "Phone Number";

        System.out.println("\n\t+--------------CONTACTS---------------+");
        System.out.printf("\t| %-18s | %-14s |%n", nameHeader, numberHeader);
        System.out.println("\t|.....................................|");

        for (String contact : input) {
            System.out.printf("\t%s%n", contact);
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
        String firstName, lastName, fullName;
        String phoneNum;
        String contact;
        String pipe = "|";
        boolean inputCheck = true;

        do {
            // FIRST NAME - validate only letters, capitalize 1st letter
            do {
                System.out.print("Enter first name: ");
                firstName = scanner.nextLine().trim();
                firstName = firstName.substring(0, 1).toUpperCase() + firstName.substring(1); // capitalize 1st letter

                // Check if input contains only letters
                if (firstName.matches("[a-zA-Z]+")) {
                    inputCheck = true;
                } else {
                    System.out.println("Invalid entry - please enter a valid name.");
                    firstName = "";
                    inputCheck = false;
                }
            } while (!inputCheck);

            // LAST NAME - validate only letters, capitalize 1st letter
            do {
                System.out.print("Enter last name: ");
                lastName = scanner.nextLine().trim();
                lastName = lastName.substring(0, 1).toUpperCase() + lastName.substring(1); // capitalize 1st letter

                // Check if input contains only letters
                if (lastName.matches("[a-zA-Z]+")) {
                    inputCheck = true;
                } else {
                    System.out.println("Invalid entry - please enter a valid name.");
                    lastName = "";
                    inputCheck = false;
                }
            } while (!inputCheck);

            // PHONE NUMBER - validate only numbers, is either 7 or 10 characters in length
            do {
                System.out.print("Enter phone number: ");
                phoneNum = scanner.nextLine().trim();

                if (phoneNum.matches("[0-9]+") && phoneNum.length() == 7 || phoneNum.length() == 10) {
                    inputCheck = true;
                } else {
                    System.out.println("Not a valid number. Please enter again.");
                    phoneNum = "";
                    inputCheck = false;
                }
            } while (!inputCheck);

            // After checks, put name's together
            fullName = String.format("%s %s", firstName, lastName);

            // format contact before adding it to .txt file
            contact = String.format("%s %-18s %s %-14s %s", pipe, fullName, pipe, phoneNum, pipe);

            // Add new contact to .txt file
            Files.write(contactsPath, Arrays.asList(contact), StandardOpenOption.APPEND);

            System.out.println("\nContact successfully added.");
            inputCheck = validation("Add another contact? [Y/N]");
        } while (inputCheck);
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
    }
}