package io.ayushchivate.github.claimplugin;

import org.bukkit.Chunk;

import java.util.Objects;

public class ClaimedChunkLocation {

    /* x coordinate of chunk */
    int x;
    /* z coordinate of chunk */
    int z;

    public ClaimedChunkLocation(Chunk chunk) {
        this.x = chunk.getX();
        this.z = chunk.getZ();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClaimedChunkLocation that = (ClaimedChunkLocation) o;
        return x == that.x && z == that.z;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, z);
    }

    @Override
    public String toString() {
        return "ClaimedChunkLocation{" +
                "x=" + x +
                ", z=" + z +
                '}';
    }
}
