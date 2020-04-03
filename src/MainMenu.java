import java.util.Scanner;

public class MainMenu {

    public static void main(String[] args) {

// 2 files min - main and contacts
// HashMap to store contacts - key = name, value = phone number
// Create main menu
// record keypress

        Scanner scanner = new Scanner(System.in);


        System.out.println("\n- - - - - Main Menu - - - - -");
        System.out.println("1. View All Contacts");
        System.out.println("2. Search Contacts List");
        System.out.println("3. Add A Contact");
        System.out.println("4. Delete A Contact");
        System.out.println("Please enter an option (1, 2, 3, 4, 5)");
        System.out.println("- - - - - - - - - - - - - - - -");

        do {
            try {
                int menuSelection = scanner.nextInt();
                break;
            }
            catch (Exception e)
            {
                System.out.println("Not a valid input - please try again.");
            }
        }
        while (true);


    }
}
