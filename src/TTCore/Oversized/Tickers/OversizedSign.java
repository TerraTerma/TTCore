package TTCore.Oversized.Tickers;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;

public class OversizedSign extends OversizedTicker{

	Block BLOCK;
	List<OversizedSignLine> APPLY = new ArrayList<>();
	
	public OversizedSign(Block block, List<OversizedSignLine> lines) {
		super(16);
		BLOCK = block;
		APPLY = lines;
	}
	
	public Sign getSign(){
		return (Sign)BLOCK.getState();
	}

	@Override
	public void tick() {
		Sign sign = getSign();
		for(OversizedSignLine store : APPLY){
			String display = getNext(store.getLine(), store.getLast());
			store.setLast(store.getLast() + 1);
			sign.setLine(store.getLineNumber(), display);
		}
		sign.update();
		
	}
	
	public static class OversizedSignLine{
		String ORIGINAL;
		int LINE_NUMBER;
		int LAST;
		ChatColor COLOR;
		
		public OversizedSignLine(String message, int line){
			ORIGINAL = message;
			LINE_NUMBER = line;
			LAST = 0;
			COLOR = ChatColor.BLACK;
		}
		
		public OversizedSignLine(String message, int line, ChatColor color){
			ORIGINAL = message;
			LINE_NUMBER = line;
			LAST = 0;
			COLOR = color;
		}
		
		public String getLine(){
			return ORIGINAL;
		}
		
		public int getLineNumber(){
			return LINE_NUMBER;
		}
		
		public int getLast(){
			return LAST;
		}
		
		public void setLast(int last){
			LAST = last;
		}
		
		public ChatColor getColour(){
			return COLOR;
		}
	}

}
