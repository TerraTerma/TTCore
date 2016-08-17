package TTCore.Entity.Implementation.Living.Human.Player;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Instrument;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Note;
import org.bukkit.Sound;
import org.bukkit.WeatherType;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.InventoryView.Property;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.Plugin;
import org.bukkit.scoreboard.Scoreboard;

import TTCore.TTCoreMain;
import TTCore.Entity.TTEntity;
import TTCore.Entity.Living.Human.Player.TTAccount;
import TTCore.Entity.Living.Human.Player.TTPlayer;
import TTCore.Entity.Living.Human.Player.MessageFormats.MessageFormat;
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
	public void sendMessageFromPlayer(TTPlayer player, String message, MessageFormat format){
		Player bPlayer = player.getPlayer();
		MessageFormatData data = player.getSingleData(MessageFormatData.class).get();
		String format2 = null;
    switch(format){
      case STAFF:
      	format2 = data.getStaffChatFormat();
      	break;
      case DEFAULT:
      	format2 = data.getChatFormat();
      	break;
      default:
        new IOException("You need to update TTCore.").printStackTrace();;
    }
    
		if(StringUtils.containsIgnoreCase(format2, "%World%")){
			format2 = format2.replace("%World%", bPlayer.getWorld().getName());
		}
		if(StringUtils.containsIgnoreCase(format2, "%Message%")){
			format2 = format2.replace("%Message%", message);
		}
		if(StringUtils.containsIgnoreCase(format2, "%Name%")){  
			format2 = format2.replace("%Name%", bPlayer.getName());  
		}  
		if(StringUtils.containsIgnoreCase(format2, "%Group%")){  
			format2 = format2.replace("%Group%", player.getPermissionGroup().getName());  
		}  
		if(StringUtils.containsIgnoreCase(format2, "%dName%")){  
			format2 = format2.replace("%dName%", bPlayer.getDisplayName());  
		}  
		if(StringUtils.containsIgnoreCase(format2, "%Prefix%")){  
			format2 = format2.replace("%Prefix%", player.getPermissionGroup().getPrefix());  
		}  
    format2 = ChatColor.translateAlternateColorCodes('&', format2);
    getPlayer().sendMessage(format2);
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

	@Override
	public String getName() {
		return getPlayer().getName();
	}

	@Override
	public UUID getUUID() {
		return getPlayer().getUniqueId();
	}

	@Override
	public LocalDate getFirstJoinDate() {
		return null;
	}

	@Override
	public LocalDate getLastPlayedDate() {
		return null;
	}

	@Override
	public Location getBedSpawn() {
		return getPlayer().getBedSpawnLocation();
	}

	@Override
	public boolean isBanned() {
		return getPlayer().isBanned();
	}

	@Override
	public boolean isOnline() {
		return getPlayer().isOnline();
	}

	@Override
	public boolean hasPlayedBefore() {
		return getPlayer().hasPlayedBefore();
	}

	@Override
	public Location getCompassTarget() {
		return getPlayer().getCompassTarget();
	}

	@Override
	public long getPlayerTime() {
		return getPlayer().getPlayerTime();
	}

	@Override
	public long getPlayerTimeOffset() {
		return getPlayer().getPlayerTimeOffset();
	}

	@Override
	public WeatherType getPlayerWeather() {
		return getPlayer().getPlayerWeather();
	}

	@Override
	public float getEnergy() {
		return getPlayer().getExhaustion();
	}

	@Override
	public float getSaturation() {
		return getPlayer().getSaturation();
	}

	@Override
	public int getFoodLevel() {
		return getPlayer().getFoodLevel();
	}

	@Override
	public float getWalkSpeed() {
		return getPlayer().getWalkSpeed();
	}

	@Override
	public float getFlySpeed() {
		return getPlayer().getFlySpeed();
	}

	@Override
	public Scoreboard getScoreboard() {
		return getPlayer().getScoreboard();
	}

	@Override
	public PlayerInventory getInventory() {
		return getPlayer().getInventory();
	}

	@Override
	public Inventory getEnderChest() {
		return getPlayer().getEnderChest();
	}

	@Override
	public InventoryView getOpenInventory() {
		return getPlayer().getOpenInventory();
	}

	@Override
	public GameMode getGamemode() {
		return getPlayer().getGameMode();
	}

	@Override
	public TTPlayer kickPlayer(String message) {
		getPlayer().kickPlayer(message);
		return this;
	}

	@Override
	public TTPlayer playNote(Location loc, Instrument ins, Note note) {
		getPlayer().playNote(loc, ins, note);
		return this;
	}

	@Override
	public TTPlayer playSound(Location loc, Sound sound, float vol, float pitch) {
		getPlayer().playSound(loc, sound, vol, pitch);
		return this;
	}

	@Override
	public <T> TTPlayer playEffect(Location loc, Effect effect, T data) {
		getPlayer().playEffect(loc, effect, data);
		return this;
	}

	@SuppressWarnings("deprecation")
	@Override
	public TTPlayer sendBlockChange(Location loc, Material material, byte data) {
		getPlayer().sendBlockChange(loc, material, data);
		return this;
	}

	@Override
	public TTPlayer sendSignChange(Location loc, String... lines) {
		getPlayer().sendSignChange(loc, lines);
		return this;
	}

	@Override
	public TTPlayer restartPlayerTime() {
		getPlayer().resetPlayerTime();
		return this;
	}

	@Override
	public TTPlayer restartPlayerWeather() {
		getPlayer().resetPlayerTime();
		return this;
	}

	@Override
	public TTPlayer hidePlayer(Player player) {
		getPlayer().hidePlayer(player);
		return this;
	}

	@Override
	public TTPlayer hidePlayer(TTPlayer player) {
		player.hidePlayer(player);
		return this;
	}

	@Override
	public TTPlayer showPlayer(Player player) {
		getPlayer().showPlayer(player);
		return this;
	}

	@Override
	public TTPlayer showPlayer(TTPlayer player) {
		player.showPlayer(player);
		return this;
	}

	@Override
	public InventoryView openInventory(Inventory inv) {
		return getPlayer().openInventory(inv);
	}

	@Override
	public InventoryView openWorkbench(boolean force) {
		return getPlayer().openWorkbench(null, force);
	}

	@Override
	public InventoryView openEnchantment(boolean force) {
		return getPlayer().openEnchanting(null, force);
}

	@Override
	public TTPlayer closeInventory() {
		getPlayer().closeInventory();
		return this;
	}

	@Override
	public boolean isSneaking() {
		return getPlayer().isSneaking();
	}

	@Override
	public boolean isSprinting() {
		return getPlayer().isSprinting();
	}

	@Override
	public boolean isSleepingIgnored() {
		return getPlayer().isSleepingIgnored();
	}

	@Override
	public boolean isPlayerTimeRelative() {
		return getPlayer().isPlayerTimeRelative();
	}

	@Override
	public boolean isAllowedToFly() {
		return getPlayer().getAllowFlight();
	}

	@Override
	public boolean isFlying() {
		return getPlayer().isFlying();
	}

	@Override
	public boolean canSeePlayer(Player player) {
		return getPlayer().canSee(player);
	}

	@Override
	public boolean canSeePlayer(TTPlayer player) {
		return player.canSeePlayer(player);
	}

	@Override
	public boolean isSleeping() {
		return getPlayer().isSleeping();
	}

	@Override
	public boolean isBlocking() {
		return getPlayer().isBlocking();
	}

	@Override
	public TTPlayer setCompassTarget(Location loc) {
		getPlayer().setCompassTarget(loc);
		return null;
	}

	@Override
	public TTPlayer setSneaking(boolean check) {
		getPlayer().setSneaking(check);
		return this;
	}

	@Override
	public TTPlayer setSprinting(boolean check) {
		getPlayer().setSprinting(check);
		return this;
	}

	@Override
	public TTPlayer setSleepingIgnored(boolean check) {
		getPlayer().setSleepingIgnored(check);
		return this;
	}

	@Override
	public TTPlayer setPlayerTime(long time, boolean rel) {
		getPlayer().setPlayerTime(time, rel);
		return this;
	}

	@Override
	public TTPlayer setPlayerWeather(WeatherType type) {
		getPlayer().setPlayerWeather(type);
		return this;
	}

	@Override
	public TTPlayer setEnergy(float energy) {
		getPlayer().setExhaustion(energy);
		return this;
	}

	@Override
	public TTPlayer setSaturation(float sat) {
		getPlayer().setSaturation(sat);
		return this;
	}

	@Override
	public TTPlayer setFoodLevel(int food) {
		getPlayer().setFoodLevel(food);
		return this;
	}

	@Override
	public TTPlayer setAllowedToFly(boolean check) {
		getPlayer().setAllowFlight(check);
		return this;
	}

	@Override
	public TTPlayer setFlying(boolean check) {
		getPlayer().setFlying(check);
		return this;
	}

	@Override
	public TTPlayer setFlySpeed(float speed) {
		getPlayer().setFlySpeed(speed);
		return this;
	}

	@Override
	public TTPlayer setWalkSpeed(float speed) {
		getPlayer().setWalkSpeed(speed);
		return this;
	}

	@Override
	public TTPlayer setScoreboard(Scoreboard board) {
		getPlayer().setScoreboard(board);
		return this;
	}

	@Override
	public TTPlayer setWindowProperty(Property prop, int value) {
		getPlayer().setWindowProperty(prop, value);
		return this;
	}

	@Override
	public TTPlayer setGamemode(GameMode mode) {
		getPlayer().setGameMode(mode);
		return this;
	}

}
