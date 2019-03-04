package org.khu.benchmark.gbench;

/**
 * Hello world!
 *
 */
public class App {
	public static final String T_SPLITTER = "title:";
	public static final String D_SPLITTER = "desc:";
	public static final String M_SPLITTER = "manu:";
	public static final String P_SPLITTER = "price:";
	
	public static void main( String[] args ) {
        System.out.println( "Hello World!" );
        
        String v = "";
        String[] parts = v.split(T_SPLITTER);
        if (parts.length == 2){
        	System.out.println("part1: " + parts[0] + " :: part2: " + parts[1]);
        } else if (parts.length != 2 && v.equals(T_SPLITTER)){
        	System.out.println("Empty String!!");
        } else {
        	System.out.println("Invalid String!!");
        }
        System.out.println("Finished Execution!!");
    }
}
