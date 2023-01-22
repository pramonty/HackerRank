package monty.ai.adversarial;

import java.util.Arrays;
import java.util.Scanner;

public class TicTacToe {
	
	static final char EMPTY_POS='_';
	static char OPPN;
	
	static class MoveAndUtil{
		String move;
		int util;
		public MoveAndUtil(String move, int util) {
			super();
			this.move = move;
			this.util = util;
		}
		
		
		
	}
	
	static int maxDepth=5;
	static int currentDepth=0;
	
	public static void main(String[] args) {
		try (Scanner sc = new Scanner(System.in)) {
			char[][] playBoard=new char[3][3];
			String player=sc.nextLine();
			String thisLine="";
			for(int i=0;i<3;i++) {
				thisLine=sc.nextLine();
				for(int j=0;j<3;j++) {
					playBoard[i][j]=thisLine.charAt(j);
				}
			}
			
			
			
			char playerToMove=player.charAt(0);
			
			if(playerToMove=='X')
				OPPN='O';
			else
				OPPN='X';
			
			String nextMoveToPlay=getNextMove(playerToMove , playBoard);
			System.out.println(nextMoveToPlay.charAt(0)+" " +nextMoveToPlay.charAt(2));
		}
		
	}
	
	static String getNextMove(char playerToMove,char[][] playBoard) {
		
		MoveAndUtil nextMove=maxMove(playerToMove, playBoard);
		
		return nextMove.move;
				
	}
	
	
	static MoveAndUtil maxMove(char playerToMove,char[][] playBoard) {
		currentDepth++;
		//System.out.println("max: Current Depth: "+currentDepth);
		if (currentDepth>=maxDepth || isTerminal(playBoard)) {
			return new MoveAndUtil("", evalState(playBoard, playerToMove));
		}
		
		
		MoveAndUtil returnee=new MoveAndUtil("", Integer.MIN_VALUE);
		
		for(int i=0;i<3;i++) {
			for(int j=0;j<3;j++) {
				//System.out.println("i/j: "+i+" "+j);
				if(playBoard[i][j]==EMPTY_POS) {
					playBoard[i][j]=playerToMove;
					//int expUtil=evalState(playBoard, playerToMove);
					//String theMove=i+"|"+j;
					//System.out.println("Max ka move: "+theMove);
					MoveAndUtil moveAndUtil=minMove(playerToMove,playBoard);
					currentDepth--;
					//System.out.println("max move ka util: "+moveAndUtil.util);
					
					if(moveAndUtil.util>returnee.util) {
						returnee.util=moveAndUtil.util;
						returnee.move=i+"|"+j;
					}
					playBoard[i][j]=EMPTY_POS;
				}
			}
			
		}
		//System.out.println(returnee.move+" XXX "+returnee.util);
		return returnee;
	}
	
	static MoveAndUtil minMove(char playerToMove,char[][] playBoard) {
		currentDepth++;
		//System.out.println("min: Current Depth: "+currentDepth);
		if (currentDepth>=maxDepth || isTerminal(playBoard)) {
			return new MoveAndUtil("", evalState(playBoard, playerToMove));
		}
		
		
		MoveAndUtil returnee=new MoveAndUtil("", Integer.MAX_VALUE);
		
		for(int i=0;i<3;i++) {
			for(int j=0;j<3;j++) {
				//System.out.println("i/j: "+i+" "+j);
				if(playBoard[i][j]==EMPTY_POS) {
					playBoard[i][j]=OPPN;
					//int expUtil=evalState(playBoard, playerToMove);
					//String theMove=i+"|"+j;
					//System.out.println("Min ka move: "+theMove);
					MoveAndUtil moveAndUtil=maxMove(playerToMove, playBoard);
					currentDepth--;
					//System.out.println("Min move ka util: "+moveAndUtil.util);
					
					if(moveAndUtil.util<returnee.util) {
						returnee.util=moveAndUtil.util;
						returnee.move=i+"|"+j;
					}
					playBoard[i][j]=EMPTY_POS;
				}
			}
			
		}
		//System.out.println(returnee.move+" OOO "+returnee.util);
		return returnee;
	}
	
