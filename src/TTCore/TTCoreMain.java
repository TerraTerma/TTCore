package TTCore;

import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import TTCore.NotAPI.Listeners.Listeners;
import net.milkbowl.vault.economy.Economy;

public class TTCoreMain extends JavaPlugin {

	private static Economy ECO;
	private static TTCoreMain PLUGIN;

	public void onEnable() {
		PLUGIN = this;
		getServer().getPluginManager().registerEvents(new Listeners(), this);
		setupEco();
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
