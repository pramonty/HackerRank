package monty.practice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

//Unsolved
public class CrossWordPuzzle {
	public static void main(String[] args) throws IOException {
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        

        List<String> crossword = IntStream.range(0, 10).mapToObj(i -> {
            try {
                return bufferedReader.readLine();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        })
            .collect(Collectors.toList());

        String words = bufferedReader.readLine();
         
        CrossWordPuzzle cp=new CrossWordPuzzle();

        char[][] result = cp.solve(crossword, words);
        
        for(int i=0;i<10;i++) {
        	for(int j=0;j<10;j++) {
        		System.out.print(result[i][j]);
        	}
        	System.out.println();
        }

        

        bufferedReader.close();
        
	}
	
	private Map<Integer,List<String>> getMap(String countries){
		Map<Integer,List<String>> returnee=new HashMap<Integer, List<String>>();
		for(String ind:countries.split(";")) {
			if(returnee.containsKey(ind.length())) {
				returnee.get(ind.length()).add(ind);
			}else {
				List<String> val=new ArrayList<String>();
				val.add(ind);
				returnee.put(ind.length(), val);
			}
		}
		
		
		return returnee;
	}
	
	private char[][] solve(List<String> crossword,String countries){
		/*
		 * Map<Integer,String> countryMap=Arrays.stream(countries.split(";"))
		 * .collect(Collectors.toMap(str->str.length(), str->str));
		 */
		//Map<Integer,List<String>> countryMap=getMap(countries);
		Map<Integer,List<String>> countryMap=Arrays.stream(countries.split(";"))
				.collect(Collectors.groupingBy(str->str.length()));
		
		
		char[][] crossWordArr=convertToArray(crossword);
		
		solveRecurssive(crossWordArr, countryMap,true);
		
		return crossWordArr;
		
	}
	
	private char[][] convertToArray(List<String> crossword){
		char[][] crossWordArr=new char[10][10];
		for(int i=0;i<10;i++) {
			for(int j=0;j<10;j++) {
				crossWordArr[i][j]=crossword.get(i).charAt(j);
			}
		}
		
		return crossWordArr;
	}
	
	private boolean solveRecurssive(char[][] crossword,Map<Integer,List<String>> countries,boolean rowPass){
		
		//i is for rows and j is for columns
		for(int i=0;i<10;i++) {
			StringBuilder strb=new StringBuilder();
			for(int j=0;j<10;j++) {
				if(crossword[rowPass?i:j][rowPass?j:i]!='+')
					strb.append(crossword[rowPass?i:j][rowPass?j:i]);
			}
			if(!countries.containsKey(strb.length())) {
				continue;
			}
			String word=countries.get(strb.length()).get(0);
			if(isAMatch(strb,word)) {
				
			}
			
		}
		
		return false;
		
	}
	
	private boolean isAMatch(StringBuilder strb,String word) {
		for(int i=0;i<word.length();i++) {
			if(strb.charAt(i)!='-' && strb.charAt(i)!=word.charAt(i)) {
				return false;
			}
		}
		return true;
	}
	

}
