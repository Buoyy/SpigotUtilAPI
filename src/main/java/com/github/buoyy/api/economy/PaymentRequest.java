package com.github.buoyy.api.economy;

import org.bukkit.OfflinePlayer;

public class PaymentRequest {
    public final OfflinePlayer from, to;
    public final CurrencyType type;
    public final int amount;

    public PaymentRequest(OfflinePlayer from, OfflinePlayer player, CurrencyType type, int amount) {
        this.from = from;
        to = player;
        this.type = type;
        this.amount = amount;
    }
}
