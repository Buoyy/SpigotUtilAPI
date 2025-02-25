package com.github.buoyy.api.economy;

import org.bukkit.Material;

/**
 * Represents a type of currency. More may be added for convenience.
 * Useful for handling what ItemStack to store and drop for players.
 */
@SuppressWarnings("unused")
public enum CurrencyType {
    DIAMOND(Material.DIAMOND, "diamond", "diamonds"),
    GOLD(Material.GOLD_INGOT, "gold", "gold");

    private final Material material;
    private final String nameSingular;
    private final String namePlural;
    CurrencyType(Material material, String nameSingular, String namePlural) {
        this.material = material;
        this.nameSingular = nameSingular;
        this.namePlural = namePlural;
    }

    public Material getMaterial() {
        return material;
    }

    public String getNameSingular() {
        return nameSingular;
    }

    public String getNamePlural() {
        return namePlural;
    }
}
