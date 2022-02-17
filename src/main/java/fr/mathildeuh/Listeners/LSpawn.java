package fr.mathildeuh.Listeners;

import fr.mathildeuh.FFANoDebuff;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class LSpawn implements Listener {
    private float red = 255;
    private float green = 255;
    private float blue = 255;

    @EventHandler
    public void playerMove(PlayerMoveEvent e) {
        if (FFANoDebuff.inCuboid( FFANoDebuff.getInstance().location1, FFANoDebuff.getInstance().location2, e.getPlayer().getLocation())) {
//            e.getPlayer().sendMessage("§aIn region");
            e.getPlayer().setHealth(20);
            e.getPlayer().setFoodLevel(20);
            e.getPlayer().setSaturation(20);
            return;
        }
//        e.getPlayer().sendMessage("§cNot in region");
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        if (e.getPlayer().getGameMode() != GameMode.CREATIVE) {
            e.setCancelled(true);
            e.getPlayer().sendMessage(FFANoDebuff.getInstance().getConfig().get("BlockPlaceNoPermission").toString());

            Location location = e.getBlockPlaced().getLocation().add(0.5, 1.5, 0.5);

            PacketPlayOutWorldParticles particles = new PacketPlayOutWorldParticles(EnumParticle.REDSTONE, true, (float) location.getX(), (float) location.getY(), (float) location.getZ(), red, green, blue, (float) 255, 0, 10);
            ((CraftPlayer) e.getPlayer()).getHandle().playerConnection.sendPacket(particles);
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        if (e.getPlayer().getGameMode() != GameMode.CREATIVE) {
            e.setCancelled(true);
            e.getPlayer().sendMessage(FFANoDebuff.getInstance().getConfig().get("BlockBreakNoPermission").toString());

            Location location = e.getBlock().getLocation().add(0.5, 1.5, 0.5);

            PacketPlayOutWorldParticles particles = new PacketPlayOutWorldParticles(EnumParticle.REDSTONE, true, (float) location.getX(), (float) location.getY(), (float) location.getZ(), red, green, blue, (float) 255, 0, 10);
            ((CraftPlayer) e.getPlayer()).getHandle().playerConnection.sendPacket(particles);
        }
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {
        if (!(e.getEntity() instanceof Player)) return;
        if (FFANoDebuff.inCuboid(FFANoDebuff.getInstance().location1, FFANoDebuff.getInstance().location2, e.getEntity().getLocation())) {
            e.setCancelled(true);
        }
        if (!(e.getDamager() instanceof Player)) return;

        Player player = (Player) e.getDamager();
        if (FFANoDebuff.inCuboid(FFANoDebuff.getInstance().location1, FFANoDebuff.getInstance().location2, player.getLocation())) {
            e.setCancelled(true);
            return;
        }

    }

    @EventHandler
    public void onPotion(PlayerItemConsumeEvent e) {
        if (e.getItem().getType().equals(Material.POTION)) {
            Bukkit.getScheduler().runTaskLater(FFANoDebuff.getInstance(), new Runnable() {
                @Override
                public void run() {
                    e.getPlayer().getInventory().remove(Material.GLASS_BOTTLE);

                }
            }, 2);
        }
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent e) {
        Player player = event.getPlayer();
        if (e.getPlayer().getWorld().getName().equalsIgnoreCase(Bukkit.getWorld(FFANoDebuff.getInstance().getConfig().getString("world")).getName())) {
            e.setCancelled(true);
            player.sendMessage(ChatColor.RED + "Tu ne peut pas jeter ton stuff ici >:(");
            player.playSound(player.getLocation(),Sound.ENTITY_VILLAGER_NO, 2, 1);
        }
    }
}
