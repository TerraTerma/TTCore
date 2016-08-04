package TTCore.Savers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * 
 * @author mosemister (Evan)
 * @since 04/08/2016 (DD/MM/YYYY) 13:59 (24 hour - UK time)
 * @git Section fix
 *
 */
public class SpecialSaver {

	private static final String ITEMSTACK_MATERIAL = "Material";
	private static final String ITEMSTACK_DATA = "Data";
	private static final String ITEMSTACK_AMOUNT = "Amount";
	private static final String ITEMSTACK_DAMAGE = "Damage";
	private static final String ITEMSTACK_NAME = "Name";
	private static final String ITEMSTACK_LORE = "Lore";
	private static final String ITEMSTACK_FLAG = "Flags";
	private static final String ITEMSTACK_ENCHANTMENT = "Enchantments";

	@SuppressWarnings("deprecation")
	public Optional<ItemStack> getItemStack(Saver saver, String... path) {
		String material = saver.get(String.class, path, ITEMSTACK_MATERIAL);
		Integer data = saver.get(Integer.class, path, ITEMSTACK_DATA);
		Integer amount = saver.get(Integer.class, path, ITEMSTACK_AMOUNT);
		Integer damage = saver.get(Integer.class, path, ITEMSTACK_DAMAGE);
		String name = saver.get(String.class, path, ITEMSTACK_NAME);
		List<String> lore = saver.getList(String.class, path, ITEMSTACK_LORE);
		List<String> enchantmentS = saver.getList(String.class, path, ITEMSTACK_ENCHANTMENT);
		List<String> flagS = saver.getList(String.class, path, ITEMSTACK_FLAG);
		
		if ((material != null) && (amount != null)) {
			ItemStack stack = new ItemStack(Material.valueOf(material), amount);
			ItemMeta meta = stack.getItemMeta();

			if (damage != null) {
				stack.setDurability(damage.shortValue());
			}
			if (data != null) {
				stack.getData().setData(data.byteValue());
			}
			if (name != null) {
				meta.setDisplayName(name);
			}
			if (lore != null) {
				meta.setLore(lore);
			}
			if (flagS != null) {
				flagS.stream().forEach(f -> {
					ItemFlag flag = ItemFlag.valueOf(f);
					meta.addItemFlags(flag);
				});
			}
			if (enchantmentS != null) {
				enchantmentS.stream().forEach(e -> {
					String[] enchantment2 = e.split(",");
					Enchantment ench = Enchantment.getByName(enchantment2[0]);
					int level = Integer.parseInt(enchantment2[1]);
					meta.addEnchant(ench, level, true);
				});
			}
			stack.setItemMeta(meta);
			return Optional.of(stack);
		}
		return Optional.empty();
	}

	@SuppressWarnings("deprecation")
	public Saver setItemStack(Saver saver, ItemStack item, String... path) {
		ItemMeta meta = item.getItemMeta();
		List<String> enchantment = new ArrayList<>();
		meta.getEnchants().entrySet().stream().forEach(e -> {
			enchantment.add(e.getKey().getName() + "," + e.getValue());
		});

		List<String> flags = new ArrayList<>();
		meta.getItemFlags().stream().forEach(f -> {
			flags.add(f.name());
		});

		saver.set(item.getType().name(), path, ITEMSTACK_MATERIAL);
		saver.set(item.getData().getData(), path, ITEMSTACK_DATA);
		saver.set(item.getAmount(), path, ITEMSTACK_AMOUNT);
		saver.set(item.getDurability(), path, ITEMSTACK_DAMAGE);
		saver.set(meta.getDisplayName(), path, ITEMSTACK_NAME);
		saver.set(meta.getLore(), path, ITEMSTACK_LORE);
		saver.set(enchantment, path, ITEMSTACK_ENCHANTMENT);
		saver.set(flags, path, ITEMSTACK_FLAG);
		return saver;

	}

}
