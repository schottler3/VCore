package V.VCore;

import V.VCore.utils.Msg;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;

public class Elevator implements Listener {

    public int maxCheck = 32;

    public Elevator(){
        Bukkit.getPluginManager().registerEvents(this, VCore.getInstance());
    }

    @EventHandler
    public void onPlayerSneak(PlayerToggleSneakEvent event){
        Player player = event.getPlayer();
        Material blockMaterial = player.getLocation().getBlock().getType();

        if (blockMaterial.equals(Material.HEAVY_WEIGHTED_PRESSURE_PLATE) && player.isSneaking()){
            for(int i = 1; i < maxCheck; i++){
                Location testLocation = player.getLocation().clone().subtract(0, i, 0);
                if(testLocation.getBlock().getType().equals(Material.HEAVY_WEIGHTED_PRESSURE_PLATE)){
                    Location location = player.getLocation();
                    float yaw = location.getYaw();
                    float pitch = location.getPitch();
                    Location newLocation = testLocation.getBlock().getLocation().add(.5,0,.5);
                    newLocation.setPitch(pitch);
                    newLocation.setYaw(yaw);
                    player.teleport(newLocation);
                    Msg.send(player, "&bYou moved down one level");
                    break;
                }
            }
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event){
        Player player = event.getPlayer();
        Material blockMaterial = player.getLocation().getBlock().getType();

        if (blockMaterial.equals(Material.HEAVY_WEIGHTED_PRESSURE_PLATE) && event.getFrom().getY() < event.getTo().getY()) {
            for(int i = 1; i < maxCheck; i++){
                Location testLocation = player.getLocation().clone().add(0, i, 0);
                if(testLocation.getBlock().getType().equals(Material.HEAVY_WEIGHTED_PRESSURE_PLATE)){
                    Location location = player.getLocation();
                    float yaw = location.getYaw();
                    float pitch = location.getPitch();
                    Location newLocation = testLocation.getBlock().getLocation().add(.5,0,.5);
                    newLocation.setPitch(pitch);
                    newLocation.setYaw(yaw);
                    player.teleport(newLocation);
                    Msg.send(player, "&bYou moved up one level");
                    break;
                }
            }
        }
    }

}
