package io.ayushchivate.github.claimplugin;

import org.bukkit.entity.Player;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class ClaimedChunk {

    /* object that holds the x and z location of a chunk */
    private ClaimedChunkLocation claimedChunkLocation;
    /* UUID of the player */
    private UUID uuid;
    /* set of players who can bypass restrictions for this chunk */
    private Set<UUID> trustedPlayers = new HashSet<>();
    /* set of disallowed permissions for this chunk */
    private EnumSet<FlagType> restrictions = EnumSet.allOf(FlagType.class);

    public ClaimedChunk(ClaimedChunkLocation claimedChunkLocation, UUID uuid) {
        this.claimedChunkLocation = claimedChunkLocation;
        this.uuid = uuid;
    }

    /* adds a restriction to all players for this chunk */
    public void addFlag(FlagType f) {
        this.restrictions.add(f);
    }

    /* removes a restriction for all players for this chunk */
    public void removeFlag(FlagType f) {
        this.restrictions.remove(f);
    }

    /* adds a trusted player that can bypass restrictions in this chunk */
    public void addTrustedPlayer(Player player) {
        this.trustedPlayers.add(player.getUniqueId());
    }

    /* removes a trusted player */
    public void removeTrustedPlayer(Player player) {
        this.trustedPlayers.remove(player.getUniqueId());
    }

    public boolean isOwner(Player player) {
        return player.getUniqueId().equals(this.uuid);
    }

    public boolean isTrusted(Player player) {
        return this.trustedPlayers.contains(player.getUniqueId());
    }

    public boolean isRestricted(FlagType f) {
        System.out.println("Restriction Map from isRestricted()" + this.restrictions);
        return this.restrictions.contains(f);
    }

    public Set<UUID> getTrustedPlayers() {
        return trustedPlayers;
    }

    public EnumSet<FlagType> getRestrictions() {
        return restrictions;
    }

    @Override
    public String toString() {
        return "ClaimedChunk{" +
                "claimedChunkLocation=" + claimedChunkLocation +
                ", uuid=" + uuid +
                ", trustedPlayers=" + trustedPlayers +
                ", restrictions=" + restrictions +
                '}';
    }
}
