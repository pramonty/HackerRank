package monty.practice;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Equal {
	
	public static void main(String[] args) throws IOException {
		/*
		 * List<Integer> input=Arrays.asList(1,5,5,10,10); Equal eq=new Equal(); int
		 * solutions=Math.min(eq.solveWithoutMinimum(input),
		 * eq.solveWithMinimum(input)); System.out.println(solutions);
		 */
		
			Equal eq=new Equal();
			BufferedReader br=new BufferedReader(new FileReader(new File("src/main/resources/Equal_testCase_15.txt")));
			BufferedWriter bw=new BufferedWriter(new FileWriter(new File("src/main/resources/Equal_testCase_15_sol.txt")));
			int t=Integer.parseInt(br.readLine().trim());
			IntStream.range(0,t).forEach(itr->{
				try {
					int n=Integer.parseInt(br.readLine().trim());
					List<Integer> input=Stream.of(br.readLine().replaceAll("\\s+$", "").split(" ")).map(Integer::parseInt).collect(Collectors.toList());
					
					int result=Math.min(eq.solveWithoutMinimum(input),eq.solveWithMinimum(input));
					System.out.println(result);
					bw.write(result);
					bw.newLine();
				
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}); 
		
			br.close();
			bw.close();
	}
	
	private void countZero(List<Integer> input) {
		int count=0;
		for(int ind:input) {
			if(ind==0)
				count++;
		}
		System.out.println("The count: "+count);
	}
	
	private int solveWithMinimum(List<Integer> input) {
		int numberOfWays=0;
		countZero(input);
		int minimum=findMinimum(input);
		System.out.println("The minimum: "+minimum);
		updateList(input,-minimum,-1);
		for(int i=0;i<input.size();i++) {
			int value=input.get(i);
			if(value!=0) {
				numberOfWays+=( (value/5) + ((value%5)/2) + ( (value%5)%2) );
			}
		}
		
		return numberOfWays;
		
	}
	
	private int solveWithoutMinimum(List<Integer> input) {
		int numberOfWays=0;
		
		
		
		for(int i=0;i<input.size();i++) {
			int value=input.get(i);
			if(value>0)
				numberOfWays+=( (value/5) + ((value%5)/2) + ((value%5)%2) );
		}
		
		return numberOfWays;
		
	}
	
	private void updateList(List<Integer> input,int updateBy,int ignoreIndex) {
		for(int i=0;i<input.size();i++) {
			if(i!=ignoreIndex)
				input.set(i, input.get(i)+updateBy);
		}
	}
	
	private int findMinimum(List<Integer> input) {
		int minimum=Integer.MAX_VALUE;
		
		for(int ind:input) {
			minimum=Math.min(minimum, ind);
		}
		return minimum;
	}
	
	

}
