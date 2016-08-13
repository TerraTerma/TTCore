package TTCore.Entity.NonLiving.FallingBlock;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.FallingBlock;

import TTCore.Entity.TTEntity;

public class TTFallingBlock implements TTEntity {

	FallingBlock ENTITY;
	
	@SuppressWarnings("deprecation")
	public TTFallingBlock(Location loc, Material material, byte data) {
		ENTITY = loc.getWorld().spawnFallingBlock(loc, material, data);
	}
	
	@SuppressWarnings("deprecation")
	public void teleport(Location loc){
		FallingBlock entity = ENTITY;
		ENTITY.remove();
		ENTITY = loc.getWorld().spawnFallingBlock(loc, entity.getMaterial(), entity.getBlockData());
	}

	@Override
	public FallingBlock getEntity() {
		return ENTITY;
	}

}
