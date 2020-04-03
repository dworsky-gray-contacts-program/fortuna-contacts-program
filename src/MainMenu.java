import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainMenu {

//    public static HashMap<String, Integer> contactsList() {
//
//        HashMap<String, Integer>contacts = new HashMap<>();
//
//        contacts.put("John Doe", 1234567881);
//        contacts.put("Jane Doe", 1569012833);
//
//        // Show all contacts - loop through hashmap, output all key-value contacts
//
//        // METHOD 1
//        System.out.println("\n- - - All Contacts - -");
//        System.out.printf("Name ");
//        for (Map.Entry<String, Integer> contact : contacts.entrySet()) {
//            System.out.println("Name: "+contact.getKey()+ "     Phone: "+contact.getValue());
//        }
//        System.out.println("- - - - - - -");
//
//        // need to not display return below
//        return contacts;
//    }

    public static void main(String[] args) throws IOException {

        Scanner scanner = new Scanner(System.in);

        // Establishing directory and file names
        String directory = "data";
        String filename = "contacttest.txt";

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
//         Create new ArrayList
        List<String> contacts = new ArrayList<>();


//         Add to List
        contacts.add("John Doe 1234567881");
        contacts.add("Jane Doe 1569012833");

//         Write to contacts.txt
        Files.write(contactsPath, contacts);

//         Output contacts.txt


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

        // test
        System.out.println(menuSelection);

//        if (menuSelection == 1) {
//            System.out.println(//need to enter code here);
//        }


    }
}