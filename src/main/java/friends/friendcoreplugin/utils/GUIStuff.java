package V.VCore.utils;

import com.earth2me.essentials.api.Economy;
import V.VCore.*;
import V.VCore.utils.Msg;
import V.VCore.utils.Items;
import V.VCore.utils.VoteUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Objects;

public class GUIStuff implements Listener {

    public GUIStuff(){
        Bukkit.getPluginManager().registerEvents(this, VCore.getInstance());
    }

    public static ItemStack[] populateGUI(int size){
        ItemStack[] testBlocks = new ItemStack[size];

        ItemStack holder = new ItemStack(Material.GRAY_STAINED_GLASS_PANE,1);
        ItemMeta meta = holder.getItemMeta();
        assert meta != null;
        meta.setDisplayName(" ");
        holder.setItemMeta(meta);

        for(int i = 0; i < size; i++){
            if(Material.values()[i].isBlock()){
                testBlocks[i] = holder;
            }
        }

        return testBlocks;
    }

    public static Inventory getGUI(int size, String name){
        Inventory gui = Bukkit.createInventory(null, size, ChatColor.DARK_GRAY + name);
        gui.setContents(populateGUI(size));
        return gui;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){
        Player player = (Player)event.getWhoClicked();
        if(Objects.equals(event.getClickedInventory(), VoteUtils.getVoteCenter())){
            if (event.getClick().equals(ClickType.LEFT)) {
                Inventory inventory = event.getClickedInventory();
                assert inventory != null;
                ItemStack item = inventory.getContents()[event.getSlot()];

                if (item != null) {
                    ItemMeta meta = item.getItemMeta();
                    if(meta == null) return;
                    PersistentDataContainer data = meta.getPersistentDataContainer();
                    PersistentDataContainer playerData = player.getPersistentDataContainer();

                    if (Objects.equals(data.get(Items.getItemKey(), PersistentDataType.STRING), "weather")) {
                        NamespacedKey weatherKey = new NamespacedKey(VCore.getInstance(), "weatherVote");

                        if (playerData.get(weatherKey, PersistentDataType.BOOLEAN) != null && Boolean.TRUE.equals(playerData.get(weatherKey, PersistentDataType.BOOLEAN))) {
                            VoteUtils.decrementWeather();
                            playerData.set(weatherKey, PersistentDataType.BOOLEAN, false);
                        } else {
                            playerData.set(weatherKey, PersistentDataType.BOOLEAN, true);
                            VoteUtils.incrementWeather();
                        }
                    } else if (Objects.equals(data.get(Items.getItemKey(), PersistentDataType.STRING), "normal")) {
                        NamespacedKey normalKey = new NamespacedKey(VCore.getInstance(), "normalVote");
                        if (playerData.get(normalKey, PersistentDataType.BOOLEAN) != null && Boolean.TRUE.equals(playerData.get(normalKey, PersistentDataType.BOOLEAN))) {
                            VoteUtils.decrementNormal();
                            playerData.set(normalKey, PersistentDataType.BOOLEAN, false);
                        } else {
                            playerData.set(normalKey, PersistentDataType.BOOLEAN, true);
                            VoteUtils.incrementNormal();
                        }
                    } else if (Objects.equals(data.get(Items.getItemKey(), PersistentDataType.STRING), "hard")) {
                        NamespacedKey hardKey = new NamespacedKey(VCore.getInstance(), "hardVote");
                        if (playerData.get(hardKey, PersistentDataType.BOOLEAN) != null && Boolean.TRUE.equals(playerData.get(hardKey, PersistentDataType.BOOLEAN))) {
                            VoteUtils.decrementHard();
                            playerData.set(hardKey, PersistentDataType.BOOLEAN, false);
                        } else {
                            playerData.set(hardKey, PersistentDataType.BOOLEAN, true);
                            VoteUtils.incrementHard();
                        }
                    } else if (Objects.equals(data.get(Items.getItemKey(), PersistentDataType.STRING), "restart")) {
                        NamespacedKey restartKey = new NamespacedKey(VCore.getInstance(), "restartVote");
                        if (playerData.get(restartKey, PersistentDataType.BOOLEAN) != null && Boolean.TRUE.equals(playerData.get(restartKey, PersistentDataType.BOOLEAN))) {
                            VoteUtils.decrementRestart();
                            playerData.set(restartKey, PersistentDataType.BOOLEAN, false);
                        } else {
                            playerData.set(restartKey, PersistentDataType.BOOLEAN, true);
                            VoteUtils.incrementRestart();
                        }
                    }
                }
            }
            player.closeInventory();
            event.setCancelled(true);
        }
    }

}