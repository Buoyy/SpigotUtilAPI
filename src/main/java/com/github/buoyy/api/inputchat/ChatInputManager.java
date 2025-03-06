package com.github.buoyy.api.inputchat;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@SuppressWarnings("unused")
public class ChatInputManager {
    private final Map<UUID, InputType> waitingPlayers = new HashMap<>();
    public void addPlayerWithType(Player player, InputType type) {
        waitingPlayers.put(player.getUniqueId(), type);
    }
    public void removePlayer(Player player) {
        waitingPlayers.remove(player.getUniqueId());
    }
    public InputType getInputType(Player player) {
        return waitingPlayers.get(player.getUniqueId());
    }
    public boolean isPlayerWaiting(Player player) {
        return waitingPlayers.containsKey(player.getUniqueId());
    }
}
