import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class Battle {
    private Player attacker;
    private Player defender;
    private String homeground;

    public Battle(Player player1, Player player2) {
        this.attacker = player1;
        this.defender = player2;
        this.homeground = player2.getHomeground();
    }

    public void setHomeground(String homeground) {
        this.homeground = homeground;
    }

    public void startBattle() {
        //save the army of the two players before battle
        Map<String, Character> originalAttackerArmy = new HashMap<>(attacker.getArmy());
        Map<String, Character> originalDefenderArmy = new HashMap<>(defender.getArmy());

        System.out.println("Battle begins at " + homeground + "!");
        MysticMayhem.writeOutputToFile("Battle begins at " + homeground + "!");
        applyHomeGroundEffects(attacker);
        applyHomeGroundEffects(defender);
        System.out.println(attacker.getName() + " vs. " + defender.getName());
        MysticMayhem.writeOutputToFile(attacker.getName() + " vs. " + defender.getName());
        for (int i = 1; i <= 10; i++) {
            System.out.println("Turn " + i + ": " + attacker.getName());
            MysticMayhem.writeOutputToFile("Turn " + i + ": " + attacker.getName());

            // Get characters of both players
            Map<String, Character> attackerArmy = attacker.getArmy();
            Map<String, Character> defenderArmy = defender.getArmy();

            // Determine attackers and defenders based on speed and priority order
            List<Character> attackerSorted = sortCharactersBySpeedAndPriority(attackerArmy, defenderArmy);
            List<Character> defenderSorted = sortCharactersBySpeedAndPriority(defenderArmy, attackerArmy);

            // Check if attacker or defender has no characters left
            if (attackerSorted.isEmpty() || defenderSorted.isEmpty()) {
                break; // No characters left, end the battle
            }

            // Perform attacks
            for (int j = 0; j < Math.min(attackerSorted.size(), defenderSorted.size()); j++) {
                Character attackerChar = attackerSorted.get(j);
                Character defenderChar = defenderSorted.get(j);
                double damage = calculateDamage(attackerChar, defenderChar);
                if (attackerChar.getType().equals("Healer")  && attackerChar instanceof Healer) {
                    ((Healer) attackerChar).healLowestHealthCharacter(attackerArmy);
                } else {
                    defenderChar.setHealth(defenderChar.getHealth() - damage);
                    System.out.println(attackerChar.getType() + " attacks " + defenderChar.getType() + ".");
                    MysticMayhem.writeOutputToFile(attackerChar.getType() + " attacks " + defenderChar.getType() + ".");
                    System.out.println(defenderChar.getType() + "'s health: " + String.format("%.1f", defenderChar.getHealth()));
                    MysticMayhem.writeOutputToFile(defenderChar.getType() + "'s health: " + String.format("%.1f", defenderChar.getHealth()));
                    System.out.println(attackerChar.getType() + "'s health: " + String.format("%.1f", attackerChar.getHealth()));
                    MysticMayhem.writeOutputToFile(attackerChar.getType() + "'s health: " + String.format("%.1f", attackerChar.getHealth()));

                    if (defenderChar.getHealth() <= 0) {
                        System.out.println(defenderChar.getType() + " died!");
                        MysticMayhem.writeOutputToFile(defenderChar.getType() + " died!");
                        defenderArmy.remove(defenderChar.getType());
                    }
                }
            }

            // Apply homeground effects again after each turn
            applyHomeGroundEffects(attacker);
            applyHomeGroundEffects(defender);

            // Swap attacker and defender for the next turn
            Player temp = attacker;
            attacker = defender;
            defender = temp;
        }

        Player winner = attacker.getArmy().isEmpty() ? defender : attacker;
        Player loser = winner == attacker ? defender : attacker;

        // Calculate coins won by the winner
        int coinsWon = (int) (loser.getGoldCoins() * 0.10);

        // Deduct coins from the loser
        loser.deductGoldCoins(coinsWon);

        // Increase coins for the winner
        winner.addGoldCoins(coinsWon);

        // Increase XP for the winner
        winner.increaseXp();

        System.out.println(winner.getName() + " won!");
        MysticMayhem.writeOutputToFile(winner.getName() + " won!");
        System.out.println(winner.getName() + " XP: " + winner.getXp() + ", gold coins: " + winner.getGoldCoins());
        MysticMayhem.writeOutputToFile(winner.getName() + " XP: " + winner.getXp() + ", gold coins: " + winner.getGoldCoins());
        System.out.println(loser.getName() + " XP: " + loser.getXp() + ", gold coins: " + loser.getGoldCoins());
        MysticMayhem.writeOutputToFile(loser.getName() + " XP: " + loser.getXp() + ", gold coins: " + loser.getGoldCoins());

        //reset the army affter the battle
        winner.resetArmy(originalAttackerArmy);
        loser.resetArmy(originalDefenderArmy);

    }

    private void applyHomeGroundEffects(Player player) {
        for (Character character : player.getArmy().values()) {
            character.applyHomeGroundEffects(homeground);
        }
    }

    private List<Character> sortCharactersBySpeedAndPriority(Map<String, Character> army, Map<String, Character> opponentArmy) {
        List<Character> sorted = new ArrayList<>(army.values());
        sorted.sort(Comparator.comparing(Character::getSpeed).reversed()
                .thenComparing(character -> {
                    if (character instanceof Healer) return 0;
                    if (character instanceof Mage) return 1;
                    if (character instanceof MythicalCreature) return 2;
                    if (character instanceof Knight) return 3;
                    return 4; // Archer
                })
                .thenComparing(character -> {
                    Character opponent = getLowestDefenseCharacter(opponentArmy);
                    if (opponent != null) {
                        if (character instanceof Mage) return 0;
                        if (character instanceof Knight) return 1;
                        if (character instanceof Archer) return 2;
                        if (character instanceof MythicalCreature) return 3;
                        return 4; // Healer
                    }
                    return 0;
                }));
        return sorted;
    }
    private Character getLowestDefenseCharacter(Map<String, Character> army) {
        return army.values().stream()
                .min(Comparator.comparing(Character::getDefense))
                .orElse(null);
    }


    private double calculateDamage(Character attacker, Character defender) {
        double damage = Math.round((0.5 * attacker.getAttack() - 0.1 * defender.getDefense()) * 10.0) / 10.0;
        return Math.max(0, damage); // Damage cannot be negative
    }
}

