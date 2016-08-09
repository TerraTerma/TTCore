package TTCore.Entity.Living.Human.Player;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import TTCore.Entity.TTEntity;
import TTCore.Entity.Implementation.Living.Human.Player.TTPlayerImp;

/**
 * 
 * @author mosemister (Evan)
 * @since 01/08/2016 (DD/MM/YYYY) 14:00 (24 hour - UK time)
 * @git First upload of the new API
 *      ---------------------------------------------------------
 *
 * @author mosemister (Evan)
 * @since 01/08/2016 (DD/MM/YYYY) 20:59 (24 hour - UK time)
 * @git Added savable mechs
 *      ---------------------------------------------------------
 * 
 * @author mosemister (Evan)
 * @since 01/08/2016 (DD/MM/YYYY) 16:22 (24 hour - UK time)
 * @git Mech now work
 *      ---------------------------------------------------------
 * 
 */

public interface TTPlayer extends TTEntity, TTAccount {

	/**
	 * this sends a player a message in the TTCore format.
	 * 
	 * @param plugin
	 *            = Your plugin root class
	 * @param message
	 *            = the message the player will gain
	 */
	public void sendMessage(Plugin plugin, String message);

	/**
	 * this sends a player a message in the TTCore format
	 * 
	 * @param prefix
	 *            = a prefix
	 * @param message
	 *            = the message the player will gain
	 */
	public void sendMessage(String prefix, String message);

	/**
	 * this forces the player to send a message
	 * 
	 * @param message
	 *            = the message
	 */
	public void sendMessageAsPlayer(String message);

	/**
	 * gets the amount of money the player currently has
	 * 
	 * @return the balance of the player
	 */
	public double getBalance();

	/**
	 * @param balance
	 *            = checks if the player has this amount of money
	 * @return = if the player has the amount
	 */
	public boolean hasBalance(double balance);

	/**
	 * @param amount
	 *            = removes this amount from the player
	 * @return = if the amount can be removed
	 */
	public boolean withdraw(double amount);

	/**
	 * @param amount
	 *            = adds this amount to the player
	 * @return = if the amount can be added
	 */
	public boolean deposit(double amount);
	
	public List<BossBar> getBars();
	
	public BossBar createBar(boolean override, BarColor colour, BarStyle style, String message, BarFlag... flags);
	
	public TTPlayer removeBars();

	/**
	 * @param return
	 *            = gets the Bukkit Player object
	 */
	public Player getPlayer();

	/**
	 * @param return
	 *            = gets the Bukkkit Player object
	 */
	public Player getEntity();

	/**
	 * convert a player object to the TTPlayer object
	 * 
	 * @param player
	 *            = Bukkit Player object
	 * @return = TTPlayer object
	 */
	public static TTPlayer getPlayer(Player player) {
		Optional<TTEntity> opEntity = REGISTERED_ENTITIES.stream().filter(e -> {
			Entity entity = e.getEntity();
			if (entity instanceof Player) {
				return (entity.equals(player));
			}
			return false;
		}).findFirst();

		if (opEntity.isPresent()) {
			return (TTPlayer) opEntity.get();
		} else {
			return new TTPlayerImp(player);
		}
	}
	
	public static Optional<TTPlayer> getPlayer(UUID uuid){
		Optional<TTEntity> opEntity = REGISTERED_ENTITIES.stream().filter(e -> {
			Entity entity = e.getEntity();
			if (entity instanceof Player) {
				return (((TTPlayer)entity).getPlayer().getUniqueId().equals(uuid));
			}
			return false;
		}).findFirst();
		if(opEntity.isPresent()){
			return Optional.of((TTPlayer)opEntity.get());
		}
		return Optional.empty();
	}
	
	public static List<TTEntity> getPlayers(){
		List<TTEntity> entities = REGISTERED_ENTITIES.stream().filter(e -> {
			Entity entity = e.getEntity();
			if (entity instanceof Player) {
				return true;
			}
			return false;
		}).collect(Collectors.toList());
		return entities;
	}
}
