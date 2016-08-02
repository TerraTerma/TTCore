package TTCore.Stores;

public class OneStore<A extends Object> {
	
	A ONE;
	
	public OneStore(A one){
		ONE = one;
	}
	
	public A getOne(){
		return ONE;
	}
	
	public OneStore<A> setOne(A object){
		ONE = object;
		return this;
	}
}
