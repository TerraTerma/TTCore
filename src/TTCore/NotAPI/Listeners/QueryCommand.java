package TTCore.NotAPI.Listeners;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import TTCore.Command.CommandData;
import TTCore.Entity.Living.Human.Player.TTPlayer;
import TTCore.Inventory.CustomInventory;

public class QueryCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String length, String[] args) {
		if (args.length == 0) {
			return false;
		} else {
			List<CommandData> data = CommandData.query(args[0]);
			if (sender instanceof Player) {
				TTPlayer player = TTPlayer.getPlayer((Player)sender);
				CustomInventory inv = CommandData.createInventory(data, args[0]);
				inv.apply(player);
			} else {
				if (data.isEmpty()) {
					sender.sendMessage("No commands found relating to your target");
				} else {
					data.stream().forEach(c -> {
						sender.sendMessage("command: " + c.getCommand() + ", Plugin: " + c.getRelatedPlugin().getName()
								+ ", Description: " + c.getDescription());
					});
				}
			}
			return true;
		}
	}

}
