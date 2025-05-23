package V.VCore.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import V.VCore.VCore;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandSendEvent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class CommandList implements Listener {

    public CommandList(){
        Bukkit.getPluginManager().registerEvents(this, VCore.getInstance());
    }

    @EventHandler
    public void showCommands(PlayerCommandSendEvent event) {
        if (!event.getPlayer().isOp()) {

            Collection<String> commands = event.getCommands();

            ArrayList<String> commandsToKeep = new ArrayList<String>();
            ArrayList<String> commandsToKill = new ArrayList<String>();

            JsonArray json = Json.getJsonArray(event.getPlayer(), "CommandsList");

            if (json != null && !json.isEmpty()) {
                for (JsonElement jsonElement : json) {
                    if (jsonElement.isJsonObject()) {
                        JsonObject jsonObject = jsonElement.getAsJsonObject();
                        for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
                            String fieldName = entry.getKey();

                            if (fieldName.startsWith("/")) {
                                fieldName = fieldName.substring(1);
                            }

                            commandsToKeep.add(fieldName);
                        }
                    }
                }
            }

            for (String cmd : commands) {
                if (!commandsToKeep.contains(cmd)) {
                    commandsToKill.add(cmd);
                }
            }

            commands.removeAll(commandsToKill);

        }
    }
}
