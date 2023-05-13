package ping.mc.game.rarity;

public enum  Rarity {
    COMMON("Common","§f",0),
    UNCOMMON("Uncommon","§a", 1),
    RARE("Rare", "§1", 2),
    EPIC("Epic", "§5", 3),
    LEGENDARY("Legendary", "§6", 4),
    MYTHICAL("Mythic", "§d", 5),
    ANCIENT("Ancient", "§2", 6),

    EXOTIC("Exotic", "§b", 9),
    SPECIAL("Special", "§c", 9),
    ILLEGAL("Illegal", "§4", 9);
    private String name="Common";
    private String effect = "§f";
    int rank=0;
    Rarity(){}
    Rarity(String name, String effect, int rank) {
        this.name = name;
        this.effect = effect;
        this.rank = rank;
    }
    Rarity(String name, String effect) {
        this.name = name;
        this.effect = effect;
    }

    public String getName() {
        return name;
    }

    public Rarity setName(String name) {
        this.name = name;
        return  this;
    }

    public String getEffect() {
        return effect;
    }

    public Rarity setEffect(String effect) {
        this.effect = effect;
        return this;
    }

    public int getRank() {
        return rank;
    }

    public Rarity setRank(int rank) {
        this.rank = rank;
        return this;
    }
}
