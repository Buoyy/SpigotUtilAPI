package com.github.buoyy.api.gui;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
public abstract class InventoryHandler {
    private final Inventory inv;
    public InventoryHandler() {
        this.inv = this.createInv();
    }
    public Inventory getInv() {
        return inv;
    }
    public abstract Inventory createInv();
    public abstract void onClick(InventoryClickEvent e);
    public abstract void onOpen(InventoryOpenEvent e);
    public abstract void onClose(InventoryCloseEvent e);
}
