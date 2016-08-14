package TTCore;

import java.util.List;

import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import TTCore.Mech.DataHandler;
import TTCore.Mech.DefaultMechs.MessageFormatData;
import TTCore.NotAPI.Listeners.DeveloperCommands;
import TTCore.NotAPI.Listeners.Listeners;
import TTCore.NotAPI.Listeners.TTCoreCommand;
import TTCore.Versions.TTVersion;
import net.milkbowl.vault.economy.Economy;

public class TTCoreMain extends JavaPlugin {

	private static Economy ECO;
	private static TTCoreMain PLUGIN;

	public void onEnable() {
		PLUGIN = this;
		getServer().getPluginManager().registerEvents(new Listeners(), this);
		getCommand("developer").setExecutor(new DeveloperCommands());
		getCommand("ttcore").setExecutor(new TTCoreCommand());
		new TTVersion();
		setupEco();
		loadMechs();
	}
	
	private void loadMechs(){
		List<Class<? extends DataHandler>> mechs = DataHandler.MECHS;
		mechs.add(MessageFormatData.class);
	}

	private Economy setupEco() {
		RegisteredServiceProvider<Economy> provider = getServer().getServicesManager().getRegistration(Economy.class);
		ECO = provider.getProvider();
		return ECO;
	}

	public Economy getVaultEco() {
		return ECO;
	}

	public static TTCoreMain getPlugin() {
		return PLUGIN;
	}

}
