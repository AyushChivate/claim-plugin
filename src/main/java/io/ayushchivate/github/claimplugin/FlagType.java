package io.ayushchivate.github.claimplugin;

public enum FlagType {
    CANNOT_PLACE("Place Blocks"),
    CANNOT_BREAK("Break Blocks"),
    CANNOT_USE_FLINT_AND_STEEL("Flint and Steel"),
    CANNOT_SPAWN_CHICKENS("Spawn Chickens"),
    CANNOT_SPEAK("Speak"),
    CANNOT_OPEN_CHESTS("Open Chests"),
    CANNOT_INTERACT_WITH_ITEMS("Interact with Items");

    String displayName;

    FlagType(String displayName) {
        this.displayName = displayName;
    }
}