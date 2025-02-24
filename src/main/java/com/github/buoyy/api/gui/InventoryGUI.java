package com.github.buoyy.api.gui;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unused")
public abstract class InventoryGUI implements InventoryHandler {
    private final Inventory inv;
    private final Map<Integer, InvButton> buttons = new HashMap<>();

    public InventoryGUI() {
        this.inv = this.createInventory();
    }

    public Inventory getInv() {
        return inv;
    }

    public void addButton(int slot, InvButton button) {
        this.buttons.put(slot, button);
    }

    public void decorate() {
        this.buttons.forEach((i, j) -> this.inv.setItem(i, j.getIcon()));
    }

    @Override
    public void onClick(InventoryClickEvent e) {
        e.setCancelled(true);
        InvButton button = this.buttons.get(e.getSlot());
        if (button != null) button.getOnClick().accept(e);
    }

    @Override
    public void onOpen(InventoryOpenEvent e) {
        this.decorate();
    }

    @Override
    public void onClose(InventoryCloseEvent e) {
    }
    public abstract Inventory createInventory();
}