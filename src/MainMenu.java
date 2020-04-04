import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainMenu {

    public static void main(String[] args) throws IOException {

        Scanner scanner = new Scanner(System.in);

        // Establishing directory and file names
        final String directory = "data";
        final String filename = "contacts.txt";

        // Establish paths to directory and files within directory
        Path dataDirectory = Paths.get(directory);
        Path contactsPath = Paths.get(directory, filename);

        // Check if pathway to directory exists - if it doesn't, create it
        if (Files.notExists(dataDirectory)) {
            Files.createDirectories(dataDirectory);
        }
        // Check if file in directory exists - if it doesn't, create it
        if (!Files.exists(contactsPath)) {
            Files.createFile(contactsPath);
        }


        // METHOD USING ARRAYLIST TO STORE INFORMATION
        // Create new ArrayList
        List<String> contacts = new ArrayList<>();
        // Add to List
        contacts.add("John Doe | 1234567881");
        contacts.add("Jane Doe | 1569012833");

        // Write to contacts.txt
        Files.write(contactsPath, contacts);



        int menuSelection = 0;
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

        // Switch statement for options
        switch (menuSelection) {
            case 1:
                viewAll();
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
        }
    }

    // VIEW ALL CONTACTS IN LIST
    public static void viewAll() throws IOException {
        Path contactsPath = Paths.get("data", "contacts.txt");
        List<String> contacts = Files.readAllLines(contactsPath);
        System.out.println("\n\t- - - - - All Contacts - - - - -");
        for (String contact : contacts) {
            System.out.println("\t\t"+contact);
        }
        System.out.println("\t- - - - - - - - - - - - - - - -");
        promptMainMenu();
    }

    // PROMPT TO GO BACK TO MAIN MENU
    public static void promptMainMenu() throws IOException {
        Scanner scanner = new Scanner(System.in);
        String goToMain;

        do {
            System.out.println("\nReturn to Main Menu? [Y/N]");
            goToMain = scanner.next();

            if (goToMain.equalsIgnoreCase("y")) {
                main(null);
                break;
            } else if (goToMain.equalsIgnoreCase("n")) {
                System.out.println("Exiting program. Goodbye.");
                break;
            } else {
                System.out.println("Invalid entry.");
            }
        } while (true);
    }
}