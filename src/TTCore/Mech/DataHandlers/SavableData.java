package TTCore.Mech.DataHandlers;

import TTCore.Mech.DataHandler;
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

public interface SavableData extends DataHandler {
	
	/**
	 * will innate when TTCore is backing up its data.
	 * please don't target.
	 * @param saver = the exact location to save the Mech data
	 */
	public void save(Saver saver);
	
	/**
	 * will innate when TTCore needs to
	 * will restart the Mech data - don't use
	 * @param saver = the exact location to load from
	 */
	public void load(Saver saver);

}
