package io.ayushchivate.github.claimplugin;

import net.dohaw.corelib.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TrustCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, @NotNull Command command, String label, String[] args) {

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

        /* if the chunk trying to be trusted has not been previously claimed */
        if (claimedChunk == null) {
            player.sendMessage(StringUtils.colorString("&cThis chunk has not been claimed!"));
            return false;
        }

        /* if the player does not own the chunk */
        if (!(claimedChunk.isOwner(player))) {
            player.sendMessage(StringUtils.colorString("&cYou do not own this chunk!"));
            return false;
        }

        /* turns the player specified into a player object */
        String playerName = args[0];
        Player otherPlayer = Bukkit.getPlayer(playerName);

        /* if the player specified is not online */
        if (otherPlayer == null) {
            player.sendMessage(StringUtils.colorString("&cThis player is not valid!"));
            return false;
        }

        /* make sure the player doesn't trust themselves */
        if (player.getUniqueId().equals(otherPlayer.getUniqueId())) {
            player.sendMessage(StringUtils.colorString("&cYou cannot trust yourself!"));
            return false;
        }

        /* trust the specified player */
        claimedChunk.addTrustedPlayer(otherPlayer);
        player.sendMessage(StringUtils.colorString("&aThis player has been trusted!"));

        /* debugging */
        System.out.println("Trusted: " + claimedChunk.getTrustedPlayers());

        return true;
    }
}
