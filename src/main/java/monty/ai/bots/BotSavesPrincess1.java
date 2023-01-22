package monty.ai.bots;

import java.util.Scanner;

public class BotSavesPrincess1 {
	
	static void displayPathtoPrincess(int n, String [] grid){
		int row_bot=-1;
		int col_bot=-1;
		int row_princess=-1;
		int col_princess=-1;
		
		for(int i=0;i<n;i++) {
			for(int j=0;j<n;j++) {
				if(grid[i].charAt(j)=='m') {
					row_bot=i;
					col_bot=j;
				}else if(grid[i].charAt(j)=='p') {
					row_princess=i;
					col_princess=j;
				}
			}
		}
		
		
		String dir1=row_princess>row_bot?"DOWN":"UP";
		String dir2=col_princess>col_princess?"RIGHT":"LEFT";
		
		for(int i=1;i<=Math.abs(row_princess-row_bot);i++) {
			System.out.println(dir1);
		}
		for(int i=1;i<=Math.abs(col_princess-col_bot);i++) {
			System.out.println(dir2);
		}
    }

	public static void main(String[] args) {
	        Scanner in = new Scanner(System.in);
	        int m;
	        m = in.nextInt();
	        String grid[] = new String[m];
	        for(int i = 0; i < m; i++) {
	            grid[i] = in.next();
	        }

	    displayPathtoPrincess(m,grid);
   }


}
