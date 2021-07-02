package fr.mathildeuh;

import fr.mathildeuh.Listeners.LConnection;
import fr.mathildeuh.Listeners.LDeath;
import fr.mathildeuh.Listeners.LSpawn;
import fr.mathildeuh.utils.ItemBuilder;
import net.minecraft.server.v1_8_R3.ChatComponentText;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import org.apache.commons.lang.math.IntRange;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffectType;

public final class FFANoDebuff extends JavaPlugin {

    private static FFANoDebuff INSTANCE;

    public Location location1;
    public Location location2;
    public Location spawnLocation;

    public String joinMessage, quitMessage, deathMessage, underName;

    ItemStack heal = new ItemBuilder(Material.POTION, 1).setData((short) 16421).toItemStack();
    ItemStack speed = new ItemBuilder(Material.POTION, 1).setData((short) 8226).toItemStack();
    ItemStack fire = new ItemBuilder(Material.POTION, 1).setData((short) 8259).toItemStack();

    public static FFANoDebuff getInstance() {
        return INSTANCE;
    }

    @Override
    public void onEnable() {
        INSTANCE = this;
        saveDefaultConfig();

        location1 = new Location(Bukkit.getWorld(getConfig().getString("world")), getConfig().getInt("pos1.x"), getConfig().getInt("pos1.y"), getConfig().getInt("pos1.z"));
        location2 = new Location(Bukkit.getWorld(getConfig().getString("world")), getConfig().getInt("pos2.x"), getConfig().getInt("pos2.y"), getConfig().getInt("pos2.z"));
        spawnLocation = new Location(Bukkit.getWorld(getConfig().getString("world")), getConfig().getInt("spawn.x"), getConfig().getInt("spawn.y"), getConfig().getInt("spawn.z"));

        joinMessage = getConfig().getString("JoinMessage.customMessage").replace("&", "§");
        quitMessage = getConfig().getString("QuitMessage.customMessage").replace("&", "§");
        deathMessage = getConfig().getString("DeathMessage.customMessage").replace("&", "§");
        underName = getConfig().getString("healthUnderName.suffix").replace("&", "§");

        getServer().getPluginManager().registerEvents(new LSpawn(), this);
        getServer().getPluginManager().registerEvents(new LConnection(), this);
        getServer().getPluginManager().registerEvents(new LDeath(), this);


    }

    public static boolean inCuboid(Location start, Location end, Location object){
        if(start == null || end == null || object == null) return false;

        double a = object.getY();
        double b = start.getY();
        double c = end.getY();

        if(b > c){
            if(a > b || a < c) return false;
        }else if(a > c || a < b) return false;

        a = object.getX();
        b = start.getX();
        c = end.getX();

        if(b > c){
            if(a > b || a < c) return false;
        }else if(a > c || a < b) return false;

        a = object.getZ();
        b = start.getZ();
        c = end.getZ();

        if(b > c){
            if(a > b || a < c) return false;
        }else if(a > c || a < b) return false;


        return true;
    }

    public void sendActionText(Player player, String message) {
        PacketPlayOutChat packet = new PacketPlayOutChat(new ChatComponentText(message), (byte) 2);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
    }

    public void giveStuff(Player player) {
        Inventory inv = player.getInventory();
        player.removePotionEffect(PotionEffectType.SPEED);
        player.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
        player.getInventory().setHeldItemSlot(0);
        inv.clear();
        inv.setItem(0, new ItemBuilder(Material.DIAMOND_SWORD).addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 3).addUnsafeEnchantment(Enchantment.DURABILITY, 3).addUnsafeEnchantment(Enchantment.FIRE_ASPECT, 2).toItemStack());
        inv.setItem(1, new ItemBuilder(Material.ENDER_PEARL, 16).toItemStack());
        inv.setItem(2, speed);
        inv.setItem(3, fire);
        for (int i = 4; i <= 35; i++) {
            inv.setItem(i, heal);
        }
        inv.setItem(8, new ItemBuilder(Material.COOKED_BEEF, 64).toItemStack());
        inv.setItem(17, speed);
        inv.setItem(26, speed);
        inv.setItem(35, speed);
        inv.setItem(36, new ItemBuilder(Material.DIAMOND_BOOTS).addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2).addUnsafeEnchantment(Enchantment.DURABILITY, 3).addUnsafeEnchantment(Enchantment.PROTECTION_FALL, 4).toItemStack());
        inv.setItem(37, new ItemBuilder(Material.DIAMOND_LEGGINGS).addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2).addUnsafeEnchantment(Enchantment.DURABILITY, 3).toItemStack());
        inv.setItem(38, new ItemBuilder(Material.DIAMOND_CHESTPLATE).addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2).addUnsafeEnchantment(Enchantment.DURABILITY, 3).toItemStack());
        inv.setItem(39, new ItemBuilder(Material.DIAMOND_HELMET).addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2).addUnsafeEnchantment(Enchantment.DURABILITY, 3).toItemStack());
    }

    @Override
    public void onDisable() {
    }
}
