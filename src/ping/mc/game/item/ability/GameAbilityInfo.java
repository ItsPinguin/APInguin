package ping.mc.game.item.ability;

public class GameAbilityInfo {
    private String id="my_id";
    private String name="My Ability";
    private String description="This is the standard ability.";
    private double manaCost=1;
    private long cooldown=1000;
    private long delay=0;
    private GameAbilityType abilityType= GameAbilityType.SHIFT_RIGHT_CLICK;

    public String getId() {
        return id;
    }

    public GameAbilityInfo setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public GameAbilityInfo setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public GameAbilityInfo setDescription(String description) {
        this.description = description;
        return this;
    }

    public double getManaCost() {
        return manaCost;
    }

    public GameAbilityInfo setManaCost(double manaCost) {
        this.manaCost = manaCost;
        return this;
    }

    public long getCooldown() {
        return cooldown;
    }

    public GameAbilityInfo setCooldown(long cooldown) {
        this.cooldown = cooldown;
        return this;
    }

    public long getDelay() {
        return delay;
    }

    public GameAbilityInfo setDelay(long delay) {
        this.delay = delay;
        return this;
    }

    public GameAbilityType getAbilityType() {
        return abilityType;
    }

    public GameAbilityInfo setAbilityType(GameAbilityType abilityType) {
        this.abilityType = abilityType;
        return this;
    }
}
