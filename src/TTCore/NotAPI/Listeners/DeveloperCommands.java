package TTCore.NotAPI.Listeners;

import java.util.Optional;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import TTCore.Entity.Living.Human.Player.TTPlayer;
import TTCore.Entity.NonLiving.FallingBlock.TTFallingBlock;
import TTCore.Mech.DefaultMechs.FlyingBlock;

/**
 * this is for testing purposes only
 *
 */
public class DeveloperCommands implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String length, String[] args) {
		if (sender instanceof Player) {
			TTPlayer player = TTPlayer.getPlayer((Player) sender);
			if (player.getPlayer().hasPermission("ttcore.cmd.developer")) {
				if(args.length == 0){
					return false;
				}
				if (args[0].equalsIgnoreCase("flyingBlock")) {
					TTFallingBlock entity = new TTFallingBlock(player.getPlayer().getEyeLocation(), Material.STONE, (byte) 0);
					FlyingBlock data = player.getOrCreateSingleData(FlyingBlock.class);
					data.setBlock(entity);
					//data.attemptToCorrectLocation(player);
					return true;
				}else if(args[0].equalsIgnoreCase("fireBlock")){
					player.getData(FlyingBlock.class).stream().forEach(b -> {
						Optional<TTFallingBlock> opBlock = b.getBlock();
						if(opBlock.isPresent()){
							opBlock.get().getEntity().setGravity(false);
							b.fire(player, 2);
						}
					});
					return true;
				}
			}
		}
		return false;
	}

}
