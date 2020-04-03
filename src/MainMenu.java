import java.util.*;

public class MainMenu {

    // Store contacts in HashMap because - store 2 different data types in key-value pair, target either the key or the value in the search function, and we can 'clear'
    public static HashMap<String, Integer> contactsList() {

        HashMap<String, Integer>contacts = new HashMap<>();

        contacts.put("John Doe", 1234567881);
        contacts.put("Jane Doe", 1569012833);

        // Show all contacts - loop through hashmap, output all key-value contacts
        // METHOD 1
//        for (Object name : contacts.keySet()) {
//            System.out.println(name);
//        }

        // METHOD 2
        System.out.println("\n- - - All Contacts - -");
        for (Map.Entry<String, Integer> contact : contacts.entrySet()) {
            System.out.println("Name: "+contact.getKey()+ "     Phone: "+contact.getValue());
        }
        System.out.println("- - - - - - -");
        return contacts;
    }

    public static void main(String[] args) {

// 2 files min - main and contacts
// HashMap to store contacts - key = name, value = phone number


        Scanner scanner = new Scanner(System.in);



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

        if (menuSelection == 1) {
            System.out.println(contactsList());
        }


    }
}
