package FileHandler;
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
}
