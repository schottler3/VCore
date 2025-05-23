package V.VCore;

import V.VCore.commands.*;
import V.VCore.utils.CommandList;
import V.VCore.utils.Msg;
import V.VCore.utils.GUIStuff;
import V.VCore.utils.VoteUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public final class VCore extends JavaPlugin {

    private static VCore instance;

    @Override
    public void onEnable() {
        instance = this;
        // Plugin startup logic
        new AddCommand();
        new RemoveCommand();
        new Commands();
        new VoteUtils();
        new GUIStuff();
        new Vote();
        new VoteUtils();
        new Elevator();
        new CommandList();

        sudoBroadcast("VCore enabled");
        
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        sudoBroadcast("VCore disabled");
    }

    public static VCore getInstance() {
        return instance;
    }

    public void restartSeq(int time){
        new BukkitRunnable() {
            int remainingTime = time;

            @Override
            public void run() {
                if (remainingTime == 0) {
                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "restart");
                }
                else if(remainingTime <= 10){
                    sudoBroadcast("Server Restart in " + remainingTime + " seconds");
                }
                remainingTime--;
            }
        }.runTaskTimer(VCore.getInstance(), 0L, 20L);
    }

    public static void sudoBroadcast(String message){
        for (Player player : Bukkit.getOnlinePlayers()) {
            Msg.send(player, message);
        }
    }

    public static void throwError(String message){
        VCore.getInstance().getLogger().severe("Error: " + message);
        sudoBroadcast(message);
    }

}
