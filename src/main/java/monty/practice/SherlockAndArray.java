package monty.practice;

import java.util.Arrays;
import java.util.List;

public class SherlockAndArray {
	
	public static void main(String[] args) {
		System.out.println(balancedSums(Arrays.asList(0,0,0,0,0,0,0,0,1,0,0,0,0,0,0)));
		//System.out.println(balancedSums(Arrays.asList(1,2,3)));
	}
	
	private static String balancedSums(List<Integer> arr) {
		
		/*
		 * for(int i=0;i<arr.size();i++) { if(getSum(arr, 0, i)==getSum(arr, i+1,
		 * arr.size())) { return "YES"; } }
		 */
		
		int mid=(0+arr.size())/2;
		int start=0;
		int end=arr.size()-1;
		while(start<end){
			int leftSum=getSum(arr,0,mid);
			int rightSum=getSum(arr,mid+1,arr.size());
			if(leftSum==rightSum) {
				return "YES";
			}
			if(leftSum>rightSum) {
				end=mid;
				mid=(start+mid)/2;
				
				
			}else {
				start=mid+1;
				mid=(mid+1+end)/2;
				
			}
		}
		
		//Edge case Scenario
		if(start==end){
            if(getSum(arr, 0, start)==getSum(arr, start+1, arr.size())){
                return "YES";
            }
        }
		
		
		
		return "NO";
	}
	
	private static int getSum(List<Integer> arr,int start,int end) {
		int sum=0;
		for(int i=start;i<end;i++) {
			sum+=arr.get(i);
		}
		return sum;
	}

}
