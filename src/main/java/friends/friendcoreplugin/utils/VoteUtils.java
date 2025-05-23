package V.VCore.utils;
import V.VCore.utils.GUIStuff;
import V.VCore.utils.Items;

import V.VCore.VCore;
import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.NamespacedKey;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class VoteUtils implements Listener {

    private static int voteWeather = 0;
    private static int voteNormal = 0;
    private static int voteHard = 0;
    private static int voteRestart = 0;

    private static double superMajority = .66;
    private static Server server;
    private static Inventory voteCenter;
    private static VCore instance;

    public VoteUtils(){
        server =  VCore.getInstance().getServer();
        instance = VCore.getInstance();
        Bukkit.getPluginManager().registerEvents(this, VCore.getInstance());
        setupVoteCenter();
    }

    public static void incrementWeather(){
        voteWeather++;
        instance.sudoBroadcast("&eWeather Vote: " + (int)(getPercent(voteWeather)*100) + "%");
        if(getPercent(voteWeather) >= superMajority){
            voteWeather = 0;
            instance.sudoBroadcast("&aWeather voted to clear!");
            server.getWorlds().get(0).setStorm(false);
            for(Player p : server.getOnlinePlayers()){
                p.getPersistentDataContainer().set(new NamespacedKey(VCore.getInstance(), "weatherVote"), PersistentDataType.BOOLEAN, false);
            }
        }
    }

    public static void incrementNormal(){
        voteNormal++;
        instance.sudoBroadcast("&eNormal Vote: " + (int)(getPercent(voteNormal)*100) + "%");
        if(getPercent(voteNormal) >= superMajority){
            voteNormal = 0;
            instance.sudoBroadcast("&aDifficulty voted to normal!");
            server.getWorlds().get(0).setDifficulty(Difficulty.NORMAL);
            for(Player p : server.getOnlinePlayers()){
                p.getPersistentDataContainer().set(new NamespacedKey(VCore.getInstance(), "normalVote"), PersistentDataType.BOOLEAN, false);
            }
        }
    }

    public static void incrementHard(){
        voteHard++;
        instance.sudoBroadcast("&eHard Vote: " + (int)(getPercent(voteHard)*100) + "%");
        if(getPercent(voteHard) >= superMajority){
            voteHard = 0;
            instance.sudoBroadcast("&aDifficulty voted to hard!");
            server.getWorlds().get(0).setDifficulty(Difficulty.HARD);
            for(Player p : server.getOnlinePlayers()){
                p.getPersistentDataContainer().set(new NamespacedKey(VCore.getInstance(), "hardVote"), PersistentDataType.BOOLEAN, false);
            }
        }
    }

    public static void incrementRestart(){
        voteRestart++;
        instance.sudoBroadcast("&eRestart Vote: " + (int)(getPercent(voteRestart)*100) + "%");
        if(getPercent(voteRestart) >= superMajority){
            instance.sudoBroadcast("&aServer restart in 1min!");
            voteRestart = 0;
            for(Player p : server.getOnlinePlayers()){
                p.getPersistentDataContainer().set(new NamespacedKey(VCore.getInstance(), "restartVote"), PersistentDataType.BOOLEAN, false);
            }
            VCore.getInstance().restartSeq(60);

        }
    }

    public static void decrementWeather(){
        if (voteWeather >= 0) {
            voteWeather--;
        }
        instance.sudoBroadcast("&eWeather Vote: " + (int)(getPercent(voteWeather)*100) + "%");
    }

    public static void decrementNormal(){
        if (voteNormal >= 0) {
            voteNormal--;
        }
        instance.sudoBroadcast("&eNormal Vote: " + (int)(getPercent(voteNormal)*100) + "%");
    }

    public static void decrementHard(){
        if (voteHard >= 0) {
            voteHard--;
        }
        instance.sudoBroadcast("&eHard Vote: " + (int)(getPercent(voteHard)*100) + "%");
    }

    public static void decrementRestart(){
        if (voteRestart >= 0) {
            voteRestart--;
        }
        instance.sudoBroadcast("&eRestart Vote: " + (int)(getPercent(voteRestart)*100) + "%");
    }

    public static double getPercent(int players){
        try {
            return (((double) players) / ((double) VCore.getInstance().getServer().getOnlinePlayers().size()));
        } catch(Exception e) {
            return 0;
        }
    }

    private void setupVoteCenter(){
        Inventory inven = GUIStuff.getGUI(9, "Vote Center");
        ItemStack[] voteItems = Items.getVoteItems();

        for(int i = 0; i < voteItems.length; i++){
            inven.setItem(i, voteItems[i]);
        }

        voteCenter = inven;
    }

    public static Inventory getVoteCenter(){
        return voteCenter;
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event){
        Player player = event.getPlayer();
        PersistentDataContainer playerData = player.getPersistentDataContainer();
        if(Boolean.TRUE.equals(playerData.get(new NamespacedKey(VCore.getInstance(), "weatherVote"), PersistentDataType.BOOLEAN))){
            decrementWeather();
            playerData.set(new NamespacedKey(VCore.getInstance(), "weatherVote"), PersistentDataType.BOOLEAN, false);
        }
        if(Boolean.TRUE.equals(playerData.get(new NamespacedKey(VCore.getInstance(), "normalVote"), PersistentDataType.BOOLEAN))){
            decrementNormal();
            playerData.set(new NamespacedKey(VCore.getInstance(), "normalVote"), PersistentDataType.BOOLEAN, false);
        }
        if(Boolean.TRUE.equals(playerData.get(new NamespacedKey(VCore.getInstance(), "hardVote"), PersistentDataType.BOOLEAN))){
            decrementHard();
            playerData.set(new NamespacedKey(VCore.getInstance(), "hardVote"), PersistentDataType.BOOLEAN, false);
        }
        if(Boolean.TRUE.equals(playerData.get(new NamespacedKey(VCore.getInstance(), "restartVote"), PersistentDataType.BOOLEAN))){
            decrementRestart();
            playerData.set(new NamespacedKey(VCore.getInstance(), "restartVote"), PersistentDataType.BOOLEAN, false);
        }
    }
}
