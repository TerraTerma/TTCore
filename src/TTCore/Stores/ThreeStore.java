package TTCore.Stores;

public class ThreeStore <A extends Object, B extends Object, C extends Object> extends TwoStore<A, B>{

	C THREE;
	
	public ThreeStore(A one, B two, C three) {
		super(one, two);
		THREE = three;
	}
	
	public C getThree(){
		return THREE;
	}
	
	public ThreeStore<A, B, C> setThree(C three){
		THREE = three;
		return this;
	}

}
