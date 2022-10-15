package monty.practise;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import monty.practice.GridlandMetro;
import monty.practice.GridlandMetro.Pair;
import monty.practice.GridlandMetro.PairSorter;
import monty.practice.GridlandMetro.RowStatus;

public class GridLandTest {

	
	@Test
	public void testMergeScene1() {
		GridlandMetro gm=new GridlandMetro();
		
		Pair p1=new Pair(1, 4);
		Pair p2=new Pair(3, 4);
		Pair p3=new Pair(2,3);
		
		List<Pair> allPairs=new ArrayList<Pair>();
		
		allPairs.add(p1);
		allPairs.add(p2);
		Assert.assertTrue(allPairs.size()==2);
		
		PairSorter ps=new PairSorter();
		
		RowStatus rs=new RowStatus(0, allPairs);
		
		gm.mergePairs(rs, p3, ps);
		
		
		Assert.assertTrue(rs.allPairs.size()==1);
		Assert.assertTrue(rs.occupiedSpots==4);
		
	}
	
	@Test
	public void intRangeOverShoot() {
		int n=454132175;
		int m=552745364;
		long i=Long.valueOf(n)*Long.valueOf(m);
		System.out.println("i is: "+i);
	}
	
	@Test
	public void testMergeScene2() {
		GridlandMetro gm=new GridlandMetro();
		
		Pair p1=new Pair(1, 7);
		Pair p2=new Pair(3, 6);
		
		
		List<Pair> allPairs=new ArrayList<Pair>();
		
		allPairs.add(p1);
		
		Assert.assertTrue(allPairs.size()==1);
		
		PairSorter ps=new PairSorter();
		
		RowStatus rs=new RowStatus(0, allPairs);
		
		gm.mergePairs(rs, p2, ps);
		
		
		Assert.assertTrue(rs.allPairs.size()==1);
		Assert.assertTrue(rs.occupiedSpots==7);
		
	}
	
	@Test
	public void testMergeScene3() {
		GridlandMetro gm=new GridlandMetro();
		
		Pair p1=new Pair(1, 3);
		Pair p2=new Pair(5, 7);
		
		
		List<Pair> allPairs=new ArrayList<Pair>();
		
		allPairs.add(p1);
		
		Assert.assertTrue(allPairs.size()==1);
		
		PairSorter ps=new PairSorter();
		
		RowStatus rs=new RowStatus(0, allPairs);
		
		gm.mergePairs(rs, p2, ps);
		
		
		Assert.assertTrue(rs.allPairs.size()==2);
		Assert.assertTrue(rs.occupiedSpots==6);
		
	}
	
	@Test
	public void testMergeScene4() {
		GridlandMetro gm=new GridlandMetro();
		
		Pair p1=new Pair(51168520, 111892295);
		Pair p2=new Pair(111892295, 249112154);
		Pair p3=new Pair(51168520, 348349058);
		Pair p4=new Pair(51168520, 224052272);
		
		
		List<Pair> allPairs=new ArrayList<Pair>();
		
		allPairs.add(p1);
		allPairs.add(p2);
		allPairs.add(p3);
		
		
		
		PairSorter ps=new PairSorter();
		
		RowStatus rs=new RowStatus(0, allPairs);
		
		gm.mergePairs(rs, p4, ps);
		
		
		System.out.println(rs.occupiedSpots);
		System.out.println(rs.allPairs.get(0).c1);
		System.out.println(rs.allPairs.get(0).c2);
		
	}
}
