public class Weapon {
    private String name;
    private int rarity;
    private String type;

    public Weapon(String name, int rarity, String type) {
        this.name = name;
        this.rarity = rarity;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public int getRarity() {
        return rarity;
    }

    public String getType() {
        return type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRarity(int rarity) {
        this.rarity = rarity;
    }

    public void setType(String type) {
        this.type = type;
    }
}
