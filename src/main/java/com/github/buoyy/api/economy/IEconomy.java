package com.github.buoyy.api.economy;

import org.bukkit.OfflinePlayer;

import java.util.List;

public interface IEconomy
{
    String format(int a, CurrencyType type);
    String prettyBal(OfflinePlayer player, CurrencyType type);
    boolean hasAccount(OfflinePlayer player);
    boolean has(OfflinePlayer player, CurrencyType type, int amount);
    int getBalance(OfflinePlayer player, CurrencyType type);
    void setBalance(OfflinePlayer player, CurrencyType type, int amount);
    boolean setRequest(OfflinePlayer from, OfflinePlayer to, CurrencyType type, int amount);
    PaymentRequest getRequest(OfflinePlayer from, OfflinePlayer to, CurrencyType type);
    List<PaymentRequest> getRequests(OfflinePlayer player, CurrencyType type);
    void processRequest(OfflinePlayer from, OfflinePlayer to, CurrencyType type, int amount);
    Transaction add(OfflinePlayer player, CurrencyType type, int amount);
    Transaction subtract(OfflinePlayer player, CurrencyType type, int amount);
    void loadAccount(OfflinePlayer player);
}
