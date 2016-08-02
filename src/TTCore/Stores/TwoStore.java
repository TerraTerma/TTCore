package TTCore.Stores;

/**
 * @author mosemister (Evan)
 * @since 02/08/2016 (DD/MM/YYYY) 14:36 (24 hour - UK time)
 * @git Added Stores
 *      ---------------------------------------------------------
 */

public class TwoStore <A extends Object, B extends Object> extends OneStore<A> {
	
	B TWO;
	
	public TwoStore(A one, B two) {
		super(one);
		TWO = two;
	}
	
	public B getTwo(){
		return TWO;
	}
	
	public TwoStore<A, B> setTwo(B object){
		TWO = object;
		return this;
	}
	
	

}
