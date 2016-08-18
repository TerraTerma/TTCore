package TTCore.Mech.DefaultMechs;

import java.util.Optional;

import org.bukkit.inventory.Inventory;

import TTCore.Entity.Living.Human.Player.TTPlayer;
import TTCore.Inventory.CustomInventory;
import TTCore.Mech.DataHandlers.PlayerData;

public class OpenInventoryData implements PlayerData {
	
	CustomInventory INV;
	
	public CustomInventory getInventory(){
		return INV;
	}
	
	public OpenInventoryData setInventory(CustomInventory inv, TTPlayer player){
		Optional<Inventory> inv2 = inv.getInventory();
		INV = inv;
		if(inv2.isPresent()){
			player.getPlayer().openInventory(inv2.get());
		}
		return this;
	}
	
	public void closeInventory(TTPlayer player){
		player.removeData(OpenInventoryData.class);
		player.closeInventory();
	}

}
