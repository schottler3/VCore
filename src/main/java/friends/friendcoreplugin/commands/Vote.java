package friends.friendcoreplugin.commands;

import friends.friendcoreplugin.utils.CommandBase;
import friends.friendcoreplugin.utils.VoteUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class Vote {

    public Vote(){
        new CommandBase("vote", true) {
            @Override
            public boolean onCommand(CommandSender sender, String [] arguments) {
                if(sender instanceof Player player){

                    Inventory inven = VoteUtils.getVoteCenter();

                    player.openInventory(inven);
                }
                return true;
            }

            @Override
            public String getUsage() {
                return "/vote";
            }
        }.enableDelay(5);


    }
}