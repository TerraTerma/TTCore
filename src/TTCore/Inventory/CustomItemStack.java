package TTCore.Inventory;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import TTCore.Entity.Living.Human.Player.TTPlayer;

public abstract class CustomItemStack {
	
	ItemStack ITEM;
	
	public abstract boolean onClick(CustomInventory inv, TTPlayer player);
	
	public CustomItemStack(ItemStack item){
		ITEM = item;
	}
	
	public CustomItemStack(Material material, String name, List<String> lore){
		ItemStack item = new ItemStack(material, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		meta.setLore(lore);
		item.setItemMeta(meta);
		ITEM = item;
	}
	
	public ItemStack getItemStack(){
		return ITEM;
	}

}
