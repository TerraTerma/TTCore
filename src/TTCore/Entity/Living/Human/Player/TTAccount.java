package TTCore.Entity.Living.Human.Player;

import org.bukkit.OfflinePlayer;

import TTCore.Mech.DataStoreTypes.SavableDataStore;

/**
 * 
 * @author mosemister (Evan)
 * @since 01/08/2016 (DD/MM/YYYY) 14:00 (24 hour - UK time)
 * @git First upload of the new API
 * ---------------------------------------------------------
 *
 * @author mosemister (Evan)
 * @since 01/08/2016 (DD/MM/YYYY) 20:59 (24 hour - UK time)
 * @git Added savable mechs
 * ---------------------------------------------------------
 * 
 */

public interface TTAccount extends SavableDataStore {
	
	public OfflinePlayer getPlayer();

}
