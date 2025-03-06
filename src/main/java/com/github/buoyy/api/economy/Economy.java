package com.github.buoyy.api.economy;

import com.github.buoyy.api.file.YAML;
import com.github.buoyy.api.util.Messenger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Represents an economy with multiple currencies.
 *
 * @see CurrencyType
 * @see Transaction
 */
@SuppressWarnings("unused")
public class Economy {
    private final YAML dataFile;
    private final Messenger messenger;

    /**
     * The constructor for this class.
     *
     * @param dataFile  The YAML where player data of the
     *                  economy will be stored.
     * @param messenger The Messenger for logging occurrences.
     *                  May be null.
     */
    public Economy(YAML dataFile, Messenger messenger) {
        this.dataFile = dataFile;
        this.messenger = messenger;
    }

    /**
     * Formats the given integer to look like "a currency/currencies"
     *
     * @param a    The integer to format
     * @param type The type of currency to format to.
     * @return The formatted string.
     */
    public String format(int a, CurrencyType type) {
        return a + " " + (a == 1 ? type.getNameSingular() : type.getNamePlural());
    }

    /**
     * A shorthand for Economy#format(Economy#getBalance(OfflinePlayer, CurrencyType)
     *
     * @param player The player whose balance is to be retrieved
     * @param type   The currency type
     * @return The formatted balance
     */
    public String prettyBal(OfflinePlayer player, CurrencyType type) {
        return format(getBalance(player, type), type);
    }

    /**
     * Checks whether the given player's data exists in the data file.
     *
     * @param player The player to be investigated
     * @return true if the player has an account, otherwise false
     */
    public boolean hasAccount(OfflinePlayer player) {
        return dataFile.getConfig().contains(player.getUniqueId().toString());
    }

    /**
     * Checks whether the given player has enough balance of given type.
     *
     * @param player The player whose balance is to be checked
     * @param type   The currency to check.
     * @param amount The threshold amount
     * @return true if the player had "amount" of "type" in their account, otherwise false
     */
    public boolean has(OfflinePlayer player, CurrencyType type, int amount) {
        return getBalance(player, type) >= amount;
    }

    /**
     * Retrieves the given player's balance of given currency type.
     *
     * @param player The player whose balance is to be retrieved
     * @param type   The currency to retrieve from.
     * @return Player's balance of given type
     */
    public int getBalance(OfflinePlayer player, CurrencyType type) {
        return (int) getForPlayer(player, ".balance." + type.getNamePlural());
    }

    /**
     * Sets the given player's balance in given currency
     * to the given amount.
     *
     * @param player The player whose balance is to be set
     * @param type   The currency to set
     * @param amount The balance to be set
     */
    public void setBalance(OfflinePlayer player, CurrencyType type, int amount) {
        if (amount <= 3456) {
            setForPlayer(player, ".balance." + type.getNamePlural(), amount);
            if (messenger != null)
                messenger.consoleOK("Set balance of player " + player.getName() + " to " + prettyBal(player, type));
            if (player.isOnline())
                ((Player) player).sendMessage(ChatColor.GREEN + "Now, your balance is " + ChatColor.GOLD + prettyBal(player, type));
        } else {
            if (player.isOnline())
                ((Player) player).sendMessage(ChatColor.RED + "54 stacks is the current limit!\nCan't have more diamonds.");
            setBalance(player, type, 3456);
        }
    }

    public boolean setRequest(OfflinePlayer from, OfflinePlayer to,
                              CurrencyType type, int amount) {
        if (!hasAccount(from) || !hasAccount(to)) {
            messenger.consoleBad("[REQUEST] One or both of the provided players don't exist on this server!");
            return false;
        }
        int toSet = (getForPlayer(to, ".requests." + type.getNamePlural() + '.' + from.getUniqueId()) == null) ?
                amount : getRequest(from, to, type).amount + amount;
        if (toSet < 0) return false;
        setForPlayer(to, ".requests." + type.getNamePlural() + '.' + from.getUniqueId() + ".amount", toSet);
        messenger.consoleOK("Successfully set a request for player " + to.getName()
                    +" from player "+from.getName()+" for type "+type+" of amount "+toSet);
        return true;
    }

