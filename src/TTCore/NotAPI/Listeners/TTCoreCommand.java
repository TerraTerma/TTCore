package TTCore.NotAPI.Listeners;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import TTCore.Entity.Living.Human.Player.TTPlayer;
import TTCore.Versions.TTVersion;

public class TTCoreCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String length, String[] args) {
		if (args.length == 0) {
			if (sender instanceof Player) {
				TTPlayer player = TTPlayer.getPlayer((Player) sender);
				player.sendMessage("/ttcore version");
			} else {
				sender.sendMessage("/ttcore version");
			}
			return true;
		} else if (args[0].equalsIgnoreCase("version")) {
			sender.sendMessage("|---[TTCore Version]---|");
			int[] version = TTVersion.VERSION.getTotalVersion();
			sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "TTCore: " + ChatColor.RESET + "" + ChatColor.AQUA
					+ version[0] + "." + version[1] + "." + version[2] + "." + version[3] + "." + version[4]);
			TTVersion.VERSION.VERSIONS.stream().forEach(v -> {
				sender.sendMessage(v.getClass().getSimpleName() + ": " + v.getMajorVersion() + "."
						+ v.getSubMajorVersion() + "." + v.getMinorVersion() + "." + v.getHotfixVersion());
			});
			return true;
		}
		return false;
	}

}
