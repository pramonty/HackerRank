package monty.ai.search;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;

public class PacManDFS {
	
	static class Node{
		int row;
		int column;
		char character;
		boolean explored;
		boolean inStack;
		//Bad memory usage ideal useCase for inheritence??
		int max_r;
		int max_c;
		
		Node up;
		Node left;
		Node right;
		Node down;
		Node parent;
		public Node(int row, int column, char character, boolean explored,boolean inStack,int max_r,int max_c,String[] grid) {
			super();
			this.row = row;
			this.column = column;
			this.character = character;
			this.explored = explored;
			this.inStack=inStack;
			this.max_r=max_r;
			this.max_c=max_c;
			this.up=null;
			this.left=null;
			this.right=null;
			this.down=null;
			this.parent=null;
			populateNeighbours(grid);
		}
		
		public Node(int row, int column, char character, boolean explored,boolean inStack,int max_r,int max_c) {
			super();
			this.row = row;
			this.column = column;
			this.character = character;
			this.explored = explored;
			this.inStack=inStack;
			this.up=null;
			this.left=null;
			this.right=null;
			this.down=null;
			this.parent=null;
			this.max_r=max_r;
			this.max_c=max_c;
		}
		
		void populateNeighbours(String[] grid) {
			if (row==0) {
				up=null;
			}
			else if(up==null) {
				up=new Node(row-1,column,grid[row-1].charAt(column),false,false,max_r,max_c);
				up.down=this;
			}
			if(column==0) {
				left=null;
			}
			else if(left ==null) {
				left=new Node(row,column-1,grid[row].charAt(column-1),false,false,max_r,max_c);
				left.right=this;
			}
			if(row==max_r) {
				down=null;
			}
			else if(down==null) {
				down=new Node(row+1,column,grid[row+1].charAt(column),false,false,max_r,max_c);
				down.up=this;
			}
			if(column==max_c) {
				right=null;
			}
			else if(right == null) {
				right=new Node(row,column+1,grid[row].charAt(column+1),false,false,max_r,max_c);
				right.left=this;
			}
				
			
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
    
        String[] grid = new String[r];

        for(int i = 0; i < r; i++) {
            grid[i] = in.next();
        }
        dfs( r, c, pacman_r, pacman_c, food_r, food_c, grid);
	}

	static boolean isGoalState(Node currNode,int food_r,int food_c) {
		return currNode.row==food_r && currNode.column==food_c;
	}
	
	static void expandNode(Node currNode,String[] grid,Stack<Node> frontier,Set<String> reached) {
		if(currNode.up!=null && !currNode.up.explored && !currNode.up.inStack && currNode.up.character!='%' && !reached.contains(String.valueOf(currNode.up.row)+String.valueOf(currNode.up.column))) {
			Node upNode=currNode.up;
			upNode.populateNeighbours(grid);
			upNode.inStack=true;
			upNode.parent=currNode;
			frontier.add(upNode);
		}
		if(currNode.left!=null && !currNode.left.explored && !currNode.left.inStack && currNode.left.character!='%' && !reached.contains(String.valueOf(currNode.left.row)+String.valueOf(currNode.left.column))) {
			Node leftNode=currNode.left;
			leftNode.populateNeighbours(grid);
			leftNode.inStack=true;
			leftNode.parent=currNode;
			frontier.add(leftNode);
		}
		if(currNode.right!=null && !currNode.right.explored && !currNode.right.inStack && currNode.right.character!='%' && !reached.contains(String.valueOf(currNode.right.row)+String.valueOf(currNode.right.column))) {
			Node rightNode=currNode.right;
			rightNode.populateNeighbours(grid);
			rightNode.inStack=true;
			rightNode.parent=currNode;
			frontier.add(rightNode);
		}
		if(currNode.down!=null && !currNode.down.explored && !currNode.down.inStack && currNode.down.character!='%' && !reached.contains(String.valueOf(currNode.down.row)+String.valueOf(currNode.down.column))) {
			Node downNode=currNode.down;
			downNode.populateNeighbours(grid);
			downNode.inStack=true;
			downNode.parent=currNode;
			frontier.add(downNode);
		}
			
	}
	static void printNodeIndex(Node currNode) {
		System.out.println(String.format("%d %d", currNode.row,currNode.column));
	}
	static void dfs(int r,int c, int pacman_r,int pacman_c,int food_r,int food_c,String[] grid) {
		Node initialNode=new Node(pacman_r, pacman_c, grid[pacman_r].charAt(pacman_c), false,true,r-1,c-1,grid);
		Stack<Node> frontier=new Stack<Node>();
		frontier.add(initialNode);
		int totalNodesExplored=0;
		List<Node> exploredNodes=new ArrayList<Node>();
		//int counter=0;
		Set<String> reached=new HashSet<String>();
		Node foodNode=null;
		while(!frontier.isEmpty()) {
			Node currNode=frontier.pop();
			totalNodesExplored++;
			currNode.explored=true;
			reached.add(String.valueOf(currNode.row)+String.valueOf(currNode.column));
			//printNodeIndex(currNode);
			exploredNodes.add(currNode);
			if(isGoalState(currNode,food_r,food_c)) {
				foodNode=currNode;
				break;
			}
			expandNode(currNode, grid, frontier,reached);
			//counter++;
			
		}
		
		System.out.println(totalNodesExplored);
		
		 for(Node currNode : exploredNodes) {
			 printNodeIndex(currNode); 
		 
		 }
		 
		 int length=-1;
		 
		 Node currNode=foodNode;
		 List<Node> path=new ArrayList<Node>();
		 while(currNode!=null) {
			 path.add(currNode);
			 length++;
			 currNode=currNode.parent;
		 }
		 
		 System.out.println(length);
		 
		 for(int i=path.size()-1;i>=0;i--) {
			 printNodeIndex(path.get(i));
		 }
		 
		 for(int i=path.size()-1;i>=0;i--) {
			 int p_r=path.get(i).row;
			 int p_c=path.get(i).column;
			 String rowString=grid[p_r];
			 String str=rowString.substring(0,p_c)+"*"+rowString.substring(p_c+1);
			 grid[p_r]=str;
		 }
		 
		 for(String str:grid) {
			 System.out.println(str);
		 }
		
		
	}
}
