package TTCore.Entity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import TTCore.Entity.Living.Human.Player.TTPlayer;
import TTCore.Mech.DataHandler;
import TTCore.Mech.DataStore;

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
 */

public interface TTEntity {

	/**
	 * ROOT FILE should not be used, please get getFile() instead.
	 */
	public static final File ROOT_FILE = new File("plugins/TTCore/Entity/");

	/**
	 * this stores all the registered TTEntities. Please note all player objects
	 * get stored and removed when a player joins/leaves
	 */
	static final List<TTEntity> REGISTERED_ENTITIES = new ArrayList<>();

	/**
	 * This will return the related Bukkit Entity object.
	 * 
	 * @return = gets the Bukkit Entity of the attached Entity
	 */
	public Entity getEntity();

	/**
	 * 
	 * @return = returns all registered Entities in a modifiable list
	 */
	public static List<TTEntity> getEntities() {
		return new ArrayList<>(REGISTERED_ENTITIES);
	}

	/**
	 * converts a Bukkit Enitty to the TTEntity
	 * 
	 * @param entity
	 *            = a bukkit entity
	 * @return = a TTEntity
	 */
	public static TTEntity convert(Entity entity) {
		if (entity instanceof Player) {
			return TTPlayer.getPlayer((Player) entity);
		} else {
			new IOException("Entity can not be converted into a TTEntity").printStackTrace();
			return null;
		}
	}
	
	/**
	 * gets the TTEntity that relates to a mech
	 * @param data = TTEntity mech
	 * @return = the TTEntity
	 */
	public static Optional<TTEntity> getRelatedMech(DataHandler data){
		return TTEntity.REGISTERED_ENTITIES.stream().filter(p -> {
			if(p instanceof DataStore){
				DataStore store = (DataStore)p;
				return store.getData().contains(data);
			}
			return false;
		}).findFirst();
	}

}
