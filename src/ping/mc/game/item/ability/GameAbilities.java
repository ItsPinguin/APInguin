package ping.mc.game.item.ability;

import java.util.HashMap;

public class GameAbilities {
    private static HashMap<String , GameAbility> abilities = new HashMap<String, GameAbility>();
    public static void addAbility(GameAbility ability){
        abilities.put(ability.getAbilityInfo().getId(),ability);
    }

    public static GameAbility getAbility(String id){
        return abilities.get(id);
    }
}
