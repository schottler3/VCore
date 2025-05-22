package friends.friendcoreplugin.commands;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import friends.friendcoreplugin.utils.CommandBase;
import friends.friendcoreplugin.utils.Json;
import friends.friendcoreplugin.utils.Msg;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Map;

public class Commands {

    public Commands(){
        new CommandBase("commands",0,true) {
            @Override
            public boolean onCommand(CommandSender sender, String[] arguments) {
                Player player = (Player) sender;

                JsonArray json = Json.getJsonArray(sender, "CommandsList");

                if (json != null && !json.isEmpty()) {
                    for (JsonElement jsonElement : json) {
                        if (jsonElement.isJsonObject()) {
                            JsonObject jsonObject = jsonElement.getAsJsonObject();
                            for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
                                String fieldName = entry.getKey();
                                JsonElement fieldValue = entry.getValue();

                                Msg.send(sender, fieldName + ": ", "&a", fieldValue.getAsString(), "&b");
                            }
                        }
                    }
                }
                else {
                    Msg.send(sender, "&6Hmm, there's nothing here yet. Probably should report this!");
                }

                return true;
            }

            @Override
            public String getUsage() {
                return "/addCommand <command> <description>";
            }
        };
    }
}


