package com.github.buoyy.api.gui;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;

/**
 * Represents an extendable class which handles an
 * Inventory based GUI.
 */
public abstract class InventoryHandler {
    private final Inventory inv;

    /**
     * The constructor of such a class.
     * Just creates the inventory.
     */
    public InventoryHandler() {
        this.inv = this.createInv();
    }

    /**
     * Retrieves the inventory being handled by this class.
     * @return the inventory being handled by this class
     */
    public Inventory getInv() {
        return inv;
    }

    /**
     * A method which returns the inventory to be created
     * for this handler. Usually made with Bukkit#createInventory
     * @return the inventory
     */
    public abstract Inventory createInv();

    /**
     * What to do when an item is clicked in GUI.
     * @param e the event to listen for
     */
    public abstract void onClick(InventoryClickEvent e);

    /**
     * What to do when this inventory is opened.
     * @param e the event to listen for
     */
    public abstract void onOpen(InventoryOpenEvent e);

    /**
     * What to do when this inventory is closed.
     * @param e the event to listen for
     */
    public abstract void onClose(InventoryCloseEvent e);
}
