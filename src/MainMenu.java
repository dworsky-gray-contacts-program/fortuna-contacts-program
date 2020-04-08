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
        contacts = Files.readAllLines(contactsPath);

        do {
            try {
                System.out.println("\n\t- - - - - MAIN MENU - - - - -");
                System.out.println("\t1. View All Contacts");
                System.out.println("\t2. Search Contacts List");
                System.out.println("\t3. Add A Contact");
                System.out.println("\t4. Delete A Contact");
                System.out.println("\t5. Exit Program");
                System.out.println("\n\tEnter (1, 2, 3, 4, 5)");
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
                System.out.println("Goodbye.");
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
    private static void viewContacts() {
        formatContacts(contacts);
    }


    // SEARCH FOR CONTACT
    public static void searchContacts() {
        String userSearch;
        List<String> results = new ArrayList<>();

        do {
            System.out.println("\n\t- - - - - - SEARCH - - - - - -");
            System.out.println("\tEnter Full or Partial Name.");
            userSearch = scanner.nextLine().toLowerCase();

            for (String contact : contacts) {
                if (contact.substring(0, 20).toLowerCase().contains(userSearch)) {
                    results.add(contact);
                }
            }

            if (results.size() >= 1) {
                formatContacts(results);
            } else {
                System.out.println("\nSorry, could not find a contact with that name.");
            }

            results.clear();

        } while (validation("Would you like to search again? [Y/N]"));
    }


    // ADD CONTACT
    public static void addContact() throws IOException {
        String firstName, lastName, fullName;
        String phoneNum;
        String contact;
        boolean inputCheck;

        do {
            System.out.println("\n\t- - - - - ADD - - - - -");

            // FIRST NAME
            do {
                System.out.println("\tEnter first name.");
                firstName = scanner.nextLine().trim();
                // capitalize 1st letter
                firstName = firstName.substring(0, 1).toUpperCase() + firstName.substring(1);
            } while (!isOnlyLetters(firstName));

            // LAST NAME
            do {
                System.out.println("\tEnter last name.");
                lastName = scanner.nextLine().trim();
                // capitalize 1st letter
                lastName = lastName.substring(0, 1).toUpperCase() + lastName.substring(1);
            } while (!isOnlyLetters(lastName));

            // PHONE NUMBER - validate only numbers, is either 7 or 10 characters in length
            do {
                System.out.println("\tEnter phone number.");
                phoneNum = scanner.nextLine().trim();

                if (phoneNum.matches("[0-9]+") && phoneNum.length() == 7 || phoneNum.length() == 10) {
                    inputCheck = true;
                } else {
                    System.out.println("Not a valid number. Please enter again.\n");
                    phoneNum = "";
                    inputCheck = false;
                }
            } while (!inputCheck);

            // After checks, put name's together
            fullName = String.format("%s %s", firstName, lastName);

            // Format phone number to include dashes
            if (phoneNum.length() == 7) {
                phoneNum = phoneNum.substring(0, 3) + "-" + phoneNum.substring(3, 7);
            } else if (phoneNum.length() == 10) {
                phoneNum = phoneNum.substring(0,3)+"-"+phoneNum.substring(3,6)+"-"+phoneNum.substring(6,10);
            }

            // Format contact before adding it to .txt file
            contact = String.format("| %-18s | %-14s |", fullName, phoneNum);

            // Add new contact to .txt file
            Files.write(contactsPath, Arrays.asList(contact), StandardOpenOption.APPEND);

            System.out.println("\nContact successfully added.");
        } while (validation("Add another contact? [Y/N]"));
    }


    // DELETE CONTACT
    public static void deleteContact() throws IOException {
        boolean contactFound = false;
        String deleteContact;

        do {
            viewContacts();
            System.out.println("\t- - - - - DELETE - - - - -");

            System.out.println("\tDelete Contact (Enter full or partial name).");
            deleteContact = scanner.nextLine().toLowerCase().trim();

            for (String contact : contacts) {
                if (contact.toLowerCase().contains(deleteContact)) {
                    System.out.println("\n" + contact.substring(2, 35));
                    contactFound = true;
                    userContinueBoolean = validation("CONFIRM - Delete contact? [Y/N]");
                }
            }

            if (!contactFound) {
                userContinueBoolean = validation("\nNo contact found with that name.\nSearch again? [Y/N]");
                    if (userContinueBoolean) {
                        deleteContact();
                    }
                break;
            }

            if (userContinueBoolean) {
                String finalDeleteContact = deleteContact;

                List<String> updatedContacts = Files.lines(contactsPath).filter(line -> !line.substring(1, 20).trim().equalsIgnoreCase(finalDeleteContact)).collect(Collectors.toList());

                Files.write(contactsPath, updatedContacts, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
                System.out.println("\nContact successfully deleted.");
            }
        } while (validation("Delete Another contact? [Y/N]"));
    }


    // PROMPT TO GO BACK TO MAIN MENU
    public static void promptMainMenu() throws IOException {
        do {
            if (validation("Return to Main Menu? [Y/N]")) {
                main(null);
                break;
            }

            if (validation("Exit program? [Y/N]")) {
                System.out.println("Goodbye.");
                break;
            }
        } while (true);
    }


    // VALIDATE STRING FOR NAMES - ALL LETTERS
    public static boolean isOnlyLetters(String input) {
        boolean isStringBoolean;

        if (input.matches("[a-z A-Z]+")) {
            isStringBoolean = true;
        } else {
            System.out.println("Not a valid name - please enter again.\n");
            isStringBoolean = false;
        }
        return isStringBoolean;
    }


    // VALIDATION FOR ALL QUESTIONS
    public static boolean validation(String question) {
        String validate = "";
        boolean validateBoolean;

        do {
            System.out.println(question);
            validate = scanner.nextLine().toLowerCase();
            switch (validate) {
                case "y":
                    validateBoolean = true;
                    break;
                case "n":
                    validateBoolean = false;
                    break;
                default:
                    System.out.println("\nInvalid input. Please enter [Y]es or [N]o.");
                    validateBoolean = false;
            }
        } while (!validate.matches("[yn]"));
        return validateBoolean;
    }


}