public class Character {
    private String name;
    private String description;
    private int rarity;
    private String vision;
    private String weapon;

    public Character(String name, String description, int rarity, String vision, String weapon) {
        this.name = name;
        this.description = description;
        this.rarity = rarity;
        this.vision = vision;
        this.weapon = weapon;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getRarity() {
        return rarity;
    }

    public String getVision() {
        return vision;
    }

    public String getWeapon() {
        return weapon;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setRarity(int rarity) {
        this.rarity = rarity;
    }

    public void setVision(String vision) {
        this.vision = vision;
    }

    public void setWeapon(String weapon) {
        this.weapon = weapon;
    }
}
