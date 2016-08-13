package TTCore.Rank;

import java.util.ArrayList;
import java.util.List;

import TTCore.Entity.Living.Human.Player.TTPlayer;
import TTCore.Entity.Living.Human.Player.Lists.AccountList;
import ru.tehkode.permissions.PermissionGroup;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class Rank {
	
	public static final Rank OWNER = new Rank(PermissionsEx.getPermissionManager().getGroup("owner"));
	public static final Rank HEAD_DEVELOPER = new Rank(PermissionsEx.getPermissionManager().getGroup("headdev"));
	public static final Rank DEVELOPER = new Rank(PermissionsEx.getPermissionManager().getGroup("developer"));
	public static final Rank HEAD_ADMIN = new Rank(PermissionsEx.getPermissionManager().getGroup("headadmin"));
	public static final Rank ADMIN = new Rank(PermissionsEx.getPermissionManager().getGroup("Admins"));
	public static final Rank LEAD_MOD = new Rank(PermissionsEx.getPermissionManager().getGroup("Lead-Mod"));
	public static final Rank MOD = new Rank(PermissionsEx.getPermissionManager().getGroup("moderator"));
	public static final Rank DEFAULT = new Rank(PermissionsEx.getPermissionManager().getGroup("default"));
	
	PermissionGroup GROUP;
	
	public Rank(PermissionGroup group){
		GROUP = group;
	}

	public String getName(){
		return GROUP.getName();
	}
	
	public PermissionGroup getRelatedPermissionGroup(){
		return GROUP;
	}
	
	public String getPrefix(){
		return GROUP.getPrefix();
	}
	
	public AccountList<TTPlayer> getActivtePlayers(){
		AccountList<TTPlayer> list = new AccountList<>();
		GROUP.getUsers().stream().forEach(p -> {
			list.add(TTPlayer.getPlayer(p.getPlayer()));
		});
		return list;
	}
	
	public boolean isStaff(){
		return (!equals(DEFAULT));
	}
	
	@SuppressWarnings("deprecation")
	public boolean isParent(Rank rank){
		return getRelatedPermissionGroup().getParentGroups().contains(rank.getRelatedPermissionGroup());
	}
	
	public boolean isMod(){
		return isParent(MOD);
	}
	
	@Override
	public boolean equals(Object obj){
		if(obj instanceof Rank){
			Rank rank = (Rank)obj;
			return rank.getName().equals(getName());
		}
		return false;
	}
	
	public static List<Rank> getRanks(){
		List<Rank> rank = new ArrayList<>();
		PermissionsEx.getPermissionManager().getGroupList().stream().forEach(g -> {
			rank.add(new Rank(g));
		});
		return rank;
	}

}
