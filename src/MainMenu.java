import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class MainMenu {

    // Initialize Scanner
    // private static Scanner scanner;
    private static Scanner scanner = new Scanner(System.in);

    // Set Scanner
    // public static void SetScanner(Scanner input) {scanner = input;}

    // Establish directory and file names
    // Should never change, so have them as 'final'
    final static String directory = "data";
    final static String filename = "contacts.txt";

    // Establish path to directory and file(s)
    static Path dataDirectory = Paths.get(directory);
    static Path contactsPath = Paths.get(directory, filename);

//    static List<String> contacts = new ArrayList<>();

    // Begin Main
    public static void main(String[] args) throws IOException {

        int menuSelection;

        do {
            try {
                System.out.println("\n\t- - - - - Main Menu - - - - -");
                System.out.println("\t1. View All Contacts");
                System.out.println("\t2. Search Contacts List");
                System.out.println("\t3. Add A Contact");
                System.out.println("\t4. Delete A Contact");
                System.out.println("\tPlease enter an option (1, 2, 3, 4, 5)");
                System.out.println("\t- - - - - - - - - - - - - - - -");
                menuSelection = scanner.nextInt();
                break;
            } catch (Exception e) {
                System.out.println("Not a valid input - please try again.");
                scanner.nextLine();
            }
        }
        while (true);

        switch (menuSelection) {
            case 1:
                viewContacts();
                break;
            case 2:
                searchContacts();
                break;
            case 3:
                addContact();
                break;
            case 4:
                deleteContact();
                break;
        }
    }

    // VIEW ALL CONTACTS IN LIST
    public static void viewContacts() throws IOException {
        List<String> contacts = Files.readAllLines(contactsPath);

        System.out.println("\n\t- - - - - All Contacts - - - - -");
        for (String contact : contacts) {
            System.out.println("\t\t" + contact);
        }
        System.out.println("\t- - - - - - - - - - - - - - - -");
        promptMainMenu();
    }

    // SEARCH FOR CONTACT IN LIST
    public static void searchContacts() throws IOException {
        String userSearch;
        String userContinue;

        do {
            System.out.println("\n\t- - - - - Search Contacts - - - - -");
            System.out.println("\tEnter Name");
            scanner.nextLine();
            userSearch = scanner.nextLine().toLowerCase();

            Path contactsPath = Paths.get("data", "contacts.txt");
            List<String> contacts = Files.readAllLines(contactsPath);

            for (String contact : contacts) {
                if (contact.toLowerCase().contains(userSearch)) {
                    System.out.println(contact);
                }
            }
            if (!contacts.toString().toLowerCase().contains(userSearch)) {
                System.out.println("Sorry, could not find a contact with that name.");
            }

            System.out.println("Would you like to search again? [Y/N]");
            userContinue = scanner.next();

            if (userContinue.equalsIgnoreCase("y")) {
                searchContacts();
            } else if (userContinue.equalsIgnoreCase("n")) {
                break;
            } else {
                System.out.println("Invalid input. Please enter [Y]es or [N]o.");
            }
        } while (true);
        promptMainMenu();
    }

    // ADD CONTACT TO LIST
    public static void addContact() throws IOException {
        String name;
        String phoneNum;
        String contact;

        System.out.println("Please enter a first name.");
        scanner.nextLine();
        name = scanner.nextLine().trim() + " ";

        System.out.println("Please enter a last name.");
        name += scanner.nextLine().trim()+" | ";

        // testing to say what name looks like
        System.out.println(name);

        System.out.println("Please enter a 10-digit phone number, starting with the area code.");
        phoneNum = scanner.nextLine().trim();

        contact = name + phoneNum;

        //ASK INSTRUCTOR ON MONDAY - stored data in string var 'contact', and added to contacts.txt with 'Arrays.asList' /// Is it better to do it this way, or create a new object and add entry to that before writing to list? pros/cons?
//        List<String> contacts = new ArrayList<>();
//        contacts.add(contact);

        Files.write(contactsPath, Arrays.asList(contact), StandardOpenOption.APPEND);
    }

    // DELETE CONTACT FROM LIST
    public static void deleteContact() {

    }

    // PROMPT TO GO BACK TO MAIN MENU
    public static void promptMainMenu() throws IOException {
        String goToMain;
        String exit;

        System.out.println("\nReturn to Main Menu? [Y/N]");
        goToMain = scanner.next();

        if (goToMain.equalsIgnoreCase("y")) {
            main(null);
        } else if (goToMain.equalsIgnoreCase("n")) {
            System.out.println("Exit program? [Y/N]");
            exit = scanner.next();

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