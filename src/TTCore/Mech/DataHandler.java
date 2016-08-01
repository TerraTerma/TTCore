package TTCore.Mech;

import java.util.ArrayList;
import java.util.List;

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

public interface DataHandler {
	
	
	/**
	 * You must inject your mech into the following List
	 */
	public static final List<Class<? extends DataHandler>> MECHS = new ArrayList<>();
	
	/**
	 * 
	 * @return gets all the possible Mech classes
	 */
	public static List<Class<? extends DataHandler>> getHandlers(){
		return new ArrayList<>(MECHS);
	}

}
