package TTCore.Entity.Living.Human.Player.Lists;

import java.util.ArrayList;

import TTCore.Entity.Living.Human.Player.TTAccount;
import TTCore.Entity.Living.Human.Player.TTPlayer;

public class AccountList<P extends TTAccount> extends ArrayList<P>{

	private static final long serialVersionUID = 1L;
	
	public AccountList<TTPlayer> getOnline(){
		AccountList<TTPlayer> players = new AccountList<>();
		stream().forEach(a -> {
			if(a.getOnline().isPresent()){
				players.add(a.getOnline().get());
			}
		});
		return players;
	}

}
