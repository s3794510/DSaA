package TheGreedyGnomesProblem;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileHandler {
	public static String[][] getMap(String inFile){
        try{
            File goldMap = new File(inFile);
            Scanner myReader = new Scanner(goldMap);

            int m = myReader.nextInt();
            int n = myReader.nextInt();
            String [][]goldMine = new String[m][n];
            
            int i =0;
            int j = 0;
            while(myReader.hasNext()){
                if(j == n){
                    i++;
                    j = 0;
                }
                goldMine[i][j] = myReader.next();
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
}
