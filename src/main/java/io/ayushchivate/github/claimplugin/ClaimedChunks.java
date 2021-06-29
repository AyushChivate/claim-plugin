package io.ayushchivate.github.claimplugin;

import java.util.HashMap;
import java.util.Map;

public class ClaimedChunks {

    private static Map<ClaimedChunkLocation, ClaimedChunk> claimedChunks = new HashMap<>();

    public static Map<ClaimedChunkLocation, ClaimedChunk> getClaimedChunks() {
        return claimedChunks;
    }
}
