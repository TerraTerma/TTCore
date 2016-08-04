package TTCore.Mech.DataStoreTypes;

import java.io.File;

import TTCore.Mech.DataStore;
import TTCore.Mech.DataHandlers.SavableData;
import TTCore.Savers.Saver;

/**
 *
 * @author mosemister (Evan)
 * @since 01/08/2016 (DD/MM/YYYY) 20:59 (24 hour - UK time)
 * @git Added savable mechs
 *      ---------------------------------------------------------
 * 
 */

public interface SavableDataStore extends DataStore {

	/**
	 * gets the associated file
	 * 
	 * @return a file in the correct location
	 */
	public File getFile();

	/**
	 * saves all the Mechs
	 */
	public void saveAll();

	public static class AbstractSavableDataStore extends AbstractDataStore implements SavableDataStore {

		File FILE;

		public AbstractSavableDataStore(File file) {
			FILE = file;
		}

		@Override
		public File getFile() {
			return FILE;
		}

		@Override
		public void saveAll() {
			Saver saver = new Saver(FILE);
			getData().stream().forEach(d -> {
				if (d instanceof SavableData) {
					SavableData save = (SavableData) d;
					String path = "Mechs." + save.getClass().getSimpleName();
					saver.setSection(path);
					save.save(saver);
				}
			});
			saver.save();
		}
	}

}
