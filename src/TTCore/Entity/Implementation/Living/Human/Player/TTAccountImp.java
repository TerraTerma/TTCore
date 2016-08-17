package TTCore.Entity.Implementation.Living.Human.Player;

import java.io.File;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.OfflinePlayer;

import TTCore.Entity.TTEntity;
import TTCore.Entity.Living.Human.Player.TTAccount;
import TTCore.Entity.Living.Human.Player.TTPlayer;
import TTCore.Mech.DataHandler;
import TTCore.Mech.DataHandlers.PlayerData;
import TTCore.Mech.DataHandlers.SavableData;
import TTCore.Mech.DataStores.SavableDataStore.AbstractSavableDataStore;
import TTCore.Savers.Saver;

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
 * @author mosemister (Evan)
 * @since 01/08/2016 (DD/MM/YYYY) 16:22 (24 hour - UK time)
 * @git Mech now work
 *      ---------------------------------------------------------
 * @author mosemister (Evan)
 * @since 03/08/2016 (DD/MM/YYYY) 20:22 (24 hour - UK time)
 * @git Mechs now save
 *      ---------------------------------------------------------
 * @author mosemister (Evan)
 * @since 04/08/2016 (DD/MM/YYYY) 13:59 (24 hour - UK time)
 * @git Section fix
 * 		---------------------------------------------------------
 */
public class TTAccountImp extends AbstractSavableDataStore implements TTAccount {

	protected OfflinePlayer PLAYER;

	public TTAccountImp(OfflinePlayer player) {
		super(new File(TTEntity.ROOT_FILE, "Player/" + player.getUniqueId() + ".yml"));
		PLAYER = player;
		DataHandler.getHandlers().stream().forEach(d -> {
			try {
				DataHandler data = d.newInstance();
				if ((data instanceof SavableData) && (data instanceof PlayerData)) {
					SavableData data2 = (SavableData) data;
					Saver saver = new Saver(getFile());
					saver.setSection("Mechs", d.getSimpleName());
					if(data2.load(saver)){
						DATA.add(data2);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	@Override
	public OfflinePlayer getPlayer() {
		return PLAYER;
	}

	@Override
	public Optional<TTPlayer> getOnline() {
		return TTPlayer.getPlayer(getPlayer().getUniqueId());
	}

	@Override
	public String getName() {
		return getPlayer().getName();
	}

	@Override
	public UUID getUUID() {
		return getPlayer().getUniqueId();
	}

	@Override
	public LocalDate getFirstJoinDate() {
		return null; /* EVAN FIX */
	}

	@Override
	public LocalDate getLastPlayedDate() {
		return null; /* EVAN FIX */
	}

	@Override
	public Location getBedSpawn() {
		return getPlayer().getBedSpawnLocation();
	}

	@Override
	public boolean isBanned() {
		return getPlayer().isBanned();
	}

	@Override
	public boolean isOnline() {
		return getPlayer().isOnline();
	}

	@Override
	public boolean hasPlayedBefore() {
		return getPlayer().hasPlayedBefore();
	}

}
