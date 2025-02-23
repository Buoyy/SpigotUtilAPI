package com.github.buoyy.api;

import org.bukkit.OfflinePlayer;

public interface Economy {
    String format(int a, CurrencyType type);
    boolean hasAccount(OfflinePlayer player);
    boolean has(OfflinePlayer player, CurrencyType type, int amount);
    int getBalance(OfflinePlayer player, CurrencyType type);
    Transaction add(OfflinePlayer player, CurrencyType type, int amount);
    Transaction subtract(OfflinePlayer player, CurrencyType type, int amount);
}
