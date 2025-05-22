package friends.friendcoreplugin.utils;

import com.google.gson.*;
import org.bukkit.command.CommandSender;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Json {

    public static JsonArray getJsonArray(CommandSender sender, String filename){

        filename = filename + ".json";

        try (FileReader reader = new FileReader(filename)) {
            Gson gson = new Gson();
            return gson.fromJson(reader, JsonArray.class);
        } catch (IOException e) {
            Msg.send(sender, "&cFile not found. Error: " + e.getMessage());
        } catch (JsonSyntaxException e) {
            return null;
        }
        return null;
    }

    public static boolean addJsonData(CommandSender sender, String filename, String field, String data){
        try {
            File file = new File(filename + ".json");
            if (!file.exists()) {
                if(file.createNewFile()){
                    Msg.send(sender, "&aFile Created: " + filename + ".json");
                }
            }

            Gson gson = new Gson();
            JsonArray jsonArray = getJsonArray(sender, filename);

            if(jsonArray == null){
                jsonArray = new JsonArray();
            }

            JsonObject newObject = new JsonObject();
            newObject.addProperty(field, data);
            jsonArray.add(newObject);

            try (FileWriter writer = new FileWriter(file)) {
                gson.toJson(jsonArray, writer);
            }

            Msg.send(sender, "&aCommand Added: " + field);
            return true;
        } catch (IOException e) {
            Msg.send(sender, "&cError writing to file");
            return false;
        }
    }

    public static boolean removeJsonData(CommandSender sender, String filename, String field){
        try {
            File file = new File(filename + ".json");
            if (!file.exists()) {
                Msg.send(sender, "&cFile not found. Error: " + filename + ".json");
                return true;
            }

            Gson gson = new Gson();
            JsonArray jsonArray = getJsonArray(sender, filename);

            if(jsonArray == null){
                Msg.send(sender, "&cFile not found. Error: " + filename + ".json");
                return true;
            }

            for(int i = 0; i < jsonArray.size(); i++){
                JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();
                if (jsonObject.has(field)) {
                    jsonArray.remove(i);
                }
            }

            try (FileWriter writer = new FileWriter(file)) {
                gson.toJson(jsonArray, writer);
            }

            Msg.send(sender, "&aCommand Deleted: " + field);
            return true;
        } catch (IOException e) {
            Msg.send(sender, "&cError writing to file");
            return false;
        }
    }
}
