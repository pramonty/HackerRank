package monty.practice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CoinChange {
	
	
	
	Map<String,Long> lookUp;
	
	public static void main(String[] args) {
		int n=10;
		List<Integer> c=Arrays.asList(2,5,3,6);	
		CoinChange cc=new CoinChange();
		cc.initializeMemoizedArray(n+1);
		System.out.println(cc.getWays(n, c, 0, c.size()));
		//System.out.println(cc.bottomUpApproach(n, c));
		
		
		
		//System.out.println(cc.solutionMap.toString());
		
		
		
	}
	
	
	private void initializeMemoizedArray(int arraySize) {
		
		lookUp=new HashMap<String,Long>();
	}
	
	
	/*private long bottomUpApproach(int n, List<Integer> c) {
		initializeMemoizedArray(n+1);
		for(int i=1;i<=n;i++) {
			long intChange=getWays(i,c,0,c.size());
			System.out.println(String.format("Change for coin %d is %d", i,intChange));
			memoizedArray[i]=intChange;
		}
		return memoizedArray[n];
	}*/
	
	
	
	private long getWays(int n, List<Integer> c,int start,int end) {
		long totalChange=0;
		
		if(n==0){
            return 1;
        }
        if(n<0){
            return 0;
        }
        
        int mStart=start;
        int mEnd=end;
        String lookUpKey=String.valueOf(mStart)+"|"+String.valueOf(n);
    	if(!lookUp.containsKey(lookUpKey)) {
    		for(int i=mStart;i<end;i++){
            	long tmpChange=0;            	
            	tmpChange=getWays(n-c.get(i),c,mStart,mEnd);
            	totalChange+=tmpChange;
                mStart++;         
            }
    		lookUp.put(lookUpKey, totalChange);
    	}
        
        
        
        //memoizedArray[n]=totalChange;
        
        
		return lookUp.get(lookUpKey);
	}

}
