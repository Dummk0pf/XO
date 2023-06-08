import java.util.Scanner;
import java.util.Arrays;

public class Table {
    private int[][] table = {{-1,-1,-1},{-1,-1,-1},{-1,-1,-1}};
    //-1 indicates that the position is empty
    // 0 indicates that the position is a O
    // 1 indicates that the position is a X
    private int moveCounter = 0;
    private Scanner s;

    public Table(Scanner s){
        this.s = s;
    }

    public void twoPlayerMode(){
        System.out.println("Entering Two player Mode .. .. ");
        System.out.println("Dramatic Music plays ....");
        displayTable();

        while(true && (moveCounter++ <= 1000)){
            System.out.print("Enter the position for marking 'X'(row column): ");
            int rowPosPlayerX = s.nextInt();
            int colPosPlayerX = s.nextInt();
            while(!(validPosition(rowPosPlayerX, colPosPlayerX))){
                System.out.println("You are placing in a pre-occupied or invalid position...");
                System.out.print("Enter the position for marking 'X'(row column): ");
                rowPosPlayerX = s.nextInt();
                colPosPlayerX = s.nextInt();
            }
            table[rowPosPlayerX][colPosPlayerX] = 1;
            displayTable();
            if(checkPosition(rowPosPlayerX, colPosPlayerX)){
                System.out.println("Player X wins !!! ;)");
                break;
            }
            else if(isFull()){
                System.out.println("Draw !!!");
                break;
            }
            
            System.out.print("Enter the position for marking 'O'(row column): ");
            int rowPosPlayerO = s.nextInt();
            int colPosPlayerO = s.nextInt();
            while(!(validPosition(rowPosPlayerO, colPosPlayerO))){
                System.out.println("You are placing in a pre-occupied or invalid position...");
                System.out.print("Enter the position for marking 'X'(row column): ");
                rowPosPlayerO = s.nextInt();
                colPosPlayerO = s.nextInt();
            }
            table[rowPosPlayerO][colPosPlayerO] = 0;
            displayTable();
            if(checkPosition(rowPosPlayerO, colPosPlayerO)){
                System.out.println("Player O wins!!!");
                break;
            }
            else if(isFull()){
                System.out.println("Draw !!!");
                break;
            }
        }
        if(tooManyPlays(moveCounter)){
            return;
        }
    }


    public void displayTable(){
        int rows = table.length;
        int columns = table[0].length;
        for(int i=0; i<rows; i++){
            for(int j=0; j<columns ;j++){
                char symbol = (table[i][j] == 0)?'O':((table[i][j] == 1)?'X':'_');
                System.out.print(symbol+""+'|');
            }
            System.out.println();
        }
    }

    public boolean checkPosition(int lastRow,int lastCol){
        boolean result = false;
        boolean rowFlag = true;
        boolean colFlag = true;
        boolean diaFlag = true;

        int length = table.length;
        int lastMove = table[lastRow][lastCol];
        for(int i=0;i<length;i++){
            if(table[lastRow][i] != lastMove){
                rowFlag = false;
                break;
            }
        }
        if(rowFlag)
        return !result;
        
        for(int i=0;i<length;i++){
            if(table[i][lastCol] != lastMove){
                colFlag = false;
                break;
            }
        }
        if(colFlag)
        return !result;
        
        if((lastRow + lastCol) == (length-1)){
            for(int i=0;i<length;i++){
                if(table[i][length-i-1] != lastMove){
                    diaFlag = false;
                    break;
                }
            }
        }
        else if((lastRow - lastCol) == 0){
            for(int i=0;i<length;i++){
                if(table[i][i] != lastMove){
                    diaFlag = false;
                    break;
                }
            }
        }
        else{
            diaFlag = false;
        }
        if(diaFlag)
        return !result;

        return result;
    }

    public boolean isFull(){
        String tableString = Arrays.deepToString(table);
        if(tableString.contains("-1")){
            return false;
        }
        else{
            return true;
        }
    }

    public boolean validPosition(int rowPos,int colPos){
        if((rowPos>=0 && rowPos<=2)&& (colPos>=0 && colPos<=2)  && table[rowPos][colPos] == -1)
        return true;
        else
        return false;
    }

    public boolean tooManyPlays(int moves){
        if(moves >= 1000){
            System.out.println("It's Over 1000!!!");
            return true;
        }
        return false;
    }
}