package com.github.buoyy.api;

import org.bukkit.Material;

public enum CurrencyType {
    DIAMOND(Material.DIAMOND),
    GOLD(Material.GOLD_INGOT);
    private final Material material;
    CurrencyType(Material material) {
        this.material = material;
    }

    public Material getMaterial() {
        return material;
    }
}
