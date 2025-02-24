package com.github.buoyy.api.economy;

import com.github.buoyy.api.file.YAML;
import com.github.buoyy.api.util.Messenger;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

@SuppressWarnings("unused")
public class Economy {
    private final YAML dataFile;
    private final Messenger messenger;
    public Economy(YAML dataFile, Messenger messenger) {
        this.dataFile = dataFile;
        this.messenger = messenger;
    }
    public String format(int a, CurrencyType type) {
        return a + " " + (a == 1 ? type.getNameSingular() : type.getNamePlural());
    }
    public String prettyBal(OfflinePlayer player, CurrencyType type) {
        return format(getBalance(player, type), type);
    }
    public boolean hasAccount(OfflinePlayer player) {
        return dataFile.getConfig().contains(player.getUniqueId().toString());
    }

    public boolean has(OfflinePlayer player, CurrencyType type, int amount) {
        return getBalance(player, type) >= amount;
    }

    public int getBalance(OfflinePlayer player, CurrencyType type) {
        return dataFile.getConfig().getInt(player.getUniqueId()+
                ".balance."+type.getNamePlural());
    }

    public void setBalance(OfflinePlayer player, CurrencyType type, int amount) {
        if (amount <= 3456) {
            dataFile.getConfig().set(player.getUniqueId() + ".balance."+type.getNamePlural(), amount);
            dataFile.save();
            messenger.consoleOK("Set balance of player " + player.getName() + " to " +prettyBal(player, type));
            if (player.isOnline())
                ((Player)player).sendMessage(ChatColor.GREEN+"Now, your balance is "+ChatColor.GOLD+prettyBal(player, type));
        } else {
            if (player.isOnline())
                ((Player)player).sendMessage(ChatColor.RED + "54 stacks is the current limit!\nCan't have more diamonds.");
            setBalance(player, type, 3456);
        }
    }

    public Transaction add(OfflinePlayer player, CurrencyType type, int amount) {
        if (amount < 0)
            return new Transaction(amount,
                    type,
                    "Negative amount",
                    Transaction.TransactionResult.FAILURE);
        setBalance(player, type, getBalance(player, type)+amount);
        return new Transaction(amount,
                type,
                "",
                Transaction.TransactionResult.SUCCESS);
    }

    public Transaction subtract(OfflinePlayer player, CurrencyType type, int amount) {
        if (amount < 0)
            return new Transaction(amount,
                    type,
                    "Negative amount",
                    Transaction.TransactionResult.FAILURE);
        if (!has(player, type, amount))
            return new Transaction(amount,
                    type,
                    "Insufficient funds",
                    Transaction.TransactionResult.FAILURE);
        return new Transaction(amount,
                type,
                "",
                Transaction.TransactionResult.SUCCESS);
    }
    public void loadAccount(OfflinePlayer player) {
        if (hasAccount(player)) {
            messenger.consoleOK("Player account found: "+player.getName());
            final int dia = getBalance(player, CurrencyType.DIAMOND);
            final int gold = getBalance(player, CurrencyType.GOLD);
            setBalance(player, CurrencyType.DIAMOND, dia);
            setBalance(player, CurrencyType.GOLD, gold);
        } else {
            messenger.consoleOK("Account not found for: "+player.getName());
            messenger.consoleGood("Creating account for "+player.getName());
            setBalance(player, CurrencyType.DIAMOND, 0);
            setBalance(player, CurrencyType.GOLD, 0);
        }
    }
}
