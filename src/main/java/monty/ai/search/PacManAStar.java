package monty.ai.search;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;

import monty.ai.search.PacManBFS.Node;

public class PacManAStar {
	static int max_r = 0;
	static int max_c = 0;
	static int target_r=0;
	static int target_c=0;
	static Map<String, Node> nodesMap = new HashMap<String, Node>();
	static List<Node> exploredNodes = new ArrayList<Node>();

	static class Node {
		int row;
		int column;
		char character;
		boolean explored;
		boolean visited;
		int totalPathCost;

		String up;
		String left;
		String right;
		String down;
		Node parent;

		public Node(int row, int column, char character, boolean explored, boolean visited) {
			super();
			this.row = row;
			this.column = column;
			this.character = character;
			this.explored = explored;
			this.visited = visited;
			this.up = null;
			this.left = null;
			this.right = null;
			this.down = null;
			this.parent = null;
			totalPathCost=0;
			populateNeighbours();
		}

		void populateNeighbours() {
			if (row==0) {
				up=null;
			}
			else if(up==null) {
				up=getKeyFromPosition(row-1, column);
			}
			if(column==0) {
				left=null;
			}
			else if(left ==null) {
				left=getKeyFromPosition(row, column-1);
			}
			if(row==max_r) {
				down=null;
			}
			else if(down==null) {
				down=getKeyFromPosition(row+1, column);
			}
			if(column==max_c) {
				right=null;
			}
			else if(right == null) {
				right=getKeyFromPosition(row, column+1);
			}	
		}
		
		void setTotalPathCost(int actionCost) {
			int hueristicCost=Math.abs(this.row-target_r)+Math.abs(this.column-target_c);
			totalPathCost=actionCost+hueristicCost;
		}
	}

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);

		int pacman_r = in.nextInt();
		int pacman_c = in.nextInt();

		int food_r = in.nextInt();
		int food_c = in.nextInt();

		int r = in.nextInt();
		int c = in.nextInt();

		max_r = r - 1;
		max_c = c - 1;
		
		target_r=food_r;
		target_c=food_c;

		String[] grid = new String[r];

		for (int i = 0; i < r; i++) {
			grid[i] = in.next();
		}
		dfs(r, c, pacman_r, pacman_c, food_r, food_c, grid);
	}

	static void initializeStateSpace(String[] grid, int r, int c) {
		for (int i = 0; i < r; i++) {
			for (int j = 0; j < c; j++) {
				Node newNode = new Node(i, j, grid[i].charAt(j), false, false);
				nodesMap.put(getKeyFromPosition(i, j), newNode);
			}
		}
	}

	static boolean isGoalState(Node currNode, int food_r, int food_c) {
		return currNode.row == food_r && currNode.column == food_c;
	}

	static void evaluateAndAddNode(Node currNode, Node node, Queue<Node> frontier) {
		if (node != null && !node.explored && !node.visited && node.character != '%') {
			node.visited = true;
			node.parent = currNode;
			node.setTotalPathCost(node.character=='.'?0:1);
			frontier.add(node);
		}
	}

	static void expandNode(Node currNode, Queue<Node> frontier) {

		if (currNode.up != null)
			evaluateAndAddNode(currNode, nodesMap.get(currNode.up), frontier);
		if (currNode.left != null)
			evaluateAndAddNode(currNode, nodesMap.get(currNode.left), frontier);
		if (currNode.right != null)
			evaluateAndAddNode(currNode, nodesMap.get(currNode.right), frontier);
		if (currNode.down != null)
			evaluateAndAddNode(currNode, nodesMap.get(currNode.down), frontier);

	}

	static void printNodeIndex(Node currNode) {
		System.out.println(String.format("%d %d", currNode.row, currNode.column));
	}

	static String getKey(Node currNode) {
		return getKeyFromPosition(currNode.row, currNode.column);
	}
	
	static String getKeyFromPosition(int row,int column) {
		return String.valueOf(row)+"|"+String.valueOf(column);
	}
	
	
	
	static class NodeComparator implements Comparator<Node>{

		@Override
		public int compare(Node o1, Node o2) {
			// TODO Auto-generated method stub
			if(o1.totalPathCost<o2.totalPathCost)
				return -1;
			else if(o1.totalPathCost>o2.totalPathCost)
				return 1;
			else 
				return 0;
		}
		
		
	}

	static void dfs(int r, int c, int pacman_r, int pacman_c, int food_r, int food_c, String[] grid) {
		initializeStateSpace(grid, r, c);
		// Stack<Node> frontier=new Stack<Node>();
		Queue<Node> frontier = new PriorityQueue<Node>(new NodeComparator());
		Node initialNode = nodesMap.get(getKeyFromPosition(pacman_r, pacman_c));
		initialNode.setTotalPathCost(0);
		frontier.add(initialNode);
		int totalNodesExplored = 0;
		Node foodNode = null;
		while (!frontier.isEmpty()) {
			Node currNode = frontier.poll();
			totalNodesExplored++;
			currNode.explored = true;
			exploredNodes.add(currNode);
			if (isGoalState(currNode, food_r, food_c)) {
				foodNode = currNode;
				break;
			}
			expandNode(currNode, frontier);
			// counter++;

		}

		System.out.println(totalNodesExplored);

		
		for(Node currNode : exploredNodes) {
			 printNodeIndex(currNode);		 
		}
		

		int length = -1;

		Node currNode = foodNode;
		List<Node> path = new ArrayList<Node>();
		while (currNode != null) {
			path.add(currNode);
			length++;
			currNode = currNode.parent;
		}

		System.out.println(length);

		/*for (int i = path.size() - 1; i >= 0; i--) {
			int p_r = path.get(i).row;
			int p_c = path.get(i).column;
			String rowString = grid[p_r];
			String str = rowString.substring(0, p_c) + "*" + rowString.substring(p_c + 1);
			grid[p_r] = str;
		}

		for (String str : grid) {
			System.out.println(str);
		}*/

		
		for(int i=path.size()-1;i>=0;i--) { 
			 printNodeIndex(path.get(i)); 
		}
		 

	}
}
