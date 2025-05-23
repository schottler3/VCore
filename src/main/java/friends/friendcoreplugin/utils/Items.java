package V.VCore.utils;

import V.VCore.VCore;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class Items {
    public Items(){}

    public static void give(Player player, ItemStack item){
        Location local = player.getLocation();
        local.getWorld().dropItemNaturally(local,item);
    }

    public static NamespacedKey getItemKey(){
        return new NamespacedKey(VCore.getInstance(), "item");
    }

    public static ItemStack[] getVoteItems(){
        ItemStack[] voteIcons = new ItemStack[4];

        ItemStack weather = new ItemStack(Material.WATER_BUCKET);
        ItemMeta weatherMeta = weather.getItemMeta();
        PersistentDataContainer weatherdata = weatherMeta.getPersistentDataContainer();
        weatherdata.set(getItemKey(), PersistentDataType.STRING, "weather");
        weatherMeta.setDisplayName(ChatColor.RESET + "Clear Weather");
        weather.setItemMeta(weatherMeta);
        voteIcons[0] = weather;

        ItemStack normal = new ItemStack(Material.IRON_SWORD);
        ItemMeta normalMeta = normal.getItemMeta();
        PersistentDataContainer normaldata = normalMeta.getPersistentDataContainer();
        normaldata.set(getItemKey(), PersistentDataType.STRING, "normal");
        normalMeta.setDisplayName(ChatColor.RESET + "Difficulty Normal");
        normal.setItemMeta(normalMeta);
        voteIcons[1] = normal;

        ItemStack hard = new ItemStack(Material.DIAMOND_SWORD);
        ItemMeta hardMeta = normal.getItemMeta();
        PersistentDataContainer harddata = hardMeta.getPersistentDataContainer();
        harddata.set(getItemKey(), PersistentDataType.STRING, "hard");
        hardMeta.setDisplayName(ChatColor.RESET + "Difficulty Hard");
        hard.setItemMeta(hardMeta);
        voteIcons[2] = hard;

        ItemStack restart = new ItemStack(Material.COMPASS);
        ItemMeta restartMeta = normal.getItemMeta();
        PersistentDataContainer restartdata = restartMeta.getPersistentDataContainer();
        restartdata.set(getItemKey(), PersistentDataType.STRING, "restart");
        restartMeta.setDisplayName(ChatColor.RESET + "Server Restart");
        restart.setItemMeta(restartMeta);
        voteIcons[3] = restart;

        return voteIcons;
    }
}