    public PaymentRequest getRequest(OfflinePlayer from, OfflinePlayer to, CurrencyType type) {
        Object amount = getForPlayer(to, ".requests." + type.getNamePlural() + '.' + from.getUniqueId() + ".amount");
        if (amount != null)
            return new PaymentRequest(from, to, type, (int) amount);
        else
            return null;
    }

    public List<PaymentRequest> getRequests(OfflinePlayer player, CurrencyType type) {
        List<PaymentRequest> requests = new ArrayList<>();
        ConfigurationSection section = dataFile.getConfig().getConfigurationSection(player.getUniqueId() + ".requests." + type.getNamePlural());
        if (section != null) {
            for (String i : section.getKeys(false)) {
                requests.add(getRequest(Bukkit.getOfflinePlayer(UUID.fromString(i)), player, type));
            }
        } else {
            messenger.consoleOK("Player " + player.getName() + " has no requests.");
        }
        return requests;
    }

    public void processRequest(OfflinePlayer from, OfflinePlayer to, CurrencyType type, int amount) {
        int current = getRequest(from, to, type).amount;
        if (current - amount > 0)
            setForPlayer(to, ".requests." + type.getNamePlural() + '.' + from.getUniqueId() + ".amount", current - amount);
        else if (current >= amount)
            setForPlayer(to, ".requests." + type.getNamePlural() + '.' + from.getUniqueId(), null);
    }

    /**
     * Does a transaction which adds the given amount to the
     * player's balance in the given currency. Will result in a
     * failure in case the amount is negative.
     *
     * @param player The player whose balance is to be changed
     * @param type   The currency to be added to
     * @param amount The amount to be added
     * @return The Transaction object
     */
    public Transaction add(OfflinePlayer player, CurrencyType type, int amount) {
        if (amount < 0)
            return new Transaction(amount,
                    type,
                    getBalance(player, type),
                    "Negative amount",
                    Transaction.TransactionResult.FAILURE);
        setBalance(player, type, getBalance(player, type) + amount);
        return new Transaction(amount,
                type,
                getBalance(player, type),
                "",
                Transaction.TransactionResult.SUCCESS);
    }

    /**
     * Does a transaction which adds the given amount to the
     * player's balance in the given currency. Will result in a
     * failure in case the amount is negative or the player does
     * not have enough balance.
     *
     * @param player The player whose balance is to be changed
     * @param type   The currency to he subtracted from
     * @param amount The amount to be subtracted
     * @return The Transaction object
     */
    public Transaction subtract(OfflinePlayer player, CurrencyType type, int amount) {
        if (amount < 0)
            return new Transaction(amount,
                    type,
                    getBalance(player, type),
                    "Negative amount",
                    Transaction.TransactionResult.FAILURE);
        if (!has(player, type, amount))
            return new Transaction(amount,
                    type,
                    getBalance(player, type),
                    "Insufficient funds",
                    Transaction.TransactionResult.FAILURE);
        setBalance(player, type, getBalance(player, type) - amount);
        return new Transaction(amount,
                type,
                getBalance(player, type),
                "",
                Transaction.TransactionResult.SUCCESS);
    }

    /**
     * Loads the player's account into the server.
     *
     * @param player The player whose account is to be loaded.
     */
    public void loadAccount(OfflinePlayer player) {
        if (hasAccount(player)) {
            messenger.consoleOK("Player account found: " + player.getName());
            final int dia = getBalance(player, CurrencyType.DIAMOND);
            final int gold = getBalance(player, CurrencyType.GOLD);
            setBalance(player, CurrencyType.DIAMOND, dia);
            setBalance(player, CurrencyType.GOLD, gold);
        } else {
            if (messenger != null) {
                messenger.consoleOK("Account not found for: " + player.getName());
                messenger.consoleGood("Creating account for " + player.getName());
            }
            setBalance(player, CurrencyType.DIAMOND, 0);
            setBalance(player, CurrencyType.GOLD, 0);
        }
    }

    private void setForPlayer(OfflinePlayer player, String toSet, Object value) {
        dataFile.getConfig().set(player.getUniqueId() + toSet, value);
        dataFile.save();
    }

    private Object getForPlayer(OfflinePlayer player, String toGet) {
        return dataFile.getConfig().get(player.getUniqueId() + toGet);
    }
}
