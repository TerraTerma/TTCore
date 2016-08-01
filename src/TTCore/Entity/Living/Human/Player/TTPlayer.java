package TTCore.Entity.Living.Human.Player;

import java.util.Optional;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import TTCore.Entity.TTEntity;
import TTCore.Entity.Implementation.Living.Human.Player.TTPlayerImp;

/**
 * 
 * @author mosemister (Evan)
 * @since 01/08/2016 (DD/MM/YYYY) 14:00 (24:00 - UK time)
 * @git First upload of the new API
 * ---------------------------------------------------------
 *
 */
public interface TTPlayer extends TTEntity, TTAccount{
	
	/**
	 * this sends a player a message in the TTCore format. 
	 * @param plugin = Your plugin root class
	 * @param message = the message the player will gain
	 */
	public void sendMessage(Plugin plugin, String message);
	
	/**
	 * this sends a player a message in the TTCore format
	 * @param prefix = a prefix
	 * @param message = the message the player will gain
	 */
	public void sendMessage(String prefix, String message);
	
	/**
	 * @param return = gets the Bukkit Player object
	 */
	public Player getPlayer();
	
	/**
	 * @param return = gets the Bukkkit Player object
	 */
	public Player getEntity();
	
	
	/**
	 * convert a player object to the TTPlayer object
	 * @param player = Bukkit Player object
	 * @return = TTPlayer object
	 */
	public static TTPlayer getPlayer(Player player){
		Optional<TTEntity> opEntity = REGISTERED_ENTITIES.stream().filter(e -> {
			Entity entity = e.getEntity();
			if(entity instanceof Player){
				return (entity.equals(player));
			}
			return false;
		}).findFirst();
		
		if(opEntity.isPresent()){
			return (TTPlayer)opEntity.get();
		}else{
			return new TTPlayerImp(player);
		}
	}
}
