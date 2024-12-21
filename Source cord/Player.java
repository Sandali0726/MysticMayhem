import java.util.HashMap;
import java.util.Map;

public class Player {
    private String name;
    private String username;
    private int userId;
    private int xp;
    private int goldCoins;
    private String homeground;
    private Map<String, Character> army;



    public Player(String name, String username,int userId) {
        this.name = name;
        this.username = username;
        this.userId = userId;
        this.xp = 0;
        this.homeground = "";
        this.army = new HashMap<>();

    }

    // Getters and setters
    public void setGoldCoins(int goldCoins) {
        this.goldCoins = goldCoins;
    }
    public String getName() {
        return name;
    }
    public String getUsername() {
        return username;
    }

    public int getUserId() {
        return userId;
    }

    public int getXp() {
        return xp;
    }

    public void increaseXp() {
        this.xp++;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public int getGoldCoins() {
        return goldCoins;
    }

    public void addGoldCoins(int amount) {
        this.goldCoins += amount;
    }

    public void deductGoldCoins(int amount) {
        this.goldCoins -= amount;
    }

    public String getHomeground() {
        return homeground;
    }

    public void setHomeground(String homeground) {
        this.homeground = homeground;
    }

    public Map<String, Character> getArmy() {
        return army;
    }
    public void resetArmy(Map<String, Character> originalArmy) {
        this.army = new HashMap<>(originalArmy);
    }

    public void addToArmy(Character character) {
        army.put(character.getType(), character);
    }

    public void printArmy() {
        System.out.println("Player's Army:");
        MysticMayhem.writeOutputToFile("Player's Army:");
        for (Map.Entry<String, Character> entry : army.entrySet()) {
            String type = entry.getKey();
            Character character = entry.getValue();
            System.out.println("Type: " + type + ", Character: " + character.getName());
            MysticMayhem.writeOutputToFile("Type: " + type + ", Character: " + character.getName());
            // You can print other character details as needed
        }
    }


    public void addEquipment(Equipment equipment, String characterName) {
        // Check if the player already has the specified character in their army
        if (army.containsKey(characterName)) {
            // Get the character object
            Character character = army.get(characterName);

            // Check if the player already has equipment of the same type for this character
            if (character.getEquipment().containsKey(equipment.getType())) {
                Equipment existingEquipment = character.getEquipment().get(equipment.getType());

                // Remove the existing equipment modifiers from the character's attributes
                character.setAttack(character.getAttack() - existingEquipment.getAttackModifier());
                character.setDefense(character.getDefense() - existingEquipment.getDefenseModifier());
                character.setHealth(character.getHealth() - existingEquipment.getHealthModifier());
                character.setSpeed(character.getSpeed() - existingEquipment.getSpeedModifier());
            }

            // Apply the new equipment modifiers to the character's attributes
            character.setAttack(character.getAttack() + equipment.getAttackModifier());
            character.setDefense(character.getDefense() + equipment.getDefenseModifier());
            character.setHealth(character.getHealth() + equipment.getHealthModifier());
            character.setSpeed(character.getSpeed() + equipment.getSpeedModifier());

            // Add the new equipment to the character's equipment inventory
            character.setEquipment(equipment.getType(),equipment);

            // Deduct the price of the equipment from the player's gold coins
            deductGoldCoins(equipment.getPrice());

            System.out.println("Equipment " + equipment.getName() + " bought successfully for " + characterName);
            MysticMayhem.writeOutputToFile("Equipment " + equipment.getName() + " bought successfully for " + characterName);
        } 
    }
    public void buyCharacter(Character character) {
        int characterPrice = character.getPrice(); // Store the character's price
        if (this.goldCoins >= characterPrice) {
            this.deductGoldCoins(characterPrice);
            this.addToArmy(character);
            System.out.println("Character " + character.getName() + " bought successfully!");
            MysticMayhem.writeOutputToFile("Character " + character.getName() + " bought successfully!");
        } else {
            System.out.println("Insufficient gold coins to buy " + character.getName() + ".");
            MysticMayhem.writeOutputToFile("Insufficient gold coins to buy " + character.getName() + ".");
            return; // Exit the method if there are insufficient gold coins
        }

        // Print the existing amount of gold coins after the purchase
        System.out.println("Existing amount of gold coins: " + this.goldCoins);
        MysticMayhem.writeOutputToFile("Existing amount of gold coins: " + this.goldCoins);
    }


    public void sellCharacter(String type) {
        if (this.army.containsKey(type)) {
            Character character = this.army.get(type);
            int price = (int) (character.getPrice() * 0.9); // Sell for 90% of original price
            this.goldCoins += price;
            this.army.remove(type);
            System.out.println("Character " + character.getName() + " sold for " + price + " gold coins.");
            MysticMayhem.writeOutputToFile("Character " + character.getName() + " sold for " + price + " gold coins.");
        } else {
            System.out.println("Character of type " + type + " not found in army.");
            MysticMayhem.writeOutputToFile("Character of type " + type + " not found in army.");
        }
    }
}