package TTCore.Mech.DataStoreTypes;

import java.io.File;

import TTCore.Mech.DataStore;

/**
 * 
 * @author mosemister (Evan)
 * @since 01/08/2016 (DD/MM/YYYY) 14:00 (24:00 - UK time)
 * @git First upload of the new API
 * ---------------------------------------------------------
 *
 */
public interface SavableDataStore extends DataStore {
	
	public File getFile();
	
	public static class AbstractSavableDataStore extends AbstractDataStore implements SavableDataStore {

		File FILE;
		
		public AbstractSavableDataStore(File file){
			FILE = file;
		}

		@Override
		public File getFile() {
			return FILE;
		}
		
	}

}
