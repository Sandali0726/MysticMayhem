import java.util.Map;
import java.util.HashMap;

class Character {
    private String name;
    private String type;
    private int price;
    private double attack;
    private double defense;
    private double health;
    private double speed;
    private Map<String, Equipment> equipment;


    public Character(String name, String type, int price, double attack, double defense, double health, double speed) {
        this.name = name;
        this.type = type;
        this.price = price;
        this.attack = attack;
        this.defense = defense;
        this.health = health;
        this.speed = speed;
        this.equipment = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public double getAttack() {
        return attack;
    }

    public void setAttack(double attack) {
        this.attack = attack;
    }

    public double getDefense() {
        return defense;
    }

    public void setDefense(double defense) {
        this.defense = defense;
    }

    public double getHealth() {
        return health;
    }
    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setHealth(double health) {
        this.health = Math.round(health * 10.0) / 10.0;
    }
    public void setEquipment(String type, Equipment equipmentName) {
        this.equipment.put(type, equipmentName);
    }

    public Map<String, Equipment> getEquipment() {
        return equipment;
    }

    public void applyHomeGroundEffects(String homeground) {
        switch (homeground) {
            case "Hillcrest":
                if (type.equals("Highlanders")) {
                    attack += 1;
                    defense += 1;
                } else if (type.equals("Marshlanders") || type.equals("SunChildren")) {
                    speed -= 1;
                }
                break;
            case "Marshland":
                if (type.equals("Marshlanders")) {
                    defense += 2;
                } else if (type.equals("SunChildren")) {
                    attack -= 1;
                } else if (type.equals("Mystics")) {
                    speed -= 1;
                }
                break;
            case "Desert":
                if (type.equals("Marshlanders")) {
                    health -= 1;
                } else if (type.equals("SunChildren")) {
                    attack += 1;
                }
                break;
            case "Arcane":
                if (type.equals("Mystics")) {
                    attack += 2;
                    speed -= 1;
                    defense -= 1;
                }
                break;
        }
    }
}