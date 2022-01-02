import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
 
public class ExhaustiveSearchRefined {
    private static int m =0 , n = 0;
    private static String goldMine[][];
    public static int max = 0;
    static String result ="";
    private static String filename = "goldMap2.txt";
    
    public static void getMap(){      // Map the goldMap.txt to 2d array
        try{
            File goldMap = new File(filename);
            Scanner myReader = new Scanner(goldMap);

            m = myReader.nextInt();
            n = myReader.nextInt();
            System.out.println(m + " " + n);
            goldMine = new String[m][n];
            
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
            
        }catch(FileNotFoundException e){
            System.out.println("Can't not find file");
            e.printStackTrace();
        }
    }

    public static void exhaustiveSearch(int i, int j, int temp, String step) throws Exception{
        // Recursively find path and record the optimal path
        if(!(i >= goldMine.length || j >= goldMine[0].length)){
            step += String.format(" %d %d", i , j);
            if (!(goldMine[i][j].equals(".") || goldMine[i][j].equals("X"))){
               temp += Integer.valueOf(goldMine[i][j]);
            }
        }else{
            if(temp > max){
                max = temp;
                result = step;
            }
            return;
        }
        if (goldMine[i][j].equals("X")){
            return;
        }
        
        exhaustiveSearch(i+1,j,temp, step);

        exhaustiveSearch(i, j+1,temp, step);
    }
    public static ArrayList<int[]> transform(){ // Turn String into 2d array
        ArrayList<int[]> goldPath = new ArrayList<>();
        Scanner sc = new Scanner(result);
        int counter =0;
        int step[] = new int[2];
        while(sc.hasNext()){
            if(counter >= 2){
                goldPath.add(step);
                step = new int[2];  // reset
                counter =0;
            }
            if(sc.hasNextInt()){
                if(counter == 0){step[0] = sc.nextInt();}
                if(counter == 1){step[1] = sc.nextInt();}
                counter ++;
            }
        }
        goldPath.add(step);     // Add the last location

        sc.close();
        return goldPath;
    }
    public static ArrayList<int[]> reduce(ArrayList<int[]> goldPath){   // reduce the redundant step at the end
        int i =0;
        for(i = goldPath.size()-1; i >=0; i--){
            if(!(goldMine[goldPath.get(i)[0]][goldPath.get(i)[1]].equals("."))){
                break;
            }
        }
        if(i == goldPath.size()-1){return goldPath;}
        while(i < goldPath.size()){
            goldPath.remove(i);
        }
        return goldPath;
    }
    public static String plotAndExtract(ArrayList<int[]> goldPath){
        String direction = "";
        goldMine[0][0] = "+";
        for(int i = 1; i < goldPath.size(); i++){
            if(goldPath.get(i)[0] > goldPath.get(i-1)[0]){
                direction += "D";
            }
            else if(goldPath.get(i)[1] > goldPath.get(i-1)[1]){
                direction += "R";
            }

           if(goldMine[goldPath.get(i)[0]][goldPath.get(i)[1]].equals(".")){
                goldMine[goldPath.get(i)[0]][goldPath.get(i)[1]] = "+";
           }else{
            goldMine[goldPath.get(i)[0]][goldPath.get(i)[1]] = "G";
           }
        }
        return direction;
    }

    public static void main(String[] args) throws Exception {
        getInfor();
        if (goldMine.length == 0 || goldMine == null){
            return;
        }
        exhaustiveSearch(0,0,0, "");
        
        ArrayList<int[]> goldPath = reduce(transform());
        String result = plotAndExtract(goldPath);

        for (String[] x : goldMine){
            for (String y : x){
                    System.out.print(y + " ");
            }
            System.out.println();
        }

        System.out.printf("Steps: %d, Gold = %d, Path = %s", result.length(),max,result);
    }
    
}
