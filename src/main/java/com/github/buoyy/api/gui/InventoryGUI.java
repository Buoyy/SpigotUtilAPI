package com.github.buoyy.api.gui;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a classic Inventory based GUI. Has many
 * use cases, however if you want to build something similar,
 * but it doesn't quite fit with this class, extend
 * @see InventoryHandler
 */
@SuppressWarnings("unused")
public abstract class InventoryGUI extends InventoryHandler {
    private final Map<Integer, InvButton> buttons = new HashMap<>();

    /**
     * Adds the provided button to the provided slot
     * in the inventory.
     * @param slot The slot in the inventory where the button is
     *             to be added
     * @param button The button to add to the inventory.
     */
    public void addButton(int slot, InvButton button) {
        this.buttons.put(slot, button);
    }

    /**
     * Puts all the buttons in their provided slots in the
     * inventory. This method is usually overridden by subclasses
     * and its super variant is run at the end of the
     * developer's implementation.
     * See the GitHub page for more.
     */
    public void decorate() {
        this.buttons.forEach((i, j) -> this.getInv().setItem(i, j.getIcon()));
    }

    /**
     * Handles the clicking event in the inventory.
     * Cancels the event and runs the button's onClick.
     * Usually, the inventory would have
     * @see InvButton objects.
     * @param e the event to listen for
     */
    @Override
    public void onClick(InventoryClickEvent e) {
        e.setCancelled(true);
        InvButton button = this.buttons.get(e.getSlot());
        if (button != null) button.getOnClick().accept(e);
    }

    /**
     * Handles the inventory opening event. Populates
     * the inventory with the registered buttons.
     * @param e the event to listen for
     */
    @Override
    public void onOpen(InventoryOpenEvent e) {
        this.decorate();
    }

    /**
     * Handles the inventory closing event.
     * @param e the event to listen for
     */
    @Override
    public void onClose(InventoryCloseEvent e) {
    }
}