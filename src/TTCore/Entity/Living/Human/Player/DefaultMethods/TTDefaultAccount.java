package TTCore.Entity.Living.Human.Player.DefaultMethods;

import java.time.LocalDate;
import java.util.UUID;

import org.bukkit.Location;

public interface TTDefaultAccount {
	
	public String getName();
	public UUID getUUID();
	public LocalDate getFirstJoinDate();
	public LocalDate getLastPlayedDate();
	public Location getBedSpawn();
	
	public boolean isBanned();
	public boolean isOnline();
	
	public boolean hasPlayedBefore();

}
