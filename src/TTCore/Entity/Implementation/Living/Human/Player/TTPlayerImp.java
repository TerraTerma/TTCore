package TTCore.Entity.Implementation.Living.Human.Player;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import TTCore.TTCoreMain;
import TTCore.Entity.TTEntity;
import TTCore.Entity.Living.Human.Player.TTAccount;
import TTCore.Entity.Living.Human.Player.TTPlayer;
import TTCore.Mech.DataHandler;
import TTCore.Mech.DefaultMechs.MessageFormatData;
import TTCore.Rank.Rank;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import ru.tehkode.permissions.PermissionGroup;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

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
 * @author mosemister (Evan)
 * @since 01/08/2016 (DD/MM/YYYY) 16:22 (24 hour - UK time)
 * @git Mech now work
 *      ---------------------------------------------------------
 * @author mosemister (Evan)
 * @since 09/08/2016 (DD/MM/YYYY) 20:46 (24 hour - UK time)
 * @git BossBar now added
 *      ---------------------------------------------------------
 * 
 */

public class TTPlayerImp implements TTPlayer {

	TTAccount ACCOUNT;
	Map<BossBar, Boolean> BARS = new HashMap<>();

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
	public <M extends DataHandler> M getOrCreateSingleData(Class<M> mech) {
		return ACCOUNT.getOrCreateSingleData(mech);
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
	public void sendMessage(String message) {
		getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', message));
	}

	@Override
	public void sendMessage(Plugin plugin, String message) {
		getPlayer().sendMessage("[" + plugin.getName() + "] " + message);
	}
	
	@Override
	public void sendMessageFromPlayer(TTPlayer player, String unformattedMessage){
		Player bPlayer = player.getPlayer();
		MessageFormatData data = player.getSingleData(MessageFormatData.class).get();
		String format = data.getChatFormat();
		if(StringUtils.containsIgnoreCase(format, "%World%")){
			format = format.replace("%World%", bPlayer.getWorld().getName());
		}
		if(StringUtils.containsIgnoreCase(format, "%Message%")){
			format = format.replace("%Message%", unformattedMessage);
		}
		if(StringUtils.containsIgnoreCase(format, "%Name%")){  
			format = format.replace("%Name%", bPlayer.getName());  
		}  
		if(StringUtils.containsIgnoreCase(format, "%Group%")){  
			format = format.replace("%Group%", player.getPermissionGroup().getName());  
		}  
		if(StringUtils.containsIgnoreCase(format, "%dName%")){  
			format = format.replace("%dName%", bPlayer.getDisplayName());  
		}  
		if(StringUtils.containsIgnoreCase(format, "%Prefix%")){  
			format = format.replace("%Prefix%", player.getPermissionGroup().getPrefix());  
		}  

		format = ChatColor.translateAlternateColorCodes('&', format);
		getPlayer().sendMessage(format);
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

	@Override
	public Map<BossBar, Boolean> getBars() {
		return BARS;
	}

	@Override
	public BossBar createBar(boolean override, BarColor colour, BarStyle style, String message, BarFlag... flags) {
		if(override){
			removeBars();
		}
		BossBar bar = Bukkit.createBossBar(message, colour, style, flags);
		BARS.put(bar, false);
		bar.addPlayer(getPlayer());
		bar.setVisible(true);
		return bar;
	}

	@Override
	public TTPlayer removeBars() {
		getBars().entrySet().stream().forEach(b -> {
			if(b.getValue()){
				b.getKey().removeAll();
				BARS.remove(b.getKey());
			}
		});
		return this;
	}

	@Override
	public Optional<TTPlayer> getOnline() {
		return Optional.of(this);
	}

	@Override
	public boolean lockBar(BossBar bar, boolean lock) {
		return BARS.replace(bar, lock);
	}

	@SuppressWarnings("deprecation")
	@Override
	public PermissionGroup getPermissionGroup() {
		PermissionUser user = PermissionsEx.getUser(getPlayer());
		List<PermissionGroup> group = Arrays.asList(user.getGroups());
		group.remove(user.getParents());
		return group.get(0);
	}

	@Override
	public Rank getRank() {
		return new Rank(getPermissionGroup());
	}

}
