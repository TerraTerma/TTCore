package TTCore.Entity.Implementation.Living.Human.Player;

import java.io.File;
import java.util.List;
import java.util.Optional;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import TTCore.TTCoreMain;
import TTCore.Entity.TTEntity;
import TTCore.Entity.Living.Human.Player.TTAccount;
import TTCore.Entity.Living.Human.Player.TTPlayer;
import TTCore.Mech.DataHandler;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;

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
 * @since 02/08/2016 (DD/MM/YYYY) 12:56 (24 hour - UK time)
 * @git Player data loads
 *      ---------------------------------------------------------
 * 
 */

public class TTPlayerImp implements TTPlayer {

	TTAccount ACCOUNT;

	public TTPlayerImp(Player player) {
		ACCOUNT = new TTAccountImp(player);
		TTEntity.REGISTERED_ENTITIES.add(this);
	}

	public void leave() {
		saveAll();
		TTEntity.REGISTERED_ENTITIES.remove(this);

	}

	@Override
	public List<DataHandler> getData() {
		return ACCOUNT.getData();
	}

	@Override
	public <M extends DataHandler> Optional<M> getSingleData(Class<M> mech) {
		return ACCOUNT.getSingleData(mech);
	}

	@Override
	public <M extends DataHandler> List<M> getData(Class<M> data) {
		return ACCOUNT.getData(data);
	}

	@Override
	public <M extends DataHandler> Optional<M> getOrCreateSingleData(Class<M> mech, Object... requirements) {
		return ACCOUNT.getOrCreateSingleData(mech, requirements);
	}

	@Override
	public boolean addSingleData(boolean force, DataHandler data) {
		return ACCOUNT.addSingleData(force, data);
	}

	@Override
	public boolean addData(DataHandler... data) {
		return ACCOUNT.addData(data);
	}

	@Override
	public boolean removeData(DataHandler... mech) {
		return ACCOUNT.removeData(mech);
	}

	@Override
	public boolean removeData(Class<? extends DataHandler> mech) {
		return ACCOUNT.removeData(mech);
	}

	@Override
	public void saveAll() {
		ACCOUNT.saveAll();
	}

	@Override
	public File getFile() {
		return ACCOUNT.getFile();
	}

	@Override
	public void sendMessage(String prefix, String message) {
		getPlayer().sendMessage("[" + prefix + "] " + message);
	}

	@Override
	public void sendMessage(Plugin plugin, String message) {
		getPlayer().sendMessage("[" + plugin.getName() + "] " + message);
	}

	@Override
	public Player getPlayer() {
		return (Player) ACCOUNT.getPlayer();
	}

	@Override
	public Player getEntity() {
		return getPlayer();
	}

	@Override
	public double getBalance() {
		Economy eco = TTCoreMain.getPlugin().getVaultEco();
		double bal = eco.getBalance(getEntity());
		return bal;
	}

	@Override
	public boolean hasBalance(double balance) {
		Economy eco = TTCoreMain.getPlugin().getVaultEco();
		return eco.has(getEntity(), balance);
	}

	@Override
	public boolean withdraw(double amount) {
		Economy eco = TTCoreMain.getPlugin().getVaultEco();
		EconomyResponse result = eco.withdrawPlayer(getEntity(), amount);
		return result.transactionSuccess();
	}

	@Override
	public boolean deposit(double amount) {
		Economy eco = TTCoreMain.getPlugin().getVaultEco();
		EconomyResponse result = eco.depositPlayer(getEntity(), amount);
		return result.transactionSuccess();
	}

	@Override
	public void sendMessageAsPlayer(String message) {
		getPlayer().performCommand("/say " + message);

	}

}