// Healer class
class Healer extends Character {
    public Healer(String name, String type, int price, double attack, double defense, double health, double speed) {
        super(name, type, price, attack, defense, health, speed);
    }

    public void healLowestHealthCharacter(Map<String, Character> army) {
        // Find the character with the lowest health
        double lowestHealth = Double.MAX_VALUE;
        Character lowestHealthCharacter = null;
        for (Character character : army.values()) {
            if (character.getHealth() < lowestHealth) {
                lowestHealth = character.getHealth();
                lowestHealthCharacter = character;
            }
        }

        // Heal the lowest health character
        if (lowestHealthCharacter != null) {
            lowestHealthCharacter.setHealth(lowestHealthCharacter.getHealth() + 0.1 * this.getAttack());
            System.out.println("Healer heals " + lowestHealthCharacter.getType() + ".");
            MysticMayhem.writeOutputToFile("Healer heals " + lowestHealthCharacter.getType() + ".");
            System.out.println(lowestHealthCharacter.getType() + "'s health: " + String.format("%.1f", lowestHealthCharacter.getHealth()));
            MysticMayhem.writeOutputToFile(lowestHealthCharacter.getType() + "'s health: " + String.format("%.1f", lowestHealthCharacter.getHealth()));
        }
    }
}
// Mage class
class Mage extends Character {
    public Mage(String name, String type, int price, double attack, double defense, double health, double speed) {
        super(name, type, price, attack, defense, health, speed);
    }
}

// MythicalCreature class
class MythicalCreature extends Character {
    public MythicalCreature(String name, String type, int price, double attack, double defense, double health, double speed) {
        super(name, type, price, attack, defense, health, speed);
    }
}

// Knight class
class Knight extends Character {
    public Knight(String name, String type, int price, double attack, double defense, double health, double speed) {
        super(name, type, price, attack, defense, health, speed);
    }
}

// Archer class
class Archer extends Character {
    public Archer(String name, String type, int price, double attack, double defense, double health, double speed) {
        super(name, type, price, attack, defense, health, speed);
    }
}