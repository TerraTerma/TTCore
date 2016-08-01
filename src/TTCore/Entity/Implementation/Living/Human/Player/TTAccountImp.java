package TTCore.Entity.Implementation.Living.Human.Player;

import java.io.File;

import org.bukkit.OfflinePlayer;

import TTCore.Entity.TTEntity;
import TTCore.Entity.Living.Human.Player.TTAccount;
import TTCore.Mech.DataHandler;
import TTCore.Mech.DataHandlers.SavableData;
import TTCore.Mech.DataStoreTypes.SavableDataStore.AbstractSavableDataStore;
import TTCore.Mech.Stores.PlayerData;
import TTCore.Savers.Saver;

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
public class TTAccountImp extends AbstractSavableDataStore implements TTAccount {

	protected OfflinePlayer PLAYER;
	
	public TTAccountImp(OfflinePlayer player) {
		super(new File(TTEntity.ROOT_FILE, "Player/" + player.getUniqueId() + ".yml"));
		PLAYER = player;
		DataHandler.getHandlers().stream().forEach(d -> {
			if (d.isAssignableFrom(SavableData.class) && (d.isAssignableFrom(PlayerData.class))){
				try {
					SavableData data = (SavableData)d.newInstance();
					Saver saver = new Saver(getFile());
					String[] target = {"Mech", d.getSimpleName()};
					saver.set("Target", target);
					saver.setSection(target);
					data.load(saver);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	@Override
	public OfflinePlayer getPlayer() {
		return PLAYER;
	}
	
	

}
