package com.github.buoyy.api.gui;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.Map;

/**
 * A class used for loading and handling inventory based GUIs.
 * Generally you would want to follow singleton pattern for this.
 * To set this up, you will also need a Listener class, whose
 * basic implementation is provided in the GitHub page.
 */
@SuppressWarnings("unused")
public class GUIManager {
    private final Map<Inventory, InventoryHandler> storages = new HashMap<>();
    private void registerInv(Inventory inv, InventoryHandler handler) {
        this.storages.put(inv, handler);
    }
    private void unregisterInv(Inventory inv) {
        this.storages.remove(inv);
    }

    /**
     * Handles the clicking event.
     * @param e The event to pass to the Listener
     */
    public void handleClick(InventoryClickEvent e) {
        InventoryHandler handler = this.storages.get(e.getInventory());
        if (handler != null) handler.onClick(e);
    }

    /**
     * Handles the inventory opening event.
     * @param e The event to pass to the Listener
     */
    public void handleOpen(InventoryOpenEvent e) {
        InventoryHandler handler = this.storages.get(e.getInventory());
        if (handler != null) handler.onOpen(e);
    }

    /**
     * Handles the inventory closing event.
     * @param e The event to pass to the Listener
     */
    public void handleClose(InventoryCloseEvent e) {
        InventoryHandler handler = this.storages.get(e.getInventory());
        if (handler != null) {
            handler.onClose(e);
            this.unregisterInv(e.getInventory());
        }
    }

    /**
     * Opens the inventory associated with the given handler for
     * the given player
     * @param player The player to open the inventory for
     * @param handler The handler associated with the inventory to open
     */
    public void openGUI(Player player, InventoryHandler handler) {
        this.registerInv(handler.getInv(), handler);
        player.openInventory(handler.getInv());
    }
}
