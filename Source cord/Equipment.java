public class Equipment {
    private String name;
    private String type;
    private int price;
    private double attackModifier;
    private double defenseModifier;
    private double healthModifier;
    private double speedModifier;

    public Equipment(String name, String type, int price, double attackModifier, double defenseModifier, double healthModifier, double speedModifier) {
        this.name = name;
        this.type = type;
        this.price = price;
        this.attackModifier = attackModifier;
        this.defenseModifier = defenseModifier;
        this.healthModifier = healthModifier;
        this.speedModifier = speedModifier;
    }

    // Getters
    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getType() {
        return type;
    }

    public double getAttackModifier() {
        return attackModifier;
    }

    public double getDefenseModifier() {
        return defenseModifier;
    }

    public double getHealthModifier() {
        return healthModifier;
    }

    public double getSpeedModifier() {
        return speedModifier;
    }
}