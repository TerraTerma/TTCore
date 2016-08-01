package TTCore.Entity.Living.Human.Player;

import org.bukkit.OfflinePlayer;

import TTCore.Mech.DataStoreTypes.SavableDataStore;

/**
 * 
 * @author mosemister (Evan)
 * @since 01/08/2016 (DD/MM/YYYY) 14:00 (24:00 - UK time)
 * @git First upload of the new API
 * ---------------------------------------------------------
 *
 */
public interface TTAccount extends SavableDataStore {
	
	public OfflinePlayer getPlayer();

}
