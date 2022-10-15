package monty.practice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PasswordCracker {
	
	private List<String> solutionArray;
	private Map<String,Boolean> memoryMap;
	private int maxSize;
	
	public PasswordCracker() {
		memoryMap=new HashMap<String,Boolean>();
	}
	
	private int getMaxSize(List<String> passwords) {
		int maxSize=0;
		for(String ind:passwords) {
			if(ind.length()>maxSize) {
				maxSize=ind.length();
			}
		}
		
		return maxSize;
	}
	
	public static void main(String[] args) {
		List<String> passwords=Arrays.asList("the","cake","is","a","lie","thec","ak","ei","sal","ie");
		String loginAttempt="thecakeisaliethecakeisalieakthecakeisaliethecakeisaliethecakeisaliethecakeisaliethecakeisaliethecakeisaliethecakeisaliethecakeisaliethecakeisaliethecakeisaliethecakeisaliethecakeisaliethecakeisaliethecakeisaliethecakeisaliethecakeisaliethecakeisaliethecakeisaliethecakeisaliethecakeisaliethecakeisaliethecakeisaliethecakeisalieathecakeisaliethecakeisaliethecakeisaliethecakeisaliethecakeisaliethecakeisaliethecakeisaliethecakeisaliethethecakeisaliethecakeisaliethecakeisaliethecakeisaliethethecakeisalieakthecakeisaliethecakeisaliethecakeisaliethecakeisaliethecakeisaliesalthecakeisaliethecakeisaliethecakeisaliethecakeisaliethecakeisaliethecakeisaliethecakeisaliethecakeisaliesalthecakeisaliethecakeisalielieakthecakeisalieliethecakeisaliethecakeisaliethecakeisaliethecakeisaliethecakeisaliethecakeisaliethecakeisaliethecakeisaliethecakeisaliethecakeisaliethecakeisaliethecakeisaliethecakeisalieakthecakeisaliethecakeisaliethecakeisaliethecakeisaliethecakeisalieeithecakeisaliethecakeisalieeithecakeisaliethecakeisaliethecakeisaliethecakeisaliethecakeisaliethecakeisaliethecakeisaliethecakeisaliethecakeisaliethecakeisaliethecakeisalieiethecakeisaliethecakeisaliethecakeisaliethecakeisalieisthecakeisaliethecakeisalieiscakeakthecakeisaliethecakeisaliethecakeisaliethecakeisaliethecakeisaliethecakeisaliethecakeisaliethecakeisalieakcakethecakeisaliethecieiethecthecakeisaliethecakeisaliethecakeisaliethecakeisaliethecakeisalieeithecakeisaliethecakeisaliethecakeisaliethecakeisaliethecakeisalieathecakeisaliethecakeisaliethecakeisaliethecakeisaliethecakeisaliethecakeisaliethecakeisaliethecakeisaliethecakeisaliethecakeisaliethecakeisaliethecakeisaliethecakeisaliethecakeisalieeithecakeisaliethecakeisaliethecakeisaliethecakeisalieacakethecakeisaliethecakeisaliesalthecakeisaliethecakeisaliethecakeisaliethecakeisaliethecakeisaliethecakeisaliethecakeisaliethecakeisalieakthecakeisaliethecakeisaliethecakeisaliethecakeisaliethecakeisaliethecakeisaliethecakeisaliethecakeisaliethecakeisaliethecakeisalie";
		PasswordCracker pw=new PasswordCracker();
		pw.solutionArray=new ArrayList<String>();
		pw.maxSize=pw.getMaxSize(passwords);
		Set<String> passwordSet=new HashSet<String>();
		for(String ind:passwords) {
			passwordSet.add(ind);
		}
		if(!pw.crack(passwordSet, loginAttempt)){
			System.out.println("WRONG PASSWORD");
		}else {
			StringBuilder strb=new StringBuilder();
			for(int i=pw.solutionArray.size()-1;i>=0;i--) {
				strb.append(pw.solutionArray.get(i)+" ");
			}
			System.out.println(strb.toString());
		}
		
		
		
	}
	
	private boolean crack(Set<String> passwords,String loginAttempt) {
		//System.out.println(String.format("%s is the problem", loginAttempt));
		if("".equals(loginAttempt)) {
			return true;
		}
		StringBuilder strb=new StringBuilder();
		for(int i=0;i<loginAttempt.length();i++) {
			strb.append(loginAttempt.charAt(i));
			if(passwords.contains(strb.toString())) {
				String newLoginAttempt=loginAttempt.replaceFirst(strb.toString(), "");
				if(!memoryMap.containsKey(newLoginAttempt)) {
					boolean value=crack(passwords,newLoginAttempt);
					if(value) {
						solutionArray.add(strb.toString());
						memoryMap.put(newLoginAttempt, value);
						return value;
					}else {
						memoryMap.put(newLoginAttempt, value);
					}
					
				}else if(memoryMap.get(newLoginAttempt)) {
					return true;
				}
				
			}
			if(strb.length()>maxSize) {
				break;
			}
				
		}
		
		
		return false;
		
	}

}
