package V.VCore.commands;

import V.VCore.utils.CommandBase;
import V.VCore.utils.Json;
import V.VCore.utils.Msg;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AddCommand {

    public AddCommand(){
        new CommandBase("addCommand",true) {
            @Override
            public boolean onCommand(CommandSender sender, String[] arguments) {
                Player player = (Player) sender;
                if(!player.isOp()) {
                    Msg.send(player, "&4You do not have permission to use this command!");
                    return true;
                }

                String des = "";
                for (int i = 1; i < arguments.length; i++) {
                    des += arguments[i] + " ";
                }

                Json.addJsonData(sender, "CommandsList", "/" + arguments[0], des);

                return true;

            }

            @Override
            public String getUsage() {
                return "/AddCommand <command> <description>";
            }
        };
    }
}