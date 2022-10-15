package monty.practice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class GridlandMetro {
	
	public static void main(String[] args) {
		
		GridlandMetro gm=new GridlandMetro();
		/*
		 * List<List<Integer>>
		 * tracks=Arrays.asList(Arrays.asList(2,2,3),Arrays.asList(3,1,4),
		 * Arrays.asList(4,4,4));
		 */
		
		List<List<Integer>> tracks=Arrays.asList(Arrays.asList(2,3,6)
				,Arrays.asList(2,1,7),
				Arrays.asList(3,1,3),
				Arrays.asList(3,3,5),
				Arrays.asList(4,1,6),
				Arrays.asList(4,3,6),
				Arrays.asList(5,1,3),
				Arrays.asList(5,2,5),
				Arrays.asList(5,4,6),
				Arrays.asList(6,1,4),
				Arrays.asList(6,2,5),
				Arrays.asList(6,4,6),
				Arrays.asList(7,1,7),
				Arrays.asList(8,1,3),
				Arrays.asList(8,5,7));
		System.out.println(gm.getPositions(8, 7, tracks));
		System.out.println(gm.getPositionsAltApproach(8, 7, tracks));
		
	}
	
	
	
	
	public static class Pair{
		public int c1;
		public int c2;
		
		public Pair(int c1,int c2) {
			this.c1=c1;
			this.c2=c2;
		}
	}
	
	public static class RowStatus{
		public int occupiedSpots;
		public List<Pair> allPairs;
		
		public RowStatus(int occupiedSpots,List<Pair> allPairs) {
			this.occupiedSpots=occupiedSpots;
			this.allPairs=allPairs;
		}
	}
	
	public static class PairSorter implements Comparator<Pair>{

		public int compare(Pair o1, Pair o2) {
			// TODO Auto-generated method stub
			if(o1.c1<o2.c1) {
				return -1;
			}else if(o1.c1>o2.c1) {
				return 1;
			}else {
				if(o1.c2<o2.c2) {
					return -1;
				}else if (o1.c2>o2.c2) {
					return 1;
				}
			}
			return 0;
		}
		
	}
	
	private long getPositionsAltApproach(int n,int m, List<List<Integer>> tracks) 
	{
		long availableSpots=Long.valueOf(n)*Long.valueOf(m);
		if(tracks.size()==0) {
			return availableSpots;
		}
		
		Map<Integer,RowStatus> memoryMap=new HashMap<Integer,RowStatus>();
		PairSorter pairSorter=new PairSorter();
		for(List<Integer> indTrack:tracks) {
			int row=indTrack.get(0);
			int c1=indTrack.get(1);
			int c2=indTrack.get(2);
			if(!memoryMap.containsKey(row)) {
				int takenUpSpots=c2-c1+1;
				//availableSpots-=takenUpSpots;
				Pair pair=new Pair(c1,c2);
				List<Pair> allPairs=new ArrayList<Pair>();
				allPairs.add(pair);
				RowStatus rs=new RowStatus(takenUpSpots, allPairs);
						
				memoryMap.put(row, rs);
			}else {
				RowStatus rs=memoryMap.get(row);
				Pair currentPair=new Pair(c1,c2);
				mergePairs(rs,currentPair,pairSorter);
				//availableSpots-=rollingDiff;
			}
		}
		
		for(Entry<Integer, RowStatus> indRowStatus:memoryMap.entrySet()) {
			availableSpots-=indRowStatus.getValue().occupiedSpots;
		}
			
		
		return availableSpots;
		
	}
	
	public void mergePairs(RowStatus rs,Pair currentPair,PairSorter pairSorter) {
		List<Pair> allPairs=rs.allPairs;
		allPairs.add(currentPair);
		Collections.sort(allPairs,pairSorter);
		
		List<Pair> newAllPairs=new ArrayList<Pair>();
		
		Pair rollingPair=allPairs.get(0);
		//Everything relies on proper sorting
		for(int i=1;i<allPairs.size();i++) {
			//Clear overlap or end to end condition
			if(allPairs.get(i).c1<=(rollingPair.c2+1)) {
				rollingPair.c2=Math.max(rollingPair.c2, allPairs.get(i).c2);
			}else {
				newAllPairs.add(rollingPair);
				rollingPair=allPairs.get(i);
			}
			
			//Add if its the final iteration, as it won't have a chance to get added
			if(i==allPairs.size()-1) {
				newAllPairs.add(rollingPair);
			}
		}
		int newOccupiedCount=0;
		for(Pair indPair:newAllPairs) {
			newOccupiedCount+=(indPair.c2-indPair.c1+1);
		}
		rs.occupiedSpots=newOccupiedCount;
		rs.allPairs=newAllPairs;
		
		
	}
	
	private int getPositions(int n,int m, List<List<Integer>> tracks) {
		boolean[][] city=new boolean[n][m];
		
		
		//Formulate the tracks
		for(List<Integer> indTrack:tracks) {
			int rowOfTrack=indTrack.get(0);
			int startPos=indTrack.get(1);
			int endPos=indTrack.get(2);
			for(int i=startPos-1;i<endPos;i++) {
				city[rowOfTrack-1][i]=true;
			}
			
		}
		
		int availableSpots=0;
		for(int i=0;i<n;i++) {
			for(int j=0;j<m;j++) {
				if(!city[i][j]) {
					availableSpots++;
				}
			}
		}
		
		return availableSpots;
	}
	
	

}
