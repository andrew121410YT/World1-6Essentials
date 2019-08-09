package World16.Utils;

import World16.Main.Main;
import net.minecraft.server.v1_12_R1.*;
import org.bukkit.Location;
import org.bukkit.block.Sign;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class SignUtils {

    private Main plugin;

    private API api;

    public SignUtils(Main plugin) {
        this.plugin = plugin;
        this.api = this.plugin.getApi();
    }

    public void edit(Player player, Sign sign) {
        for (int i = 0; i < 4; ++i)
            // Removes all color from the sign, pretty buggy if you don't
            sign.setLine(i, sign.getLine(i).replace("§", "&"));
        // Updates the sign so we open it after the color replace takes effect
        sign.update();

        Location loc = sign.getLocation();
        BlockPosition pos = new BlockPosition(loc.getX(), loc.getY(), loc.getZ());
        EntityPlayer nmsPlayer = ((CraftPlayer) player).getHandle();
        TileEntitySign tileEntitySign = (TileEntitySign) nmsPlayer.world.getTileEntity(pos);
        PlayerConnection conn = nmsPlayer.playerConnection;

        tileEntitySign.isEditable = true;
        tileEntitySign.a(nmsPlayer);
        conn.sendPacket(new PacketPlayOutOpenSignEditor(pos));
    }
}
