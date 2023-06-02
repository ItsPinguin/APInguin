package ping.mc.game.item;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import ping.mc.game.attribute.GameAttribute;
import ping.mc.game.attribute.GameAttributeModifier;
import ping.mc.game.attribute.GameAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DefaultItemBuilder implements ping.mc.game.item.ItemBuilder {
    @Override
    public ItemStack build(ItemStack itemStack) {
        GameItem gameItem=new GameItem(itemStack);
        if (!gameItem.isGameItem()){
            return itemStack;
        }
        shiny(gameItem);
        ItemMeta itemMeta = itemStack.getItemMeta();
        assert itemMeta != null;
        itemMeta.setUnbreakable(true);
        itemMeta.setDisplayName(name(gameItem));
        List<String> lore=new ArrayList<>();
        lore.addAll(stats(gameItem.getGameItemBase().getAttributes()));
        lore.addAll(description(gameItem.getGameItemBase().getDescription()));
        lore.add(typeAndRarity(gameItem));
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static String name(GameItem gameItem){
        return gameItem.gameItemBase.getRarity().getEffect()+gameItem.getGameItemBase().getName();
    }

    public static String typeAndRarity(GameItem gameItem){
        return gameItem.gameItemBase.getRarity().getEffect()+"§l"+gameItem.getGameItemBase().getRarity().getName()+" "+gameItem.getGameItemBase().getType().getName();
    }

    public static List<String> stats(List<GameAttributeModifier> stats){
        List<String> statLore=new ArrayList<>();
        if (stats==null){
            return statLore;
        }
        for (GameAttribute attribute : GameAttributes.getAttributes().values()){
            for (GameAttributeModifier stat : stats) {
                if (Objects.equals(stat.getAttribute().getId(), attribute.getId())){
                    statLore.add("§7"+attribute.getName()+": "+GameAttributes.getAttributeBuilder().build(stat));
                }
            }
        }
        statLore.add("");
        return statLore;
    }

    public static List<String> description(String description){
        List<String> descriptionList=new ArrayList<>();
        if (description==null){
            return descriptionList;
        }
        description = description.replaceAll("(.{1,32})(\\s|$)", "§8§o$1\n");
        descriptionList.addAll(List.of(description.split("\n")));
        return descriptionList;
    }
    public static GameItem shiny(GameItem item){
        if (item.getGameItemBase().isShiny()){
            ItemMeta itm=item.getItemStack().getItemMeta();
            itm.addEnchant(Enchantment.LUCK,1,true);
            item.getItemStack().setItemMeta(itm);
            return item;
        }
        return item;
    }
}
