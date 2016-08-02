package TTCore.NotAPI.Listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import TTCore.Entity.Implementation.Living.Human.Player.TTPlayerImp;
import TTCore.Entity.Living.Human.Player.TTPlayer;

/**
 * 
 * @author mosemister (Evan)
 * @since 02/08/2016 (DD/MM/YYYY) 12:56 (24 hour - UK time)
 * @git Player data loads
 *      ---------------------------------------------------------
 * 
 *
 */

public class Listeners implements Listener {

	@EventHandler
	public void playerJoinEvent(PlayerJoinEvent event) {
		new TTPlayerImp(event.getPlayer());
	}

	@EventHandler
	public void playerQuitEvent(PlayerQuitEvent event) {
		playerQuit(event.getPlayer());
	}

	@EventHandler
	public void playerKickedEvent(PlayerKickEvent event) {
		playerQuit(event.getPlayer());
	}

	private void playerQuit(Player player) {
		TTPlayerImp player2 = (TTPlayerImp) TTPlayer.getPlayer(player);
		player2.leave();
	}

}
