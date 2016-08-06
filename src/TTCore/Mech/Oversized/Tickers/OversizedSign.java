package TTCore.Mech.Oversized.Tickers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;

import TTCore.Stores.ThreeStore;

public class OversizedSign extends OversizedTicker{

	Block BLOCK;
	List<ThreeStore<Integer, Boolean, ChatColor>> APPLY = new ArrayList<>();
	
	public OversizedSign(Block block, List<ThreeStore<Integer, Boolean, ChatColor>> three) {
		super(16);
		BLOCK = block;
		APPLY = three;
	}
	
	public Sign getSign(){
		return (Sign)BLOCK.getState();
	}

	@Override
	public void tick() {
		Sign sign = getSign();
		for(int A = 0; A < 3; A++){
			final int B = A;
			Optional<ThreeStore<Integer, Boolean, ChatColor>> opStore = APPLY.stream().filter(s -> s.getOne() == B).findFirst();
			String line = sign.getLine(A);
			String next = null;
			if(opStore.isPresent()){
				ThreeStore<Integer, Boolean, ChatColor> store = opStore.get();
				if(store.getTwo()){
					if(store.getThree() == null){
						next = getNext(line);
					}else{
						next = getNext(line, store.getThree());
					}
				}
			}
			sign.setLine(A, next);
		}
		sign.update();
		
	}

}
