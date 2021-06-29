package io.ayushchivate.github.claimplugin;

import net.dohaw.corelib.StringUtils;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class FlagListener implements Listener {

    public void isActionRestricted(Cancellable e, Player player, Chunk chunk, FlagType f) {

        /* check if player is allowed to interact in the chunk */
        /* a player can interact with a chunk if the chunk is not claimed,
           they are the owner, they are trusted, or the chunk has interact permissions */

        /* check if chunk is claimed */
        // getting the location of the chunk in which the block was placed
        ClaimedChunkLocation claimedChunkLocation = new ClaimedChunkLocation(chunk);
        // get the claimed chunk
        ClaimedChunk claimedChunk = ClaimedChunks.getClaimedChunks().get(claimedChunkLocation);

        /* debugging */
        System.out.println("Claimed chunk: " + claimedChunk);

        // if the chunk is claimed
        if (claimedChunk != null) {
            System.out.println("Restrictions Map from isActionRestricted(): " + claimedChunk.getRestrictions());
            System.out.println("is restricted: " + claimedChunk.isRestricted(f) + ", is not owner: " + !claimedChunk.isOwner(player) + ", is not trusted: " + !claimedChunk.isTrusted(player));
            if (claimedChunk.isRestricted(f) && !claimedChunk.isOwner(player) && !claimedChunk.isTrusted(player)) {
                e.setCancelled(true);
                player.sendMessage(StringUtils.colorString("&cYou don't have permission to do that!"));
            }
        }
    }

    @EventHandler
    public void onPlayerBuild(BlockPlaceEvent e) {
        isActionRestricted(e, e.getPlayer(), e.getBlockPlaced().getChunk(), FlagType.CANNOT_PLACE);
        /* debugging */
        System.out.println("OnPlayerBuild has fired.");
    }

    @EventHandler
    public void onPlayerBreak(BlockBreakEvent e) {
        isActionRestricted(e, e.getPlayer(), e.getBlock().getChunk(), FlagType.CANNOT_BREAK);
    }

    @EventHandler
    public void onFlintAndSteel(BlockIgniteEvent e) {
        if (e.getCause() == BlockIgniteEvent.IgniteCause.FLINT_AND_STEEL) {
            isActionRestricted(e, e.getPlayer(), e.getBlock().getChunk(), FlagType.CANNOT_USE_FLINT_AND_STEEL);
        }
    }

    @EventHandler
    public void onChickenSpawn(ProjectileHitEvent e) {
        Projectile p = e.getEntity();
        if (p.getType() == EntityType.EGG && p.getShooter() instanceof Player) {
            isActionRestricted(e, (Player) p.getShooter(), p.getLocation().getChunk(), FlagType.CANNOT_SPAWN_CHICKENS);
        }
    }

    @EventHandler
    public void onPlayerSpeak(AsyncPlayerChatEvent e) {
        isActionRestricted(e, e.getPlayer(), e.getPlayer().getLocation().getChunk(), FlagType.CANNOT_SPEAK);
    }

    @EventHandler
    public void onChestOpen(PlayerInteractEvent e) {
        if (e.hasBlock()) {
            if (e.getAction() == Action.RIGHT_CLICK_BLOCK && e.getClickedBlock().getType() == Material.CHEST) {
                isActionRestricted(e, e.getPlayer(), e.getPlayer().getLocation().getChunk(), FlagType.CANNOT_OPEN_CHESTS);
            }
        }
    }

    @EventHandler
    public void onItemInteract(PlayerInteractEvent e) {
        if (e.hasBlock()) {
            if (e.getAction() == Action.RIGHT_CLICK_BLOCK && e.getClickedBlock().getType() != Material.CHEST && e.getClickedBlock().getType().isInteractable()) {
                isActionRestricted(e, e.getPlayer(), e.getPlayer().getLocation().getChunk(), FlagType.CANNOT_INTERACT_WITH_ITEMS);
            }
        }
    }
}
