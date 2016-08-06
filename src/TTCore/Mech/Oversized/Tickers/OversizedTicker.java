package TTCore.Mech.Oversized.Tickers;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import TTCore.TTCoreMain;

public abstract class OversizedTicker {
	
	int LAST;
	int LIMIT;
	
	static List<OversizedTicker> TICKERS = new ArrayList<>();
	static long TICK_TIME = 5;
	
	public abstract void tick();
	
	public OversizedTicker(int limit){
		LIMIT = (limit - 1);
		TICKERS.add(this);
	}
	
	public String getNext(String before){
		if(before.length() == LAST){
			LAST = 0;
		}
		String after = "";
		int A = 0;
		int limit = LIMIT;
		if(limit > before.length()){
			limit = before.length();
		}
		for(; A < limit; A++){
			after = (after + before.charAt(A+LAST));
		}
		LAST++;
		return after;
	}
	
	public String getNext(String before, ChatColor color){
		if(before.length() == LAST){
			LAST = 0;
		}
		String after = color.toString();
		int A = 0;
		int limit = LIMIT;
		if(limit > before.length()){
			limit = before.length();
		}
		for(; A < limit; A++){
			after = (after + before.charAt(A+LAST));
		}
		LAST++;
		return after;
	}
	
	public static void init(){
		Bukkit.getScheduler().scheduleSyncRepeatingTask(TTCoreMain.getPlugin(), new Runnable(){

			@Override
			public void run() {
				TICKERS.stream().forEach(t -> t.tick());
			}
			
		}, 0, TICK_TIME);
	}

}
