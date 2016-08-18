package TTCore.Inventory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import TTCore.Entity.Living.Human.Player.TTPlayer;
import TTCore.Mech.DefaultMechs.OpenInventoryData;

public class CustomInventory {
	
	public static final CustomItemStack BACK_BUTTON = new CustomItemStack(Material.ARROW, "Back", Arrays.asList("Go back to previous page")){

		@Override
		public boolean onClick(CustomInventory inv, TTPlayer player) {
			return false;
		}
		
	};
	
	public static final CustomItemStack PREVIOUS_BUTTON = new CustomItemStack(Material.BOW, "Next", Arrays.asList("Go to the next page")){

		@Override
		public boolean onClick(CustomInventory inv, TTPlayer player) {
			return false;
		}
		
	};
	
	List<CustomItemStack> ITEMS = new ArrayList<>();
	Inventory INVENTORY;
	String NAME;
	
	public CustomInventory(){
		NAME = "Chest";
	}
	
	public CustomInventory(String name){
		NAME = name;
	}
	
	public Optional<Inventory> getInventory(){
		return Optional.ofNullable(INVENTORY);
	}
	
	public List<CustomItemStack> getItems(){
		return ITEMS;
	}
	
	public Optional<CustomItemStack> getItem(ItemStack item){
		return ITEMS.stream().filter(i -> i.getItemStack().equals(item)).findFirst();
	}
	
	public CustomInventory apply(TTPlayer player){
		if(INVENTORY == null){
			int invSize = ((int)(ITEMS.size() / 9) + 2)*9;
			Inventory inv = Bukkit.createInventory(null, invSize, NAME);
			ITEMS.stream().forEach(i -> inv.addItem(i.getItemStack()));
			inv.setItem(inv.getSize() - 9, BACK_BUTTON.getItemStack());
			inv.setItem(inv.getSize() - 1, PREVIOUS_BUTTON.getItemStack());
			INVENTORY = inv;
		}
		player.openInventory(INVENTORY);
		OpenInventoryData data = player.getOrCreateSingleData(OpenInventoryData.class, true);
		if(player.addSingleData(true, data)){
			data.setInventory(this, player);
		}else{
			System.out.println("inject failed");
		}
		
		return this;
	}

}
