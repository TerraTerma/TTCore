package TTCore.NotAPI.Listeners;

import java.util.Optional;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import TTCore.Entity.Implementation.Living.Human.Player.TTPlayerImp;
import TTCore.Entity.Living.Human.Player.TTPlayer;
import TTCore.Inventory.CustomInventory;
import TTCore.Inventory.CustomItemStack;
import TTCore.Mech.DefaultMechs.OpenInventoryData;

/**
 * 
 * @author mosemister (Evan)
 * @since 02/08/2016 (DD/MM/YYYY) 12:56 (24 hour - UK time)
 * @git Player data loads
 *      ---------------------------------------------------------
 * 
 */

public class Listeners implements Listener {

	@EventHandler
	public void playerClickEvent(InventoryClickEvent event){
		System.out.println("Inventory click");
		TTPlayer player = TTPlayer.getPlayer(event.getWhoClicked());
		Optional<OpenInventoryData> opData = player.getSingleData(OpenInventoryData.class);
		if(opData.isPresent()){
			System.out.println("has OpenInventory data");
			OpenInventoryData data = opData.get();
			CustomInventory inv = data.getInventory();
			Optional<CustomItemStack> opItem =  inv.getItem(event.getCurrentItem());
			if(opItem.isPresent()){
				System.out.println("is item");
				CustomItemStack item = opItem.get();
				event.setCancelled(item.onClick(inv, player));
			}
			
		}
	}
	
	@EventHandler
	public void playerCloseInventory(InventoryCloseEvent event){
		TTPlayer player = TTPlayer.getPlayer(event.getPlayer());
		Optional<OpenInventoryData> opData = player.getSingleData(OpenInventoryData.class);
		if(opData.isPresent()){
			opData.get().closeInventory(player);
		}
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void playerJoinEvent(PlayerJoinEvent event) {
		new TTPlayerImp(event.getPlayer());
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void playerQuitEvent(PlayerQuitEvent event) {
		playerQuit(event.getPlayer());
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void playerKickedEvent(PlayerKickEvent event) {
		playerQuit(event.getPlayer());
	}

	private void playerQuit(Player player) {
		TTPlayerImp player2 = (TTPlayerImp) TTPlayer.getPlayer(player);
		player2.leave();
	}

}
