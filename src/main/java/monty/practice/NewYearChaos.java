package monty.practice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class NewYearChaos {
	
	public static void main(String[] args) throws IOException {
		//List<Integer> q=Arrays.asList(1,2,5,3,7,8,6,4);
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        int t = Integer.parseInt(bufferedReader.readLine().trim());

        IntStream.range(0, t).forEach(tItr -> {
            try {
                int n = Integer.parseInt(bufferedReader.readLine().trim());

                List<Integer> q = Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());
                altApproach2(q);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        bufferedReader.close();
	}
	
	//Solved
	private static void altApproach2(List<Integer> q) {
		Map<String,Integer> minMemoryMap=new HashMap<String,Integer>();
		int prevMin=Integer.MAX_VALUE;
		int len=q.size();
		for(int i=len-1;i>=0;i--) {
			String key=String.valueOf(i)+String.valueOf(len-1);
			prevMin=Math.min(prevMin, q.get(i));
			minMemoryMap.put(key, prevMin);
		}
		
		
		int totalBribes=0;
		boolean chaotic=false;
		for(int i=0;i<q.size();i++) {
			int iValue=q.get(i);
			int bribes=0;
			String key=String.valueOf(i+1)+String.valueOf(len-1);
			if(minMemoryMap.containsKey(key) &&  minMemoryMap.get(key)>iValue) {
				continue;
			}
			for(int j=i+1;j<q.size();j++) {
				int jValue=q.get(j);
				if(iValue>jValue) {
					bribes++;
				}
				if(bribes>2) {
					break;
				}
				String keyJ=String.valueOf(j+1)+String.valueOf(len-1);
				if(minMemoryMap.containsKey(keyJ) &&  minMemoryMap.get(keyJ)>iValue) {
					break;
				}
				
			}
			if(bribes>2) {
				chaotic=true;
			}else {
				totalBribes+=bribes;
			}
		}
		
		System.out.println(chaotic?"Too chaotic":totalBribes);
	}
}
