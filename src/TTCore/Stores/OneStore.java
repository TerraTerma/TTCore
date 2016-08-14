package TTCore.Stores;

import TTCore.Versions.VersionTrack;

public class OneStore<A extends Object> {

	A ONE;

	public OneStore(A one) {
		ONE = one;
	}

	public A getOne() {
		return ONE;
	}

	public OneStore<A> setOne(A object) {
		ONE = object;
		return this;
	}

	public static class StoreVersion implements VersionTrack {

		@Override
		public int getMajorVersion() {
			return 1;
		}

		@Override
		public int getSubMajorVersion() {
			return 0;
		}

		@Override
		public int getMinorVersion() {
			return 0;
		}

		@Override
		public int getHotfixVersion() {
			return 3;
		}
	}
}
