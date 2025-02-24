package com.github.buoyy.api.command;

import org.bukkit.command.CommandSender;

import java.util.List;

/**
 * A class representing a sub-command related to a
 * base command (@sew BaseCommand).
 */
public interface SubCommand {
    /**
     * The code to execute when this sub-command is run.
     * It is to be implemented just like Bukkit#onCommand().
     * @param sender - The player/command block/console which sent
     *               the command.
     * @param args - The arguments to the base command. Always
     *             remember that these are the same arguments which
     *             the respective base command ran.
     * @return - The boolean to return when this command is run. Always return true.
     */
    boolean execute(CommandSender sender, String[] args);

    /**
     * The list of tab completions to give to the sender. If you don't
     * want to use tab completions, return null or List#of().
     * @param args - The arguments to be completed. Always remember that
     *             these are the same argument which the base command ran.
     * @return - The list of completions which the sender will see
     */
    List<String> getCompletions(String[] args);
}