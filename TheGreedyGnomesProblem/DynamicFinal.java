package TheGreedyGnomesProblem;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class DynamicFinal{
    private static int m =0 , n = 0;
    private static String goldMine[][];
    private static String direction = "";
    private static int max = 0;

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    
    public static String[][] getMap(String inFile){
        try{
            File goldMap = new File(inFile);
            Scanner myReader = new Scanner(goldMap);

            m = myReader.nextInt();
            n = myReader.nextInt();
            String [][]goldMine = new String[m][n];
            String cell;
            int i =0;
            int j = 0;
            while(myReader.hasNext()){
                if(j == n){
                    i++;
                    j = 0;
                }	
                cell = myReader.next();
                for (int index = 0; index < cell.length(); index++) {              	
                	if(index == 0 && (cell.charAt(0) == 'X' || cell.charAt(0) == '.') && cell.length() == 1) {
                		break;
                	}
                	if (!Character.isDigit(cell.charAt(index))){
                		System.out.println("Map file contains invalid character\n");
                		myReader.close();
                		return null;
                	}
                }
                goldMine[i][j] = cell;
                
                j++;

            }
            myReader.close();
            return goldMine;
            
        }catch(FileNotFoundException e){
            System.out.println("Can't not find file");
            e.printStackTrace();
            return null;
        }
    }
    
    public static int getMaximumGold() throws Exception{
        if (goldMine.length == 0 || goldMine == null){
            return -1;
        }
        int res = 0;

        int[][] goldMineTable = new int[m][n];

        for(int[] rows:goldMineTable)
            Arrays.fill(rows, 0);

        res = DynamicSearch(0,0, goldMineTable);
        // Now through it into another function to extract the path
        extract(goldMineTable);
        return res;
    }
    public static int DynamicSearch(int i, int j,int[][] goldMineTable) throws Exception{
        if(!(i >= goldMine.length || j >= goldMine[0].length)){
            if(goldMineTable[i][j] >0){return goldMineTable[i][j];}
        }else{
            return 0;
        }
        if (goldMine[i][j].equals("X")){
            return 0;
        }

        int down = DynamicSearch(i+1, j, goldMineTable);

        int right = DynamicSearch(i, j+1, goldMineTable);

        max = Math.max(right,down);
        if(goldMine[i][j].equals(".")){
            goldMineTable[i][j] = max;
            return max;
        }
        goldMineTable[i][j] = Integer.valueOf(goldMine[i][j]) + max;
        return Integer.valueOf(goldMine[i][j]) + max;
        
    }
    public static void extract(int[][] goldMineTable){
        int i =0,j =0;
        int right = 0, down = 0;
        goldMine[0][0] = ANSI_YELLOW + "+" + ANSI_RESET;
        while(true){

            right = (j == n-1) ? 0 : goldMineTable[i][j+1];

            down = (i == m-1) ? 0 : goldMineTable[i+1][j];

            if(right == 0 && down == 0){break;}

            if(down >= right){
                if(goldMine[i+1][j].equals(".")) {
                    goldMine[i+1][j] = ANSI_YELLOW + "+" + ANSI_RESET;
                }else{
                    goldMine[i+1][j] = ANSI_YELLOW + "G" + ANSI_RESET;
                }
                direction += "D";
                i++;
            }else{
                if(goldMine[i][j+1].equals(".")) {
                    goldMine[i][j+1] = ANSI_YELLOW + "+" + ANSI_RESET;
                }else{
                    goldMine[i][j+1] = ANSI_YELLOW+ "G" + ANSI_RESET;
                }
                direction += "R";
                j++;
            }
        }
    }
    public static void main(String[] args) throws Exception {
 
        if(args.length >0){
            String fileName = args[0];
            goldMine = getMap(fileName);
        }else{
            System.out.println("Enter your goldMap file's name");
        }

        int maxGold = getMaximumGold();

        System.out.println(m + " " + n);

        for (String[] x : goldMine)
        {
        for (String y : x)
        {
                System.out.print(y + " ");
        }
        System.out.println();
        }
        
        System.out.printf("Steps: %d, Gold = %d, Path = %s", direction.length(),maxGold,direction);
    }
    
}