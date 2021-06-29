package io.ayushchivate.github.claimplugin;

import net.dohaw.corelib.StringUtils;
import org.bukkit.Chunk;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class FlagsCommand implements CommandExecutor {

    ClaimPlugin plugin;

    public FlagsCommand(ClaimPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player)) {
            return false;
        }

        Player player = (Player) sender;

        Chunk chunk = player.getLocation().getChunk();
        ClaimedChunkLocation claimedChunkLocation = new ClaimedChunkLocation(chunk);
        ClaimedChunk claimedChunk = ClaimedChunks.getClaimedChunks().get(claimedChunkLocation);

        if (claimedChunk == null) {
            player.sendMessage(StringUtils.colorString("&cThis chunk is not claimed"));
            return false;
        }

        if (!(claimedChunk.isOwner(player))) {
            player.sendMessage(StringUtils.colorString("&cYou do not own this chunk!"));
            return false;
        }

        FlagsMenu flagsMenu = new FlagsMenu(this.plugin, claimedChunk);

        flagsMenu.initializeItems(player);

        flagsMenu.openInventory(player);

        return true;
    }
}
