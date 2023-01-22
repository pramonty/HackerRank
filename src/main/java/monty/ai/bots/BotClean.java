package monty.ai.bots;

import java.util.Scanner;

public class BotClean {
	
	static boolean immediateVicinityCheck(int posr,int posc, String board[]) {
		if(posr+1<5 && board[posr+1].charAt(posc)=='d') {
			System.out.println("DOWN");
			return true;
		}else if(posr-1>=0 && board[posr-1].charAt(posc)=='d') {
			System.out.println("UP");
			return true;
		}else if(posc+1<5 && board[posr].charAt(posc+1)=='d') {
			System.out.println("RIGHT");
			return true;
		}else if(posc-1>=0 && board[posr].charAt(posc-1)=='d') {
			System.out.println("LEFT");
			return true;
		}
		
		return false;
		
	}
	
	static void next_move(int posr, int posc, String[] board){
		if(board[posr].charAt(posc)=='d') {
			System.out.println("CLEAN");
			return;
		}
		if(immediateVicinityCheck(posr, posc, board)) {
			return;
		}
		
		int acc_left,acc_right,acc_up,acc_down;
		acc_left=acc_right=acc_up=acc_down=0;
		
		for(int i=0;i<5;i++) {
			for(int j=0;j<5;j++) {
				if(board[i].charAt(j)=='d') {
					if(posr>i)
						acc_up++;
					else if(posr<i)
						acc_down++;
					if(posc>j)
						acc_left++;
					else if(posc<j)
						acc_right++;
				}
			}
		}
		//1=UP, 2=DOWN, 3=LEFT, 4=RIGHT
		int max=1;
		int maxVal=acc_up;
		if(acc_down>maxVal) {
			max=2;
			maxVal=acc_down;
		}
		if(acc_left>maxVal) {
			max=3;
			maxVal=acc_left;
		}
		if(acc_right>maxVal) {
			max=4;
		}
		
		System.out.println(maxToMove(max));
		
    }
	
	private static String maxToMove(int max) {
		return max==1?"UP":max==2?"DOWN":max==3?"LEFT":"RIGHT";
	}

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int [] pos = new int[2];
        String board[] = new String[5];
        for(int i=0;i<2;i++) pos[i] = in.nextInt();
        for(int i=0;i<5;i++) board[i] = in.next();
        next_move(pos[0], pos[1], board);
    }

}
