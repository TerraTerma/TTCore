package TTCore.Command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Nullable;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import TTCore.Entity.Living.Human.Player.TTPlayer;
import TTCore.Inventory.CustomInventory;
import TTCore.Inventory.CustomItemStack;

public class CommandData {

	Plugin PLUGIN;
	String COMMAND;
	String DESCRIPTION;
	List<String> ALIASES;

	public CommandData(String command, String description, Plugin plugin, List<String> aliases) {
		PLUGIN = plugin;
		COMMAND = command;
		DESCRIPTION = description;
		ALIASES = aliases;
	}

	public Plugin getRelatedPlugin() {
		return PLUGIN;
	}

	public String getCommand() {
		return COMMAND;
	}

	public String getDescription() {
		return DESCRIPTION;
	}

	public List<String> getAlies() {
		return ALIASES;
	}

	public boolean isRelated(String relate) {
		if (StringUtils.containsIgnoreCase(COMMAND, relate)) {
			return true;
		}
		if (StringUtils.containsIgnoreCase(DESCRIPTION, relate)) {
			return true;
		}
		return ALIASES.stream().anyMatch(a -> {
			return StringUtils.containsIgnoreCase(a, relate);
		});
	}
	
	public static CustomInventory createInventory(List<CommandData> data, @Nullable String name){
		CustomInventory inv;
		if(name == null){
			inv = new CustomInventory();
		}else{
			inv = new CustomInventory(name);
		}
		data.stream().forEach(cmd -> {
			inv.getItems().add(new CustomItemStack(Material.COMMAND, "/" + cmd.getCommand(), Arrays.asList("From: " + cmd.getRelatedPlugin().getName(), "Description: " + cmd.getDescription())){

				@Override
				public boolean onClick(CustomInventory inv, TTPlayer player) {
					return true;
				}
				
			});
		});
		return inv;
	}

	public static List<CommandData> query(String name) {
		return getCommands().stream().filter(c -> c.isRelated(name)).collect(Collectors.toList());
	}

	public static List<CommandData> getCommands() {
		List<CommandData> data = new ArrayList<>();
		for (Plugin plugin : Bukkit.getPluginManager().getPlugins()) {
			if (plugin instanceof JavaPlugin) {
				data.addAll(getCommands((JavaPlugin) plugin));
			}
		}
		return data;
	}

	public static List<CommandData> getCommands(JavaPlugin plugin) {
		List<CommandData> cmds = new ArrayList<>();
		List<String> commands = new ArrayList<>();
		Map<String, Map<String, Object>> commands2 = plugin.getDescription().getCommands();
		if (commands2 != null) {
			commands2.keySet().stream().forEach(c -> {
				commands.add(c);
			});
			commands.stream().forEach(c -> {
				PluginCommand cmd = plugin.getCommand(c);
				CommandData data = new CommandData(c, cmd.getDescription(), plugin, cmd.getAliases());
				cmds.add(data);
			});
		}
		return cmds;
	}

}
