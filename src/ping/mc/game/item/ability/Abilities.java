package ping.mc.game.item.ability;

import java.util.HashMap;

public class Abilities {
    private static HashMap<String , Ability> abilities = new HashMap<String, Ability>();
    public static void addAbility(Ability ability){
        abilities.put(ability.getAbilityInfo().getId(),ability);
    }

    public static Ability getAbility(String id){
        return abilities.get(id);
    }
}
