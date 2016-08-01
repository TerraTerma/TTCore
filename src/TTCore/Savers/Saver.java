package TTCore.Savers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

/**
 *
 * @author mosemister (Evan)
 * @since 01/08/2016 (DD/MM/YYYY) 20:59 (24 hour - UK time)
 * @git Added savable mechs
 * ---------------------------------------------------------
 * 
 */

public class Saver {
	
	protected File FILE;
	protected YamlConfiguration CONFIG;
	protected ConfigurationSection SECTION;
	
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
		if(SECTION == null){
			object = CONFIG.get(location);
		}else{
			object = SECTION.get(location);
		}
		return (T) object;
	}

	@SuppressWarnings("unchecked")
	public <T extends Object> T get(Class<T> type, String[]... loc) {
		String location = null;
		for (String[] loc2 : loc) {
			for (String loc3 : loc2) {
				if (location == null) {
					location = loc3;
				} else {
					location = (location + "." + loc3);
				}
			}
		}
		Object object = null;
		if(SECTION == null){
			object = CONFIG.get(location);
		}else{
			object = SECTION.get(location);
		}
		return (T) object;
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
		SECTION = CONFIG.getConfigurationSection(location);
		return this;
	}
	
	public Saver setSection(String[]... loc){
		String location = null;
		for (String[] loc2 : loc) {
			for (String loc3 : loc2) {
				if (location == null) {
					location = loc3;
				} else {
					location = (location + "." + loc3);
				}
			}
		}
		SECTION = CONFIG.getConfigurationSection(location);
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
		if(SECTION == null){
			list = CONFIG.getList(location);
		}else{
			list = SECTION.getList(location);
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
	public <T extends Object> List<T> getList(Class<T> type, String[]... loc) {
		String location = null;
		for (String[] loc2 : loc) {
			for (String loc3 : loc2) {
				if (location == null) {
					location = loc3;
				} else {
					location = (location + "." + loc3);
				}
			}
		}
		List<?> list = null;
		if(SECTION == null){
			list = CONFIG.getList(location);
		}else{
			list = SECTION.getList(location);
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
		if(SECTION == null){
			CONFIG.set(location, object);
		}else{
			SECTION.set(location, object);
		}
		return this;
	}
	
	public Saver set(Object object, ConfigurationSection section, String... loc){
		String location = section.getCurrentPath();
		for (String loc2 : loc) {
				location = (location + "." + loc2);
		}
		if(SECTION == null){
			CONFIG.set(location, object);
		}else{
			SECTION.set(location, object);
		}
		return this;
	}

	public Saver set(Object object, String[]... loc) {
		String location = null;
		for (String[] loc2 : loc) {
			for (String loc3 : loc2) {
				if (location == null) {
					location = loc3;
				} else {
					location = (location + "." + loc3);
				}
			}
		}
		if(SECTION == null){
			CONFIG.set(location, object);
		}else{
			SECTION.set(location, object);
		}
		return this;
	}
	
	public Saver set(Object object, ConfigurationSection section, String[]... loc) {
		String location = section.getCurrentPath();
		for (String[] loc2 : loc) {
			for (String loc3 : loc2) {
				if (location == null) {
					location = loc3;
				} else {
					location = (location + "." + loc3);
				}
			}
		}
		if(SECTION == null){
			CONFIG.set(location, object);
		}else{
			SECTION.set(location, object);
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

}
