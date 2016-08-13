package TTCore.Mech.DefaultMechs;

import java.util.HashSet;
import java.util.Optional;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.FallingBlock;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import TTCore.TTCoreMain;
import TTCore.Entity.TTEntity;
import TTCore.Entity.Living.Human.Player.TTPlayer;
import TTCore.Entity.NonLiving.FallingBlock.TTFallingBlock;
import TTCore.Mech.DataHandlers.PlayerData;

/**
 * this is a simple Mech that will connect and keep a TTFallingBlock at the
 * players crosshairs. The movement of the TTFallingBlock should be smooth.
 *
 */
public class FlyingBlock implements PlayerData {

	TTFallingBlock BLOCK;
	int OFFSET = 4;
	BukkitTask BUKKIT_REPEATE;

	public Optional<TTFallingBlock> getBlock() {
		return Optional.ofNullable(BLOCK);
	}

	public int getOffset() {
		return OFFSET;
	}

	public TTFallingBlock fire(TTPlayer player, double force) {
		if (BLOCK != null) {
			Location is = BLOCK.getEntity().getLocation();
			Vector vector = is.toVector().subtract(player.getPlayer().getLocation().toVector()).normalize()
					.multiply(force);
			player.removeData(this);
			BUKKIT_REPEATE.cancel();
			BLOCK.getEntity().setVelocity(vector);
		}
		return BLOCK;
	}

	public boolean isInCorrectPos(TTPlayer player) {
		Block location = player.getPlayer().getTargetBlock((HashSet<Material>) null, OFFSET);
		Location is = BLOCK.getEntity().getLocation();
		if (is.getBlock().equals(location)) {
			return true;
		}
		return false;
	}

	public void attemptToCorrectLocation(TTPlayer player) {
		Location location = player.getPlayer().getTargetBlock((HashSet<Material>) null, OFFSET).getLocation();
		if (BLOCK != null) {
			Location is = BLOCK.getEntity().getLocation();
			BLOCK.getEntity().setVelocity(location.subtract(is).toVector());
		}
	}

	public boolean setBlock(TTFallingBlock block) {
		Optional<TTEntity> opEntity = TTEntity.getRelatedMech(this);
		if (opEntity.isPresent()) {
			if (opEntity.get() instanceof TTPlayer) {
				final TTPlayer player = (TTPlayer) opEntity.get();
				if (BLOCK != null) {
					BLOCK.getEntity().remove();
					BUKKIT_REPEATE.cancel();
				}
				BLOCK = block;
				block.teleport(player.getPlayer().getTargetBlock((HashSet<Material>) null, OFFSET).getLocation());
				FallingBlock entity = BLOCK.getEntity();
				entity.setGravity(false);
				entity.setDropItem(false);
				FlyingBlock data = this;
				BUKKIT_REPEATE = Bukkit.getScheduler().runTaskTimer(TTCoreMain.getPlugin(),
						new Runnable() {

							@Override
							public void run() {
								if (data.isInCorrectPos(player)) {
									data.getBlock().get().getEntity().setVelocity(new Vector(0, 0, 0));
								}else{
									data.attemptToCorrectLocation(player);
								}
							}

						}, 20L, 1);
				return true;
			}
		}
		return false;
	}

	public boolean setOffset(int offset) {
		Optional<TTEntity> opEntity = TTEntity.getRelatedMech(this);
		if (opEntity.isPresent()) {
			if (opEntity.get() instanceof TTPlayer) {
				TTPlayer player = (TTPlayer) opEntity.get();
				if (BLOCK != null) {
					BLOCK.teleport(player.getPlayer().getTargetBlock((HashSet<Material>) null, OFFSET).getLocation());
					BLOCK.getEntity().setGravity(false);
				}
				return true;
			}
		}
		return false;
	}

}
