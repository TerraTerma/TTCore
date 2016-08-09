package TTCore.Entity.Living.Human.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import TTCore.Entity.TTEntity;
import TTCore.Entity.Implementation.Living.Human.Player.TTAccountImp;
import TTCore.Mech.DataStores.SavableDataStore;

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
 * @since 06/08/2016
 * @git added static methods for getting offlineAccount
 */

public interface TTAccount extends SavableDataStore {

	public OfflinePlayer getPlayer();
	
	/**
	 * gets all accounts that have ever been on the TTServer
	 * @return all possible accounts the server knows about
	 */
	public static List<TTAccount> getAccounts(){
		List<TTAccount> accounts = new ArrayList<>();
		File folder = new File(TTEntity.ROOT_FILE, "Player/");
		File[] files = folder.listFiles();
		if(files != null){
			List<UUID> onlineUUID = new ArrayList<>();
			TTPlayer.getPlayers().stream().forEach(p -> {
				TTPlayer player = (TTPlayer)p;
				onlineUUID.add(player.getPlayer().getUniqueId());
			});
			Arrays.asList(files).stream().forEach(f -> {
				UUID uuid = UUID.fromString(f.getName().replace(".yml", ""));
				if(onlineUUID.contains(uuid)){
					TTAccount account = new TTAccountImp(Bukkit.getOfflinePlayer(uuid));
					accounts.add(account);
				}else{
					TTAccount account = TTPlayer.getPlayer(uuid).get();
					accounts.add(account);
				}
			});
		}
		return accounts;
	}

}
