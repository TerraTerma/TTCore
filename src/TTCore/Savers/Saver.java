package TTCore.Savers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.bukkit.configuration.file.YamlConfiguration;

import TTCore.Versions.VersionTrack;

/**
 *
 * @author mosemister (Evan)
 * @since 01/08/2016 (DD/MM/YYYY) 20:59 (24 hour - UK time)
 * @git Added savable mechs
 *      ---------------------------------------------------------
 * @author mosemister (Evan)
 * @since 03/08/2016 (DD/MM/YYYY) 22:35 (24 hour - UK time)
 * @git Mechs now save ---------------------------------------------------------
 * @author mosemister (Evan)
 * @since 04/08/2016 (DD/MM/YYYY) 13:59 (24 hour - UK time)
 * @git Section fix ---------------------------------------------------------
 * 
 */

public class Saver {

	protected File FILE;
	protected YamlConfiguration CONFIG;
	protected String SECTION;

	public Saver(File file) {
		FILE = file;
		CONFIG = YamlConfiguration.loadConfiguration(file);
	}

	public File getFile() {
		return FILE;
	}

	public YamlConfiguration getConfig() {
		return CONFIG;
	}

	public void remove(String... path) {
		set(null, path);
	}

	public void remove(String[] args, String... path) {
		set(null, args, path);
	}

	@SuppressWarnings("unchecked")
	public <T extends Object> T get(Class<T> type, String... loc) {
		String location = null;
		for (String loc2 : loc) {
			if (location == null) {
				location = loc2;
			} else {
				location = (location + "." + loc2);
			}
		}
		Object object = null;
		if (SECTION == null) {
			object = CONFIG.get(location);
		} else {
			object = CONFIG.get(SECTION + "." + location);
		}
		return (T) object;
	}

	@SuppressWarnings("unchecked")
	public <T extends Object> T get(Class<T> type, String[] path, String... loc) {
		String location = null;
		for (String loc2 : path) {
			if (location == null) {
				location = loc2;
			} else {
				location = (location + "." + loc2);
			}
		}
		for (String loc2 : loc) {
			if (location == null) {
				location = loc2;
			} else {
				location = (location + "." + loc2);
			}
		}
		Object object = null;
		if (SECTION == null) {
			object = CONFIG.get(location);
		} else {
			object = CONFIG.get(SECTION + "." + location);
		}
		return (T) object;
	}

	public Optional<String> getSection() {
		return Optional.ofNullable(SECTION);
	}

	public Map<String, Object> values(boolean embed) {
		Map<String, Object> map = new HashMap<>();
		CONFIG.getValues(embed).entrySet().forEach(e -> {
			map.put(e.getKey().replace(SECTION, ""), e.getValue());
		});
		return map;
	}

	public Saver setSection(String... loc) {
		String location = null;
		for (String loc2 : loc) {
			if (location == null) {
				location = loc2;
			} else {
				location = (location + "." + loc2);
			}
		}
		SECTION = location;
		return this;
	}

	public Saver setSection(String[] args, String... loc) {
		String location = null;
		for (String loc2 : args) {
			if (location == null) {
				location = loc2;
			} else {
				location = (location + "." + loc2);
			}
		}
		for (String loc2 : loc) {
			if (location == null) {
				location = loc2;
			} else {
				location = (location + "." + loc2);
			}
		}
		SECTION = location;
		return this;
	}

	@SuppressWarnings("unchecked")
	public <T extends Object> List<T> getList(Class<T> type, String... loc) {
		String location = null;
		for (String loc2 : loc) {
			if (location == null) {
				location = loc2;
			} else {
				location = (location + "." + loc2);
			}
		}
		List<?> list = null;
		if (SECTION == null) {
			list = CONFIG.getList(location);
		} else {
			list = CONFIG.getList(SECTION + "." + location);
		}
		if (list != null) {
			List<T> list2 = new ArrayList<T>();
			for (Object object : list) {
				list2.add((T) object);
			}
			return list2;
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public <T extends Object> List<T> getList(Class<T> type, String[] path, String... loc) {
		String location = null;
		for (String loc2 : path) {
			if (location == null) {
				location = loc2;
			} else {
				location = (location + "." + loc2);
			}
		}
		for (String loc2 : loc) {
			if (location == null) {
				location = loc2;
			} else {
				location = (location + "." + loc2);
			}
		}
		List<?> list = null;
		if (SECTION == null) {
			list = CONFIG.getList(location);
		} else {
			list = CONFIG.getList(SECTION + "." + location);
		}
		if (list != null) {
			List<T> list2 = new ArrayList<T>();
			for (Object object : list) {
				list2.add((T) object);
			}
			return list2;
		}
		return null;
	}

	public Saver set(Object object, String... loc) {
		String location = null;
		for (String loc2 : loc) {
			if (location == null) {
				location = loc2;
			} else {
				location = (location + "." + loc2);
			}
		}
		if (SECTION == null) {
			CONFIG.set(location, object);
		} else {
			CONFIG.set(SECTION + "." + location, object);
		}
		return this;
	}

	public Saver set(Object object, String[] path, String... loc) {
		String location = null;
		for (String loc2 : path) {
			if (location == null) {
				location = loc2;
			} else {
				location = (location + "." + loc2);
			}
		}
		for (String loc2 : loc) {
			if (location == null) {
				location = loc2;
			} else {
				location = (location + "." + loc2);
			}
		}
		if (SECTION == null) {
			CONFIG.set(location, object);
		} else {
			CONFIG.set(SECTION + "." + location, object);
		}
		return this;
	}

	public boolean save() {
		try {
			CONFIG.save(FILE);
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	public static class SaverVersion implements VersionTrack {

		@Override
		public int getMajorVersion() {
			return 1;
		}

		@Override
		public int getSubMajorVersion() {
			return 1;
		}

		@Override
		public int getMinorVersion() {
			return 0;
		}

		@Override
		public int getHotfixVersion() {
			return 0;
		}

	}

}
