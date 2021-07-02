package fr.mathildeuh.Listeners;

import fr.mathildeuh.FFANoDebuff;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scoreboard.Criterias;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public class LConnection implements Listener {


    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        e.getPlayer().teleport(FFANoDebuff.getInstance().spawnLocation);

        FFANoDebuff.getInstance().giveStuff(e.getPlayer());

        e.setJoinMessage(FFANoDebuff.getInstance().joinMessage.replace("%player%", e.getPlayer().getName()));

        if (FFANoDebuff.getInstance().getConfig().getBoolean("healthUnderName.enabled")) {
            org.bukkit.scoreboard.ScoreboardManager sm = Bukkit.getScoreboardManager();
            Scoreboard s = sm.getNewScoreboard();
            Objective h = s.registerNewObjective("showhealth", Criterias.HEALTH);
            h.setDisplaySlot(DisplaySlot.BELOW_NAME);
            h.setDisplayName(FFANoDebuff.getInstance().underName);
            e.getPlayer().setScoreboard(s);
        }

    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        e.setQuitMessage(FFANoDebuff.getInstance().quitMessage.replace("%player%", e.getPlayer().getName()));


    }
}
