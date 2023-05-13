package ping.mc.game.item.type;

public enum Type {
    ITEM("Item", 0),
    MATERIAL("Material", 1),
    SWORD("Sword", 2),
    AXE("Axe",3),
    PICKAXE("Pickaxe",4),
    SHOVEL("Shovel", 5),
    HOE("Hoe", 6),
    SHEARS("Shears", 7),
    DRILL("Drill",8),
    SPEAR("Spear", 9),
    LONGSWORD("Longsword", 10),
    ACCESSORY("Accessory", 11),
    TALISMAN("Talisman", 12),
    ;
    String name="Item";
    int id=0;

    Type(String name, int id) {
        this.name = name;
        this.id = id;
    }
}
