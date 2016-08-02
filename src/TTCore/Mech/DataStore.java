package TTCore.Mech;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

public interface DataStore {

	public List<DataHandler> getData();

	public <M extends DataHandler> Optional<M> getSingleData(Class<M> mech);

	public <M extends DataHandler> List<M> getData(Class<M> data);

	public <M extends DataHandler> Optional<M> getOrCreateSingleData(Class<M> mech, Object... requirements);

	public boolean addSingleData(boolean force, DataHandler data);

	public boolean addData(DataHandler... data);

	public boolean removeData(DataHandler... mech);

	public boolean removeData(Class<? extends DataHandler> mech);

	public static class AbstractDataStore implements DataStore {

		List<DataHandler> DATA = new ArrayList<>();

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
		public <M extends DataHandler> Optional<M> getOrCreateSingleData(Class<M> mech, Object... requirements) {
			Optional<M> value = getSingleData(mech);
			if (value.isPresent()) {
				return value;
			} else {
				@SuppressWarnings("unchecked")
				Class<? extends Object>[] cArgs = new Class[requirements.length];
				for (int A = 0; A < requirements.length; A++) {
					cArgs[A] = requirements[A].getClass();
				}
				try {
					M value2 = mech.getDeclaredConstructor(cArgs).newInstance(requirements);
					addSingleData(false, value2);
					return Optional.of(value2);
				} catch (Exception e) {
					return Optional.empty();
				}
			}
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

}
