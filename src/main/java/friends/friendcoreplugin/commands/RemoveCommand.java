package V.VCore.commands;

import V.VCore.utils.CommandBase;
import V.VCore.utils.Json;
import V.VCore.utils.Msg;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RemoveCommand {

    public RemoveCommand(){
        new CommandBase("removeCommand",1, true) {
            @Override
            public boolean onCommand(CommandSender sender, String[] arguments) {
                Player player = (Player) sender;
                if(!player.isOp()) {
                    Msg.send(player, "&4You do not have permission to use this command!");
                    return true;
                }

                Json.removeJsonData(sender, "CommandsList", "/" + arguments[0]);
                return true;
            }

            @Override
            public String getUsage() {
                return "/RemoveCommand <command>";
            }
        };
    }
}