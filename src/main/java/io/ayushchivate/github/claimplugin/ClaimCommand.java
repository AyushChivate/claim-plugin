package io.ayushchivate.github.claimplugin;

import net.dohaw.corelib.StringUtils;
import org.bukkit.Chunk;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

public class ClaimCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        /* make sure only players can use the command */
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command!");
            return false;
        }

        /* turn the sender into a player */
        Player player = (Player) sender;

        /* get the player's chunk */
        Chunk chunk = player.getLocation().getChunk();

        /* turn the chunk's x and z into a ClaimedChunkLocation */
        ClaimedChunkLocation claimedChunkLocation = new ClaimedChunkLocation(chunk);

        /* check if this chunk is claimed */
        if (ClaimedChunks.getClaimedChunks().get(claimedChunkLocation) != null) {
            player.sendMessage(StringUtils.colorString("&cThis chunk has already been claimed by another player!"));
            return false;
        }

        /* make a claimed chunk */
        ClaimedChunk claimedChunk = new ClaimedChunk(claimedChunkLocation, player.getUniqueId());

        /* claim the chunk (by putting it in the claimedChunks map) */
        ClaimedChunks.getClaimedChunks().put(claimedChunkLocation, claimedChunk);

        player.sendMessage(StringUtils.colorString("&aYou have claimed this chunk!"));

        /* debugging */
        System.out.println("Claim: " + ClaimedChunks.getClaimedChunks());

        return true;
    }
}
