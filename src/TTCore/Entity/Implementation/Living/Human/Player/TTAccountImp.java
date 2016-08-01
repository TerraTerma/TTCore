package TTCore.Entity.Implementation.Living.Human.Player;

import java.io.File;

import org.bukkit.OfflinePlayer;

import TTCore.Entity.TTEntity;
import TTCore.Entity.Living.Human.Player.TTAccount;
import TTCore.Mech.DataStoreTypes.SavableDataStore.AbstractSavableDataStore;

/**
 * 
 * @author mosemister (Evan)
 * @since 01/08/2016 (DD/MM/YYYY) 14:00 (24:00 - UK time)
 * @git First upload of the new API
 * ---------------------------------------------------------
 *
 */
public class TTAccountImp extends AbstractSavableDataStore implements TTAccount {

	protected OfflinePlayer PLAYER;
	
	public TTAccountImp(OfflinePlayer player) {
		super(new File(TTEntity.ROOT_FILE, "Player/" + player.getUniqueId() + ".yml"));
		PLAYER = player;
	}

	@Override
	public OfflinePlayer getPlayer() {
		return PLAYER;
	}
	
	

}
