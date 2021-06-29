package io.ayushchivate.github.claimplugin;

import net.dohaw.corelib.StringUtils;
import org.bukkit.Chunk;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class UnclaimCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        /* make sure only players can use the command */
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command!");
            return false;
        }

        /* turn the sender into a player */
        Player player = (Player) sender;

        Chunk chunk = player.getLocation().getChunk();

        ClaimedChunkLocation claimedChunkLocation = new ClaimedChunkLocation(chunk);

        ClaimedChunk claimedChunk = ClaimedChunks.getClaimedChunks().get(claimedChunkLocation);

        /* if the chunk trying to be unclaimed has not been previously claimed */
        if (claimedChunk == null) {
            player.sendMessage(StringUtils.colorString("&4This chunk has not been claimed!"));
            return false;
        }

        /* if the player does not own the chunk */
        if (!(claimedChunk.isOwner(player))) {
            player.sendMessage(StringUtils.colorString("&4You do not own this chunk!"));
            return false;
        }

        ClaimedChunks.getClaimedChunks().remove(claimedChunkLocation);
        player.sendMessage(StringUtils.colorString("&aChunk has been unclaimed!"));

        /* debugging */
        System.out.println("Unclaim: " + ClaimedChunks.getClaimedChunks());

        return true;
    }
}
