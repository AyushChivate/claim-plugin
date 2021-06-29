package io.ayushchivate.github.claimplugin;

import net.dohaw.corelib.JPUtils;
import net.dohaw.corelib.menus.Menu;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public class FlagsMenu extends Menu implements Listener {

    ClaimedChunk claimedChunk;

    public FlagsMenu(JavaPlugin plugin, ClaimedChunk claimedChunk) {
        super(plugin, null, "Flags", 27);
        this.claimedChunk = claimedChunk;
        JPUtils.registerEvents(this);
    }

    @Override
    public void initializeItems(Player p) {
        FlagType[] flagType = FlagType.values();
        for (int i = 10; i < 17; i++) {
            FlagType flag = flagType[i - 10];
            Material material = claimedChunk.isRestricted(flag) ? Material.RED_WOOL : Material.GREEN_WOOL;
            inv.setItem(i, createGuiItem(material, flag.displayName, new ArrayList<>()));
        }
        this.fillerMat = Material.BLACK_STAINED_GLASS_PANE;
        fillMenu(false);
    }

    @Override
    @EventHandler
    protected void onInventoryClick(InventoryClickEvent e) {

        Player player = (Player) e.getWhoClicked();
        int slotClicked = e.getSlot();
        ItemStack clickedItem = e.getCurrentItem();

        Inventory clickedInventory = e.getClickedInventory();
        Inventory topInventory = player.getOpenInventory().getTopInventory();

        if (clickedInventory == null) return;
        if (!topInventory.equals(inv) || !clickedInventory.equals(topInventory)) return;
        if (clickedItem == null && e.getCursor() == null) return;

        e.setCancelled(true);

        if (clickedItem == null || clickedItem.getItemMeta() == null || clickedItem.getType() == fillerMat) {
            return;
        }

        String displayName = clickedItem.getItemMeta().getDisplayName();

        FlagType flagType = getFlagClicked(displayName);

        if (flagType == null) {
            return;
        }

        Material material;

        if (claimedChunk.isRestricted(flagType)) {
            claimedChunk.removeFlag(flagType);
            material = Material.GREEN_WOOL;
        } else {
            claimedChunk.addFlag(flagType);
            material = Material.RED_WOOL;
        }

        clickedItem.setType(material);
    }

    public FlagType getFlagClicked(String displayName) {

        FlagType flagTypeClicked = null;
        for (FlagType flag : FlagType.values()) {
            if (flag.displayName.equals(displayName)) {
                flagTypeClicked = flag;
            }
        }
        return flagTypeClicked;
    }
}