	static int evalState(char[][] playBoard,char playerToMove) {
		
		//PRint the state received
		/*System.out.println("player to move: "+playerToMove);
		for(int i=0;i<3;i++) {
			for(int j=0;j<3;j++) {
				System.out.print(playBoard[i][j]);
			}
			System.out.println();
		}*/
		
		int runningUtility=0;
		
		//Row level utility
		for(int i=0;i<3;i++) {
			int rowLvlUtil=0;
			int rowOppnUtil=0;
			boolean opponentPresent=false;
			boolean playerPresent=false;
			
			for(int j=0;j<3;j++) {
				if(playBoard[i][j]==playerToMove) {
					rowLvlUtil=rowLvlUtil*10+1;
					playerPresent=true;
				}
				else if(playBoard[i][j]!=EMPTY_POS) {
					rowOppnUtil=rowOppnUtil*10-1;
					opponentPresent=true;
				}
			}
			if(playerPresent && !opponentPresent) {
				runningUtility+=rowLvlUtil;
			}
			else if(!playerPresent && opponentPresent) {
				runningUtility+=rowOppnUtil;
			}
		}
		//System.out.println("After rowLvl: "+runningUtility);
		//ColumnLevelUtility
		for(int i=0;i<3;i++) {
			int colLvlUtil=0;
			int colOppnUtil=0;
			boolean opponentPresent=false;
			boolean playerPresent=false;
			for(int j=0;j<3;j++) {
				if(playBoard[j][i]==playerToMove) {
					colLvlUtil=colLvlUtil*10+1;
					playerPresent=true;
				}
				else if(playBoard[j][i]!=EMPTY_POS) {
					colOppnUtil=colOppnUtil*10-1;
					opponentPresent=true;
				}
			}
			if(playerPresent && !opponentPresent)
				runningUtility+=colLvlUtil;
			else if(!playerPresent && opponentPresent)
				runningUtility+=colOppnUtil;
		}
		//System.out.println("After col levl: "+runningUtility);
		//Primary Diagonal Utility
		boolean playerPresentDiag=false;
		boolean opponentPresentDiag=false;
		int primDiagUtil=0;
		int primOppnDiagUtil=0;
		for(int i=0;i<3;i++) {
			
			if(playBoard[i][i]==playerToMove) {
				primDiagUtil=primDiagUtil*10+1;
				playerPresentDiag=true;
			}else if(playBoard[i][i]!=EMPTY_POS) {
				primOppnDiagUtil=primOppnDiagUtil*10-1;
				opponentPresentDiag=true;
			}
		}
		if(playerPresentDiag && !opponentPresentDiag)
			runningUtility+=primDiagUtil;
		else if(!playerPresentDiag && opponentPresentDiag)
			runningUtility+=primOppnDiagUtil;
		
		//System.out.println("After prim diag: "+runningUtility);
		//Secondary Diagonal Utility
		//Reusing variable identifiers
		playerPresentDiag=false;
		opponentPresentDiag=false;
		primDiagUtil=0;
		primOppnDiagUtil=0;
		for(int i=2;i>=0;i--) {
			if(playBoard[i][2-i]==playerToMove) {
				primDiagUtil=primDiagUtil*10+1;
				playerPresentDiag=true;
			}else if(playBoard[i][2-i]!=EMPTY_POS) {
				primOppnDiagUtil=primOppnDiagUtil*10-1;
				opponentPresentDiag=true;
			}
		}
		if(playerPresentDiag && !opponentPresentDiag)
			runningUtility+=primDiagUtil;
		else if(!playerPresentDiag && opponentPresentDiag)
			runningUtility+=primOppnDiagUtil;
		
		
		return runningUtility;
	}
	
	static boolean isTerminal(char[][] playBoard) {
		//Rows
		boolean rowSatisfied=true;
		for(int i=0;i<3;i++) {
			char firstChar=playBoard[i][0];
			if(firstChar==EMPTY_POS) {
				rowSatisfied=false;
				continue;
			}
			for(int j=1;j<3;j++) {
				if(playBoard[i][j]!=firstChar) {
					rowSatisfied=false;
					break;
				}else {
					rowSatisfied=true;
				}
			}
			if(rowSatisfied)
				return true;
		}
		
		//Columns
		boolean colSatisfied=true;
		for(int i=0;i<3;i++) {
			char firstChar=playBoard[0][i];
			if(firstChar==EMPTY_POS) {
				colSatisfied=false;
				continue;
			}
			for(int j=1;j<3;j++) {
				if(playBoard[j][i]!=firstChar) {
					colSatisfied=false;
					break;
				}else {
					colSatisfied=true;
				}
			}
			if(colSatisfied)
				return true;
		}
		
		//primaryDiag
		boolean primdiag=true;
		char firstChar=playBoard[0][0];
		
		for(int i=1;i<3;i++) {
			if(firstChar==EMPTY_POS) {
				primdiag=false;
				break;
			}	
			if(playBoard[i][i]!=firstChar) {
				primdiag=false;
				break;
			}
		}
		if(primdiag)
			return true;
		
		//SEcondarydiag
		boolean seconDiag=true;
		firstChar=playBoard[2][0];
		for(int i=1;i>=0;i--) {
			if(firstChar==EMPTY_POS) {
				seconDiag=false;
				break;
			}	
			if(playBoard[i][2-i]!=firstChar) {
				seconDiag=false;
				break;
			}
		}
		if(seconDiag)
			return true;
		
		
		//All PlacesFilled
		boolean allPlacesfilled=true;
		for(int i=0;i<3;i++) {
			for(int j=0;j<3;j++) {
				if(playBoard[i][j]==EMPTY_POS) {
					return false;
				}
			}
		}
		if(allPlacesfilled)
			return true;
		
		return false;
	}

}
