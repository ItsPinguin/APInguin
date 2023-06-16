package ping.mc.game.profile;

import de.tr7zw.nbtapi.NBTFile;
import org.bukkit.entity.Player;
import ping.Config;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

public class GameProfile extends NBTFile {
    public UUID uuid;
    public GameProfile(String uuid) throws IOException {
        super(new File(Config.PROFILES_DIRECTORY+uuid));
    }
    public void saveData(Player player){
        this.getOrCreateCompound("inventory").setItemStackArray("content",player.getInventory().getContents());
        this.getOrCreateCompound("inventory").setItemStackArray("armor",player.getInventory().getArmorContents());
        this.getOrCreateCompound("inventory").setItemStackArray("extra",player.getInventory().getExtraContents());
    }

    @Override
    public void save() {
        try {
            super.save();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void load(Player player){
        if (this.getOrCreateCompound("inventory").getItemStackArray("content")!=null)
            player.getInventory().setContents(Objects.requireNonNull(this.getOrCreateCompound("inventory").getItemStackArray("content")));
        if (this.getOrCreateCompound("inventory").getItemStackArray("armor")!=null)
            player.getInventory().setArmorContents(Objects.requireNonNull(this.getOrCreateCompound("inventory").getItemStackArray("armor")));
        if (this.getOrCreateCompound("inventory").getItemStackArray("extra")!=null)
            player.getInventory().setExtraContents(Objects.requireNonNull(this.getOrCreateCompound("inventory").getItemStackArray("extra")));
    }
}
