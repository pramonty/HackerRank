package monty.ai.search;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;

public class NPuzzle {
	
	static Map<String,PuzzleNode> stateSpace=new HashMap<String,PuzzleNode>();
	
	static class PuzzleNode{
		int[][] gridState;
		int currentStateCost;
		int gridSize;
		int zero_row;
		int zero_column;
		boolean visited;
		boolean explored;
		String moveFromParent;
		PuzzleNode parentNode;
		
		
		public PuzzleNode(int k) {
			this.gridSize=k;
			this.gridState=new int[k][k];
			this.currentStateCost=0;
			visited=false;
			explored=false;
			this.moveFromParent=null;
		}
		
		void evaluateCurrentStateCost() {
			for(int i=0;i<gridSize;i++) {
				for(int j=0;j<gridSize;j++) {
					int currVal=gridState[i][j];
					if(currVal!=0) {
						int currValRow=currVal/gridSize;
						int currValCol=currVal%gridSize;
						int currentCost=Math.abs(i-currValRow)+Math.abs(currValCol-j);
						currentStateCost+=currentCost;
					}else {
						zero_row=i;
						zero_column=j;
					}
				}
			}
		}
		
		boolean isGoalState() {
			
		
			for(int i=0;i<gridSize;i++) {
				for(int j=0;j<gridSize;j++) {
					if( (gridSize*i+j) != gridState[i][j] )
						return false;
				}
		}
			return true;
	}
		
		PuzzleNode getNewNode(int newRow,int newCol,String parentMove) {
			
			int[][] newGrid=Arrays.stream(this.gridState).map(el->el.clone()).toArray($->this.gridState.clone());
			int tmp=newGrid[newRow][newCol];
			newGrid[newRow][newCol]=0;
			newGrid[zero_row][zero_column]=tmp;
			PuzzleNode node=new PuzzleNode(gridSize);
			node.gridState=newGrid;
			node.evaluateCurrentStateCost();
			node.zero_column=newCol;
			node.zero_row=newRow;
			node.moveFromParent=parentMove;
			node.parentNode=this;
			
			String keyFromState=node.getStateKey();
			if(stateSpace.containsKey(keyFromState)) {
				return stateSpace.get(keyFromState);
			}
			
			stateSpace.put(keyFromState, node);
			
			return node;
			
						
		}
		
		List<PuzzleNode> getNeighbours(){
			List<PuzzleNode> neighbours=new ArrayList<PuzzleNode>();
			
			if(zero_row!=0) {
				neighbours.add(getNewNode(zero_row-1, zero_column, "UP"));
			}
			if(zero_column!=0) {
				neighbours.add(getNewNode(zero_row, zero_column-1, "LEFT"));
			}
			
			if(zero_row!=gridSize-1) {
				neighbours.add(getNewNode(zero_row+1, zero_column, "DOWN"));
			}
			if(zero_column!=gridSize-1) {
				neighbours.add(getNewNode(zero_row, zero_column+1, "RIGHT"));
			}
			
			
			return neighbours;
		
		}
		
		String getStateKey() {
			StringBuilder strb=new StringBuilder();
			for(int i=0;i<gridSize;i++) {
				for(int j=0;j<gridSize;j++) {
					strb.append(this.gridState[i][j]);
				}
		}
			return strb.toString();
		}
}
	
	public static void main(String[] args) {
		Scanner scanner=new Scanner(System.in);
		
		int k=scanner.nextInt();
		PuzzleNode problemGrid=new PuzzleNode(k);
		
		for(int i=0;i<k;i++) {
			for(int j=0;j<k;j++) {
				problemGrid.gridState[i][j]=scanner.nextInt();
			}
		}
		
		problemGrid.evaluateCurrentStateCost();
		/*for(PuzzleNode ind:problemGrid.getNeighbours()) {
			System.out.println(ind.isGoalState()+" with cost: "+ind.currentStateCost);
		}*/
		//System.out.println(problemGrid.isGoalState());
		solve(problemGrid);
	}
	
	static class CostComparator implements Comparator<PuzzleNode>{

		@Override
		public int compare(PuzzleNode o1, PuzzleNode o2) {
			// TODO Auto-generated method stub
			if(o1.currentStateCost<o2.currentStateCost) {
				return -1;
			}else if(o1.currentStateCost>o2.currentStateCost) {
				return 1;
			}else {
				return 0;
			}
		}
		
	} 
	
	
	static void expand(PuzzleNode currNode,Queue<PuzzleNode> frontier) {
		
		for(PuzzleNode ind:currNode.getNeighbours()) {
			if(!ind.explored) {
				//System.out.println("Adding "+ind.getStateKey()+" with cost: "+ind.currentStateCost);
				frontier.add(ind);
			}
		}
		
	}
	
	
	
	static void solve(PuzzleNode problem) {
		Queue<PuzzleNode> frontier=new PriorityQueue<PuzzleNode>(new CostComparator());
		frontier.add(problem);
		PuzzleNode goalNode=null;
		stateSpace.put(problem.getStateKey(), problem);
		//int counter=0;
		while(!frontier.isEmpty()) {
			PuzzleNode currNode=frontier.poll();
			/*System.out.println("-----------------------------------");
			System.out.println("CurrNode state: "+currNode.getStateKey());
			System.out.println(currNode.moveFromParent);*/
			currNode.explored=true;
			if(currNode.isGoalState()) {
				//TODO add memory so that it remembers the moves
				goalNode=currNode;
				break;
			}
			expand(currNode,frontier);
		}
		
		if(goalNode!=null) {
			List<String> moves=new ArrayList<String>();
			PuzzleNode nextNode=goalNode;
			while(nextNode!=null) {
				moves.add(nextNode.moveFromParent);
				nextNode=nextNode.parentNode;
			}
			System.out.println(moves.size()-1);
			for(int i=moves.size()-2;i>=0;i--) {
				System.out.println(moves.get(i));
			}
		}else {
			System.out.println("No Solution");
		}
		
	}

}
