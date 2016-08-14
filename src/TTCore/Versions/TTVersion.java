package TTCore.Versions;

import java.util.ArrayList;
import java.util.List;

import TTCore.Entity.TTEntity;
import TTCore.Mech.DataStore;
import TTCore.Oversized.Tickers.OversizedTicker;
import TTCore.Savers.Saver;
import TTCore.Stores.OneStore;

public class TTVersion {
	
	public List<VersionTrack> VERSIONS = new ArrayList<VersionTrack>();
	public static TTVersion VERSION;
	
	public TTVersion(){
		VERSION = this;
		VERSIONS.add(new OneStore.StoreVersion());
		VERSIONS.add(new Saver.SaverVersion());
		VERSIONS.add(new OversizedTicker.OversizedVersion());
		VERSIONS.add(new DataStore.MechVersion());
		VERSIONS.add(new TTEntity.TTEntityVersion());
	}
	
	public int getMajorVersion(){
		OneStore<Integer> major = new OneStore<>(0);
		VERSIONS.stream().forEach(v -> {
			major.setOne(major.getOne() + v.getMajorVersion());
		});
		return major.getOne();
	}
	
	public int getSubMajorVersion(){
		OneStore<Integer> major = new OneStore<>(0);
		VERSIONS.stream().forEach(v -> {
			major.setOne(major.getOne() + v.getSubMajorVersion());
		});
		return major.getOne();
	}
	
	public int getMinorVersion(){
		OneStore<Integer> major = new OneStore<>(0);
		VERSIONS.stream().forEach(v -> {
			major.setOne(major.getOne() + v.getMinorVersion());
		});
		return major.getOne();
	}
	
	public int getHotfixVersion(){
		OneStore<Integer> major = new OneStore<>(0);
		VERSIONS.stream().forEach(v -> {
			major.setOne(major.getOne() + v.getHotfixVersion());
		});
		return major.getOne();
	}
	
	public int[] getTotalVersion(){
		int[] version = {2, getMajorVersion(), getSubMajorVersion(), getMinorVersion(), getHotfixVersion()};
		return version;
	}

}
