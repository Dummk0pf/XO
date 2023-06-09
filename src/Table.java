import java.util.Scanner;
import java.util.Arrays;

public class Table implements TablePosition{
    //-1 indicates that the position is empty
    // 0 indicates that the position is a O
    // 1 indicates that the position is a X
    private Scanner s;

    public Table(Scanner s){
        this.s = s;
        moveCounter[0] = -1;
        setPosition(table);
        setPosition(xPosition);
        setPosition(oPosition);
    }
    
    public void setPosition(int[][] state){
        for(int[] row:state)
        Arrays.fill(row,-1);
    }

    public void onePlayerMode(char turn) throws Exception{
        boolean Turn = true;
        boolean xWins = false;
        boolean oWins = false;
        int engineturn = 0;

        if(turn == 'X'){
            Turn = true;
            engineturn = 0;
        }
        else if(turn == 'O'){
            Turn = false;
            engineturn = 1;
        }
        XOEngine engine = new XOEngine(engineturn);
        System.out.println("Entering One player Mode .. .. ");
        System.out.println("du bist bereits tot ....");
        displayTable();
        
        while((!xWins || !oWins) && (moveCounter[0]++ <= 500)){
            if(Turn){
                xWins = xMove();
                if(xWins||isDraw())
                break;
                oWins = oEngineMove(engine);
                if(oWins||isDraw())
                break;
            }
            else{
                xWins = xEngineMove(engine);
                if(xWins||isDraw())
                break;
                oWins = oMove();
                if(oWins||isDraw())
                break;
            }
        }
    }

    public char twoPlayerMode(char turn){
        boolean Turn = (turn == 'X')?true:false;
        boolean xWins = false;
        boolean oWins = false;
        System.out.println("Entering Two player Mode .. .. ");
        System.out.println("Dramatic Music plays ....");
        displayTable();

        while((!xWins || !oWins) && (moveCounter[0]++ <= 1000)){
            if(Turn){
                xWins = xMove();
                if(xWins||isDraw())
                break;
                oWins = oMove();
                if(oWins||isDraw())
                break;
            }
            else{
                oWins = oMove();
                if(oWins||isDraw())
                break;
                xWins = xMove();
                if(xWins||isDraw())
                break;
            }
        }
        if(tooManyPlays(moveCounter[0]))
        return turn;
        if(xWins)
        return 'O';
        if(oWins)
        return 'X';
        else
        return 'X';
    }
    
    public boolean xMove(){
        System.out.print("Enter the position for marking 'X'(row column): ");
        int rowPosPlayerX = s.nextInt();
        int colPosPlayerX = s.nextInt();
        while(!(validPosition(rowPosPlayerX, colPosPlayerX))){
            System.out.println("You are placing in a pre-occupied or invalid position...");
            System.out.print("Enter the position for marking 'X'(row column): ");
            rowPosPlayerX = s.nextInt();
            colPosPlayerX = s.nextInt();
        }
        xPosition[moveCounter[0]][0] = rowPosPlayerX;
        xPosition[moveCounter[0]][1] = colPosPlayerX;
        table[rowPosPlayerX][colPosPlayerX] = 1;
        displayTable();
        if(checkPosition(rowPosPlayerX, colPosPlayerX)){
            System.out.println("Player X wins !!! ;)");
            return true;
        }
        return false;
    }
    
    public boolean oMove(){
        System.out.print("Enter the position for marking 'O'(row column): ");
        int rowPosPlayerO = s.nextInt();
        int colPosPlayerO = s.nextInt();
        while(!(validPosition(rowPosPlayerO, colPosPlayerO))){
            System.out.println("You are placing in a pre-occupied or invalid position...");
            System.out.print("Enter the position for marking 'O'(row column): ");
            rowPosPlayerO = s.nextInt();
            colPosPlayerO = s.nextInt();
        }
        oPosition[moveCounter[0]][0] = rowPosPlayerO;
        oPosition[moveCounter[0]][1] = colPosPlayerO;
        table[rowPosPlayerO][colPosPlayerO] = 0;
        displayTable();
        if(checkPosition(rowPosPlayerO, colPosPlayerO)){
            System.out.println("Player O wins!!!");
            return true;
        }
        return false;
    }

    public boolean xEngineMove(XOEngine engine) throws Exception{
        System.out.println(";)");
        int[] enginemove = engine.nextMove();
        // System.out.println(Arrays.toString(enginemove));
        xPosition[moveCounter[0]][0] = enginemove[0];
        xPosition[moveCounter[0]][1] = enginemove[1];
        table[enginemove[0]][enginemove[1]] = 1;
        displayTable();
        if(checkPosition(enginemove[0],enginemove[1])){
            System.out.println("Elementary my dear Player :)");
            return true;
        }   
        return false;
    }

    public boolean oEngineMove(XOEngine engine) throws Exception{
        System.out.println(";)");
        int[] enginemove = engine.nextMove();
        oPosition[moveCounter[0]][0] = enginemove[0];
        oPosition[moveCounter[0]][1] = enginemove[1];
        table[enginemove[0]][enginemove[1]] = 0;
        displayTable();
        if(checkPosition(enginemove[0],enginemove[1])){
            System.out.println("Elementary my dear Player :)");
            return true;
        }   
        return false;
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
    
    public boolean isDraw(){
        String tableString = Arrays.deepToString(table);
        if(tableString.contains("-1")){
            return false;
        }
        else{
            System.out.println("Draw !!!");
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
        if(moves >= 500){
            System.out.println("It's Over 1000!!!");
            return true;
        }
        return false;
    }
}