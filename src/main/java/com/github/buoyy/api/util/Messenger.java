package com.github.buoyy.api.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;

/**
 * The messenger class. A useful logger which can be used
 * for reporting important messages to the console elegantly.
 */
public class Messenger {
    private final ConsoleCommandSender console = Bukkit.getConsoleSender();

    /**
     * Logs a message for an occurrence which is okay to happen.
     * Colours the message aqua.
     * @param message - The message to log
     */
    public void consoleOK(String message) {
        console.sendMessage(ChatColor.AQUA + "[FINE]: "+message);
    }

    /**
     * Logs a message for an occurrence which should happen.
     * Colours the message green.
     * @param message - The message to log
     */
    public void consoleGood(String message) {
        console.sendMessage(ChatColor.GREEN + "[GOOD]: "+message);
    }

    /**
     * Logs a message for an occurrence which shouldn't have happened.
     * Colors the message red.
     * @param message - The message to log.
     */
    public void consoleBad(String message) {
        console.sendMessage(ChatColor.RED + "[BAD]: " +message);
    }
}
