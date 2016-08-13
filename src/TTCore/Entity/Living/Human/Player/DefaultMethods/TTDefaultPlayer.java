package TTCore.Entity.Living.Human.Player.DefaultMethods;

import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Instrument;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Note;
import org.bukkit.Sound;
import org.bukkit.WeatherType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.scoreboard.Scoreboard;

import TTCore.Entity.Living.Human.Player.TTPlayer;

public interface TTDefaultPlayer {
	
	public Location getCompassTarget();
	public long getPlayerTime();
	public long getPlayerTimeOffset();
	public WeatherType getPlayerWeather();
	public float getEnergy();
	public float getSaturation();
	public int getFoodLevel();
	public float getWalkSpeed();
	public float getFlySpeed();
	public Scoreboard getScoreboard();
	public PlayerInventory getInventory();
	public Inventory getEnderChest();
	public InventoryView getOpenInventory();
	public GameMode getGamemode();
	
	public TTPlayer kickPlayer(String message);
	public TTPlayer playNote(Location loc, Instrument ins, Note note);
	public TTPlayer playSound(Location loc, Sound sound, float vol, float pitch);
	public <T> TTPlayer playEffect(Location loc, Effect effect, T data);
	public TTPlayer sendBlockChange(Location loc, Material material, byte data);
	public TTPlayer sendSignChange(Location loc, String... lines);
	public TTPlayer restartPlayerTime();
	public TTPlayer restartPlayerWeather();
	public TTPlayer hidePlayer(Player player);
	public TTPlayer hidePlayer(TTPlayer player);
	public TTPlayer showPlayer(Player player);
	public TTPlayer showPlayer(TTPlayer player);
	public InventoryView openInventory(Inventory inv);
	public InventoryView openWorkbench(boolean force);
	public InventoryView openEnchantment(boolean force);
	public TTPlayer closeInventory();
	
	public boolean isSneaking();
	public boolean isSprinting();
	public boolean isSleepingIgnored();
	public boolean isPlayerTimeRelative();
	public boolean isAllowedToFly();
	public boolean isFlying();
	public boolean canSeePlayer(Player player);
	public boolean canSeePlayer(TTPlayer player);
	public boolean isSleeping();
	public boolean isBlocking();
	
	public TTPlayer setCompassTarget(Location loc);
	public TTPlayer setSneaking(boolean check);
	public TTPlayer setSprinting(boolean check);
	public TTPlayer setSleepingIgnored(boolean check);
	public TTPlayer setPlayerTime(long time, boolean rel);
	public TTPlayer setPlayerWeather(WeatherType type);
	public TTPlayer setEnergy(float energy);
	public TTPlayer setSaturation(float sat);
	public TTPlayer setFoodLevel(int food);
	public TTPlayer setAllowedToFly(boolean check);
	public TTPlayer setFlying(boolean check);
	public TTPlayer setFlySpeed(float speed);
	public TTPlayer setWalkSpeed(float speed);
	public TTPlayer setScoreboard(Scoreboard board);
	public TTPlayer setWindowProperty(InventoryView.Property prop, int value);
	public TTPlayer setGamemode(GameMode mode);

}
