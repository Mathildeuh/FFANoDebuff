package fr.mathildeuh.Listeners;

import fr.mathildeuh.FFANoDebuff;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class LDeath implements Listener {
    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        if (e.getEntity().getWorld().getName().equalsIgnoreCase(Bukkit.getWorld(FFANoDebuff.getInstance().getConfig().getString("world")).getName())) {
            e.setDeathMessage(null);

            e.setKeepInventory(true);
            e.getEntity().getInventory().clear();
            FFANoDebuff.getInstance().giveStuff(e.getEntity());

            e.getEntity().getKiller().setHealth(20);
            e.getEntity().getKiller().setSaturation(20);
            e.getEntity().getKiller().setFoodLevel(20);
            e.getEntity().spigot().respawn();

            String message = FFANoDebuff.getInstance().deathMessage.replace("%victim%", e.getEntity().getName()).replace("%attacker%", e.getEntity().getKiller().getName());
            if (FFANoDebuff.getInstance().getConfig().getBoolean("DeathMessage.inActionBar")) {
                for (Player players : Bukkit.getOnlinePlayers()) {
                    if (players.getWorld().getName().equalsIgnoreCase(Bukkit.getWorld(FFANoDebuff.getInstance().getConfig().getString("world")).getName())) {
                        FFANoDebuff.getInstance().sendActionText(players, message);
                    }
                }
                return;

            }

            for (Player players : Bukkit.getOnlinePlayers()) {
                if (players.getWorld().getName().equalsIgnoreCase(Bukkit.getWorld(FFANoDebuff.getInstance().getConfig().getString("world")).getName())) {
                    players.sendMessage(message);
                }
            }

        }
    }
}
