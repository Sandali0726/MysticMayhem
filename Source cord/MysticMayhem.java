import java.util.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;



public class MysticMayhem {
    private static int userIdCounter = 10056;// Static variable to keep track of the next available userID
    private static Scanner scanner = new Scanner(System.in);
    private static List<Player> players = new ArrayList<>();
    private static List<Character> characters = new ArrayList<>();
    private static List<Equipment> equipment = new ArrayList<>();
    public static FileWriter fileWriter;
    static {
        try {
            fileWriter = new FileWriter("OUTPUTSET.txt", true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeOutputToFile(String output) {
        try {
            fileWriter.write(output + "\n");
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void closeFileWriter() {
        try {
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private static void initializeCharacters() {
        characters.add(new Character("Shooter", "Archer", 80, 11, 4, 6, 9));
        characters.add(new Character("Ranger", "Archer", 115, 14, 5, 8, 10));
        characters.add(new Character("Sunfire", "Archer", 160, 15, 5, 7, 14));
        characters.add(new Character("Zing", "Archer", 200, 16, 9, 11, 14));
        characters.add(new Character("Saggitarius", "Archer", 230, 18, 7, 12, 17));

        characters.add(new Character("Squire", "Knight", 85, 8, 9, 7, 8));
        characters.add(new Character("Swiftblade", "Knight", 250, 18, 20, 17, 13));
        characters.add(new Character("Cavalier", "Knight", 110, 10, 12, 7, 10));
        characters.add(new Character("Templar", "Knight", 155, 14, 16, 12, 12));
        characters.add(new Character("Zoro", "Knight", 180, 17, 16, 13, 14));

        characters.add(new Character("Warlock", "Mage", 100, 12, 7, 10, 12));
        characters.add(new Character("Illusionist", "Mage", 120, 13, 8, 12, 14));
        characters.add(new Character("Enchanter", "Mage", 160, 16, 10, 13, 16));
        characters.add(new Character("Conjurer", "Mage", 195, 18, 15, 14, 12));
        characters.add(new Character("Eldritch", "Mage", 270, 19, 17, 18, 14));

        characters.add(new Character("Soother", "Healer", 95, 10, 8, 9, 6));
        characters.add(new Character("Medic", "Healer", 125, 12, 9, 10, 7));
        characters.add(new Character("Saint", "Healer", 200, 16, 14, 17, 9));
        characters.add(new Character("Medusa", "Healer", 165, 14, 16, 15, 12));
        characters.add(new Character("Oracle", "Healer", 185, 15, 12, 16, 10));

        characters.add(new Character("Dragon", "Mythical Creature", 120, 12, 14, 15, 8));
        characters.add(new Character("Basilisk", "Mythical Creature", 165, 15, 11, 10, 12));
        characters.add(new Character("Phoenix", "Mythical Creature", 275, 17, 13, 17, 19));
        characters.add(new Character("Pegasus", "Mythical Creature", 340, 14, 18, 20, 20));
        characters.add(new Character("Hydra", "Mythical Creature", 205, 12, 16, 15, 11));
    }

    private static void initializeEquipments() {
        equipment.add(new Equipment("Chainmail", "Armour", 70, 0.0, 1.0, 0.0, -1.0));
        equipment.add(new Equipment("Excalibur", "Artefact", 150, 2.0, 0.0, 0.0, 0.0));
        equipment.add(new Equipment("Regalia", "Armour", 105, 0.0, 1.0, 0.0, 0.0));
        equipment.add(new Equipment("Amulet", "Artefact", 200, 1.0, -1.0, 1.0, 1.0));
        equipment.add(new Equipment("Fleece", "Armour", 150, 0.0, 2.0, 1.0, -1.0));
        equipment.add(new Equipment("Crystal", "Artefact", 210, 2.0, 1.0, -1.0, -1.0));
    }


    private static void createPlayerProfile() {
        System.out.println("Creating player profile...");
        writeOutputToFile("Creating player profile...");
        System.out.print("Enter your name: ");
        writeOutputToFile("Enter your name: ");
        String name = scanner.nextLine();
        System.out.print("Enter your desired username: ");
        writeOutputToFile("Enter your desired username: ");
        String username = scanner.nextLine();
        while (isUsernameTaken(username)) {
            System.out.println("Username is already taken. Please choose another one.");
            writeOutputToFile("Username is already taken. Please choose another one.");
            System.out.print("Enter your desired username: ");
            writeOutputToFile("Enter your desired username: ");
            username = scanner.nextLine();
        }
        int userId = userIdCounter++;
        System.out.println("Your user ID is: "+userId);
        writeOutputToFile("Your user ID is: "+userId);
        System.out.println("Select Home groud");
        writeOutputToFile("Select Home groud");
        System.out.println(" Hillcrest, Marshland, Desert, and Arcane");
        writeOutputToFile(" Hillcrest, Marshland, Desert, and Arcane");
        System.out.println("Enter the name of home ground:");
        writeOutputToFile("Enter the name of home ground:");
        String homeground = scanner.nextLine();
        Player player = new Player(name, username, userId);
        player.setHomeground(homeground);
        player.setGoldCoins(500); // Award 500 gold coins upon profile creation
        System.out.println("Profile created successfully!");
        writeOutputToFile("Profile created successfully!");
        System.out.println("Your user ID: " + userId);
        writeOutputToFile("Your user ID: " + userId);
        players.add(player);
        chooseArmy(player);
        chooseEquipment(player);


    }
    private static boolean isUsernameTaken(String username) {
        for (Player player : players) {
            if (player.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    private static void chooseArmy(Player player) {
        System.out.println("Choose your army:");
        writeOutputToFile("Choose your army:");

        // Initialize counters for each category
        int archerCount = 0, knightCount = 0, mageCount = 0, healerCount = 0, mythicalCreatureCount = 0;

        for (int i = 0; i < characters.size(); i++) {
            Character character = characters.get(i);
            if (i == 0) {
                System.out.println("Buy one character from Archers");
                writeOutputToFile("Buy one character from Archers");
            } else if (i == 5) {
                System.out.println("Buy one character from Knights");
                writeOutputToFile("Buy one character from Knights");
            } else if (i == 10) {
                System.out.println("Buy one character from Mages");
                writeOutputToFile("Buy one character from Mages");
            } else if (i == 15) {
                System.out.println("Buy one character from Healers");
                writeOutputToFile("Buy one character from Healers");
            } else if (i == 20) {
                System.out.println("Buy one character from Mythical Creatures");
                writeOutputToFile("Buy one character from Mythical Creatures");
            }

            System.out.println((i + 1) + ". " + character.getName() + " - Price: " + character.getPrice());
            writeOutputToFile((i + 1) + ". " + character.getName() + " - Price: " + character.getPrice());
        }
        System.out.println("There should be five members in your army\n");
        writeOutputToFile("There should be five members in your army\n");
        System.out.println("You have to buy exactly one character from each category!\n");
        writeOutputToFile("You have to buy exactly one character from each category!\n");
        System.out.print("Enter the numbers of characters you want to add to your army (separated by spaces): ");
        writeOutputToFile("Enter the numbers of characters you want to add to your army (separated by spaces): ");
        String choices = scanner.nextLine();
        String[] choiceArray = choices.split(" ");
        Set<Integer> chosenCategories = new HashSet<>();
        int selectedCharacterCount = 0; // Counter for selected characters

        for (String choice : choiceArray) {
            try {
                int index = Integer.parseInt(choice) - 1;
                if (index >= 0 && index < characters.size()) {
                    Character selectedCharacter = characters.get(index);
                    // Check if the selected character belongs to an already chosen category
                    int categoryIndex = index / 5;
                    if (chosenCategories.contains(categoryIndex)) {
                        System.out.println("You can only select one character from each category.");
                        writeOutputToFile("You can only select one character from each category.");
                        return; // Exit method if the user tries to select another character from the same category
                    }
                    // Update category counter and add selected character to the player's army
                    switch (categoryIndex) {
                        case 0:
                            archerCount++;
                            break;
                        case 1:
                            knightCount++;
                            break;
                        case 2:
                            mageCount++;
                            break;
                        case 3:
                            healerCount++;
                            break;
                        case 4:
                            mythicalCreatureCount++;
                            break;
                    }
                    chosenCategories.add(categoryIndex);
                    player.addToArmy(selectedCharacter);
                    player.buyCharacter(selectedCharacter);
                    selectedCharacterCount++; // Increment selected character count
                } else {
                    System.out.println("Invalid choice: " + (index + 1));
                    writeOutputToFile("Invalid choice: " + (index + 1));
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input: " + choice);
                writeOutputToFile("Invalid input: " + choice);
            }
        }

        // Check if the user has selected exactly one character from each category
        if (archerCount != 1 || knightCount != 1 || mageCount != 1 || healerCount != 1 || mythicalCreatureCount != 1) {
            System.out.println("You must select exactly one character from each category.");
            writeOutputToFile("You must select exactly one character from each category.");
            return; // Exit method if the user hasn't selected one character from each category
        }

        // Print the remaining characters to be bought if the selected character count is less than five
        if (selectedCharacterCount < 5) {
            int remainingCharacters = 5 - selectedCharacterCount;
            System.out.println("You need to buy a one character from " + remainingCharacters);
            writeOutputToFile("You need to buy a one character from " + remainingCharacters);
        }
    }


    private static void chooseEquipment(Player player) {
        if (player.getGoldCoins() < 70) {
            System.out.println("Your balance is insufficient to buy equipment!");
            writeOutputToFile("Your balance is insufficient to buy equipment!");
        }
        else {
            System.out.print("Enter the name of the character you want to buy equipment for: ");
            writeOutputToFile("Enter the name of the character you want to buy equipment for: ");
            String characterName = scanner.nextLine();

            // Check if the player has the specified character in their army
            if (player.getArmy().containsKey(characterName)) {
                System.out.println("You can buy exactly one equipment from each category for the character: " + characterName);
                writeOutputToFile("You can buy exactly one equipment from each category for the character: " + characterName);
                System.out.println("Choose equipment:");
                writeOutputToFile("Choose equipment:");

                // Display equipment options
                int i=0;
                for (Equipment equip : equipment) {
                    if (equip.getType().equals("Armour")) {
                        System.out.println("Buy equipment from Armour");
                        writeOutputToFile("Buy equipment from Armour");
                    } else if (equip.getType().equals("Artefact")) {
                        System.out.println("Buy equipment from Artefact");
                        writeOutputToFile("Buy equipment from Artefact");
                    }
                    i++;
                    System.out.println((i+"-") + equip.getName() + " - Price: " + equip.getPrice());
                    writeOutputToFile((i+"-") + equip.getName() + " - Price: " + equip.getPrice());
                }

                System.out.print("Enter the number of the equipment you want to add to your inventory: ");
                writeOutputToFile("Enter the number of the equipment you want to add to your inventory: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline character

                // Validate choice
                if (choice > 0 && choice <= equipment.size()) {
                    Equipment selectedEquipment = equipment.get(choice - 1);
                    player.addEquipment(selectedEquipment, characterName);
                } else {
                    System.out.println("Invalid choice!");
                    writeOutputToFile("Invalid choice!");
                }
            } 
        }
    }

    private static Player createOpponent() {
        Player opponent = new Player("GeraltofRivia", "whitewolf", userIdCounter++);
        opponent.setXp(32);
        opponent.setGoldCoins(215);
        opponent.setHomeground("Marshland");

        // Adding characters to whitewolf's army
        opponent.addToArmy(new Character("Ranger", "Archer", 115, 14, 5, 8, 10));
        opponent.addToArmy(new Character("Squire", "Knight", 85, 8, 9, 7, 8));
        opponent.addToArmy(new Character("Warlock", "Mage", 100, 12, 7, 10, 12));
        opponent.addToArmy(new Character("Medic", "Healer", 125, 12, 9, 10, 7));
        opponent.addToArmy(new Character("Dragon", "Mythical Creature", 120, 12, 14, 15, 8));

        // Adding equipment to whitewolf
        opponent.addEquipment(new Equipment("Chainmail", "Armour", 70, 0.0, 1.0, 0.0, -1.0),"Ranger" );
        opponent.addEquipment(new Equipment("Amulet", "Artefact", 200, 1.0, -1.0, 1.0, 1.0), "Medic" );
        players.add(opponent);

        // Generate 10 random opponents
        Random random = new Random();
        List<String> A = new ArrayList<>();
        A.add("John");
        A.add("David");
        A.add("Ravi");
        A.add("Gatheesh");
        A.add("Satheesha");
        A.add("Isabel");
        List<String> B = new ArrayList<>();
        B.add("John");
        B.add("David");
        B.add("Ravi");
        B.add("Gatheesh");
        B.add("Satheesha");
        B.add("Isabel");
        for (int i = 0; i < 6; i++) {
            Player randomOpponent = new Player(A.get(i), B.get(i), userIdCounter++);
            randomOpponent.setXp(random.nextInt(100) + 1); // Random XP between 1 and 100
            randomOpponent.setGoldCoins(random.nextInt(500) + 1); // Random gold coins between 1 and 500
            // Set a random homeground from the existing options
            String[] homegrounds = {"Hillcrest", "Marshland", "Desert", "Arcane"};
            randomOpponent.setHomeground(homegrounds[random.nextInt(homegrounds.length)]);
            // Randomly add characters and equipment to the opponent (similar to whitewolf)
            // Note: You may need to adjust the logic based on your requirements
            List<Character> randomCharacters = new ArrayList<>(characters); // Copy original list
            Collections.shuffle(randomCharacters); // Shuffle to get random characters
            for (int j = 0; j < 5; j++) {
                Character randomCharacter = randomCharacters.get(j);
                randomOpponent.addToArmy(new Character(randomCharacter.getName(), randomCharacter.getType(), randomCharacter.getPrice(),
                        randomCharacter.getHealth(), randomCharacter.getAttack(),
                        randomCharacter.getDefense(), randomCharacter.getSpeed()));
            }
            players.add(randomOpponent);
        }
        return opponent;
    }

    private static void findOpponent(Player player) {
        System.out.println("Searching for opponents...");
        writeOutputToFile("Searching for opponents...");
        Player opponent = getRandomOpponent(player);
        displayOpponentStats(opponent);
        promptForBattle(player, opponent);
    }

    // Method to get a random opponent
    private static Player getRandomOpponent(Player player) {
        Random random = new Random();
        int index = random.nextInt(players.size());
        while (players.get(index) == player) {
            index = random.nextInt(players.size());
        }
        return players.get(index);
    }

    // Method to display opponent statistics
    private static void displayOpponentStats(Player opponent) {
        System.out.println("Opponent: " + opponent.getUsername());
        writeOutputToFile("Opponent: " + opponent.getUsername());
        System.out.println("XP Level: " + opponent.getXp());
        writeOutputToFile("XP Level: " + opponent.getXp());

    }

    // Method to prompt for battle
    private static void promptForBattle(Player player, Player opponent) {
        System.out.println("Do you want to challenge " + opponent.getUsername() + " to battle? (Y/N)");
        writeOutputToFile("Do you want to challenge " + opponent.getUsername() + " to battle? (Y/N)");
        String choice = scanner.nextLine();
        if (choice.equalsIgnoreCase("Y")) {
            // Start battle with opponent
            Battle battle = new Battle(player, opponent);
            battle.startBattle();
        } else {
            // Look for another opponent
            findOpponent(player);
        }
    }
    private static void playAnotherRound(Player player) {
        Scanner scanner = new Scanner(System.in);
        boolean flag=true;
        while(flag) {
            System.out.print("Do you want to play another round? (yes/no): ");
            writeOutputToFile("Do you want to play another round? (yes/no): ");
            String choice_1 = scanner.nextLine().toLowerCase();
            if(choice_1.equals("yes")){
                System.out.println("Do you want to buy new characters or equipment? (yes/no)");
                writeOutputToFile("Do you want to buy new characters or equipment? (yes/no)");
                String buyChoice = scanner.nextLine().toLowerCase();
                if (buyChoice.equals("yes")) {
                    System.out.println("Do you want to buy new characters or equipment? (characters/equipment)");
                    writeOutputToFile("Do you want to buy new characters or equipment? (characters/equipment)");
                    String buyOption = scanner.nextLine().toLowerCase();
                    if (buyOption.equals("characters")) {
                        chooseArmy(player);
                    } else if (buyOption.equals("equipment")) {
                        chooseEquipment(player);
                    }
                }
                System.out.println("Do you want to sell your characters? (yes/no)");
                writeOutputToFile("Do you want to sell your characters? (yes/no)");
                String sellChoice = scanner.nextLine().toLowerCase();
                if (sellChoice.equals("yes")) {
                    System.out.println("Enter the type of the character that you want to sell");
                    writeOutputToFile("Enter the type of the character that you want to sell");
                    String sellChoice_1= scanner.nextLine().toLowerCase();
                    player.sellCharacter(sellChoice_1);
                }

                Player opponent = getRandomOpponent(player);
                findOpponent(player);
            }
            else{
                flag=false;
            }

        }
        System.out.print("Game Over! ");
        writeOutputToFile("Game Over! ");
    }

    public static void main(String[] args) {
        printGameTitle();
    }

    public static void printGameTitle() {
        System.out.println("             *                      *  *");
        writeOutputToFile("             *                      *  *");
        System.out.println("  *     *               *   * *     *     *");
        writeOutputToFile("  *     *               *   * *     *     *");
        System.out.println("               *            *          *");
        writeOutputToFile("               *            *          *");
        System.out.println("*    *   *            MYSTIC MAYHEM      *");
        writeOutputToFile("*    *   *            MYSTIC MAYHEM      *");
        System.out.println("              *      *                *");
        writeOutputToFile("              *      *                *");
        System.out.println("        *        * *    *     *");
        writeOutputToFile("        *        * *    *     *");
        System.out.println(" *       *  * *               * *   *");
        writeOutputToFile(" *       *  * *               * *   *");
        System.out.println("           *   *         *");
        writeOutputToFile("           *   *         *");
        System.out.println("      *                *");
        writeOutputToFile("      *                *");
        System.out.println(" *                       *  *");
        writeOutputToFile(" *                       *  *");
        System.out.println("       *     *            *");
        writeOutputToFile("       *     *            *");

        initializeCharacters();
        initializeEquipments();
        createPlayerProfile();
        createOpponent();
        findOpponent(players.get(0));
        playAnotherRound(players.get(0));


    }
}