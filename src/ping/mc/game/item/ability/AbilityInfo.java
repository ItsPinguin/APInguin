package ping.mc.game.item.ability;

public class AbilityInfo {
    private String id="my_id";
    private String name="My Ability";
    private String description="This is the standard ability.";
    private double manaCost=1;
    private long cooldown=1000;
    private long delay=0;
    private AbilityType abilityType=AbilityType.SHIFT_RIGHT_CLICK;

    public String getId() {
        return id;
    }

    public AbilityInfo setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public AbilityInfo setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public AbilityInfo setDescription(String description) {
        this.description = description;
        return this;
    }

    public double getManaCost() {
        return manaCost;
    }

    public AbilityInfo setManaCost(double manaCost) {
        this.manaCost = manaCost;
        return this;
    }

    public long getCooldown() {
        return cooldown;
    }

    public AbilityInfo setCooldown(long cooldown) {
        this.cooldown = cooldown;
        return this;
    }

    public long getDelay() {
        return delay;
    }

    public AbilityInfo setDelay(long delay) {
        this.delay = delay;
        return this;
    }

    public AbilityType getAbilityType() {
        return abilityType;
    }

    public AbilityInfo setAbilityType(AbilityType abilityType) {
        this.abilityType = abilityType;
        return this;
    }
}
