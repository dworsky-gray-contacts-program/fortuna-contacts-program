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
    private static Scanner scanner = new Scanner(System.in);

    // Initialize new ArrayList
    public static List<String> contacts = new ArrayList<>();

    // Establish directory and file names
    // Should never change, so have them as 'final'
    final static String DIRECTORY = "data";
    final static String FILENAME = "contacts.txt";

    // Establish path to directory and file(s)
    static Path dataDirectory = Paths.get(DIRECTORY);
    static Path contactsPath = Paths.get(DIRECTORY, FILENAME);


    // Begin Main
    public static void main(String[] args) throws IOException {

        int menuSelection;

        do {
            try {
                System.out.println("\n- - - - - MAIN MENU - - - - -");
                System.out.println("\t1. View All Contacts");
                System.out.println("\t2. Search Contacts List");
                System.out.println("\t3. Add A Contact");
                System.out.println("\t4. Delete A Contact");
                System.out.println("\tPlease enter an option (1, 2, 3, 4, 5)");
                System.out.println("- - - - - - - - - - - - - - - -");
                menuSelection = scanner.nextInt();
                break;
            } catch (Exception e) {
                System.out.println("Not a valid input - please try again.");
                scanner.next();
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
        contacts = Files.readAllLines(contactsPath);

        System.out.println("\n- - - - - CONTACTS - - - - -");
        System.out.println("\tName | Phone Number");
        for (String contact : contacts) {
            System.out.println("\t" + contact);
        }
        System.out.println("- - - - - - - - - - - - - - -");
        promptMainMenu();
    }

    // SEARCH FOR CONTACT IN LIST
    public static void searchContacts() throws IOException {
        String userSearch;
        String userContinue;
        String results = "";
        contacts = Files.readAllLines(contactsPath);

        do {
            System.out.println("\n- - - - - SEARCH - - - - -");
            System.out.print("Enter Full or Partial Name: ");
            scanner.nextLine();
            userSearch = scanner.nextLine().toLowerCase();


            for (String contact : contacts) {
                if (contact.toLowerCase().contains(userSearch)) {
                    results.concat(contact+"\n");
                }
            }


            if (!results.equals(" ")) {
                System.out.println(results);
            } else {
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
        name += scanner.nextLine().trim() + " | ";

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