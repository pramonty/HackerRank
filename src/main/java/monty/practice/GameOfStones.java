package monty.practice;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameOfStones {
	
	private Map<Integer,Boolean> solutionMap;
	
	public GameOfStones() {
		solutionMap=new HashMap<Integer, Boolean>();
	}
	
	public static void main(String[] args) {
		GameOfStones gs=new GameOfStones();
		List<Integer> startingPoint=Arrays.asList(100);
		for(int ind:startingPoint) {
			boolean winner=gs.gameOfStones(ind);
			System.out.println(winner?"First":"Second");
		}
	}
	
	private boolean gameOfStones(int n) {
		
		if(n<2) {
			return false;
		}
		
		
		//Is there any strategy using which player can win?
		if(!solutionMap.containsKey(n)) {
			boolean winner_s1=!gameOfStones(n-2);
			
			boolean winner_s2=!gameOfStones(n-3);
			
			boolean winner_s3=!gameOfStones(n-5);
			
			solutionMap.put(n, (winner_s1 || winner_s2 || winner_s3));
		}
		
		//return winner_s1 || winner_s2 || winner_s3;
			
		return solutionMap.get(n);
	}

}
