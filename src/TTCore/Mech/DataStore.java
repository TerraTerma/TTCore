package TTCore.Mech;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import TTCore.Versions.VersionTrack;

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
 * @since 01/08/2016 (DD/MM/YYYY) 16:22 (24 hour - UK time)
 * @git Mech now work
 *      ---------------------------------------------------------
 */

public interface DataStore {

	public List<DataHandler> getData();

	public <M extends DataHandler> Optional<M> getSingleData(Class<M> mech);

	public <M extends DataHandler> List<M> getData(Class<M> data);

	public <M extends DataHandler> M getOrCreateSingleData(Class<M> mech);

	public boolean addSingleData(boolean force, DataHandler data);

	public boolean addData(DataHandler... data);

	public boolean removeData(DataHandler... mech);

	public boolean removeData(Class<? extends DataHandler> mech);

	public static class AbstractDataStore implements DataStore {

		protected List<DataHandler> DATA = new ArrayList<>();

		public AbstractDataStore() {
		}

		@Override
		public List<DataHandler> getData() {
			return DATA;
		}

		@SuppressWarnings("unchecked")
		@Override
		public <M extends DataHandler> Optional<M> getSingleData(Class<M> mech) {
			return (Optional<M>) DATA.stream().filter(m -> mech.isInstance(m)).findFirst();
		}

		@SuppressWarnings("unchecked")
		@Override
		public <M extends DataHandler> List<M> getData(Class<M> mech) {
			return (List<M>) DATA.stream().filter(m -> mech.isInstance(m)).collect(Collectors.toList());
		}

		@Override
		public <M extends DataHandler> M getOrCreateSingleData(Class<M> mech) {
			Optional<M> value = getSingleData(mech);
			if (value.isPresent()) {
				return value.get();
			} else {
				try {
					M value2 = mech.newInstance();
					addSingleData(false, value2);
					return value2;
				} catch (InstantiationException | IllegalAccessException e) {
					e.printStackTrace();
				}
			}
			return null;
		}

		@Override
		public boolean addSingleData(boolean force, DataHandler data) {
			Optional<? extends DataHandler> opMech = getSingleData(data.getClass());
			if (opMech.isPresent()) {
				if (force) {
					removeData(data);
				} else {
					return false;
				}
			}
			DATA.add(data);
			return true;
		}

		@Override
		public boolean addData(DataHandler... mech) {
			return DATA.addAll(Arrays.asList(mech));
		}

		@Override
		public boolean removeData(DataHandler... mech) {
			return DATA.removeAll(Arrays.asList(mech));
		}

		@Override
		public boolean removeData(Class<? extends DataHandler> mech) {
			DATA.stream().forEach(d -> {
				if (mech.isInstance(d)) {
					DATA.remove(d);
				}
			});
			return true;
		}

	}
	
	public static class MechVersion implements VersionTrack {

		@Override
		public int getMajorVersion() {
			return 2;
		}

		@Override
		public int getSubMajorVersion() {
			return 2;
		}

		@Override
		public int getMinorVersion() {
			return 0;
		}

		@Override
		public int getHotfixVersion() {
			return 1;
		}
		
	}

}
