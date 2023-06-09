public class XOEngine implements TablePosition{
    private int turn;
    private int[][] priorityTable = new int[3][3];
    public XOEngine(int turn){
        this.turn = turn;
    }

    public int[] getStartingPosition(){
        //create random starting position if it works
        int[] start = new int[2];
        if(turn == 1){
            start[0] = 0;
            start[1] = 1;
        }
        else{
            if(table[1][1] == -1){
                start[0] = 1;
                start[1] = 1;
            }
            else{
                start[0] = 2;
                start[1] = 0;
            }
        }
        return start;
    }
    
    public int[] nextMove() throws Exception{
        int length = table.length;
        int[] nextMove = new int[2];
        if(moveCounter[0] == 0){
            nextMove = getStartingPosition();
        }
        else{
            int[] earlymove = priorityFill();
            if(earlymove[0]==-1){
                int emptyspace = 0;
                int[] emptyposition = new int[2];
                int max = priorityTable[0][0];
                for(int i=0;i<length;i++){
                    for(int j=0;j<table.length;j++){
                        if(max < priorityTable[i][j]){
                            max = priorityTable[i][j];
                            nextMove[0] = i;
                            nextMove[1] = j;
                        }
                        if(priorityTable[i][j] == -1){
                            emptyspace++;
                            emptyposition[0] = i;
                            emptyposition[1] = j;
                        }
                    }
                }
                if((emptyspace == 1)&&(max == priorityTable[0][0])||(table[nextMove[0]][nextMove[1]] != -1)){
                    nextMove = emptyposition;
                }
            }
            else{
                nextMove = earlymove;
            }
        }
        return nextMove;
    }

    public int[] priorityFill() throws Exception{
        fillTable();
        if(turn == 1){
            int rows = xPosition.length;
            int[] earlywin = new int[2];
            int[] earlyloss = new int[2];
            for(int i=0;i<rows;i++){
                if((xPosition[i][0] == -1) || (oPosition[i][0] == -1))
                break;
                earlywin = xFill(xPosition[i][0],xPosition[i][1]);
                earlyloss = oFill(oPosition[i][0],oPosition[i][1]);
                if((earlywin[0]!=-1) || (earlyloss[0]!=-1))
                break;
            }
            if(earlywin[0]!=-1){
                return earlywin;
            }
            else if(earlyloss[0]!=-1){
                return earlyloss;
            }
            else{
                return earlywin;
            }
        }
        else if(turn == 0){
            int rows = xPosition.length;
            int[] earlywin = new int[2];
            int[] earlyloss = new int[2];
            for(int i=0;i<rows;i++){
                if((xPosition[i][0] == -1) || (oPosition[i][0] == -1))
                break;
                earlywin = oFill(oPosition[i][0],oPosition[i][1]);
                earlyloss = xFill(xPosition[i][0],xPosition[i][1]);
                if((earlywin[0]!=-1) || (earlyloss[0]!=-1))
                break;
            }
            if(earlywin[0]!=-1){
                return earlywin;
            }
            else if(earlyloss[0]!=-1){
                return earlyloss;
            }
            else{
                return earlywin;
            }
        }
        else{
            throw new Exception("Invalid turn in priority Fill");
        }
    }

    public int[] xFill(int lastRow,int lastCol) throws Exception{
        int priority = 2;
        int xCounter = 0;
        int[] starposition = {-1,-1};

        //Horizontal Filling
        int length = table.length;
        for(int i=0;i<length;i++){
            if(priorityTable[lastRow][i] == 0){
                for(int j=0;j<length;j++)
                priorityTable[lastRow][j] = table[lastRow][j];
                break;
            }
            else if(priorityTable[lastRow][i] == 1){
                xCounter++;
                if(xCounter == 2){
                    for(int k=0;k<length;k++){
                        if((priorityTable[lastRow][k]!=0)&&(priorityTable[lastRow][k]!=1)){
                            starposition[0] = lastRow;
                            starposition[1] = k;
                        }
                    }
                    if(starposition[0]!=-1)
                    return starposition;
                }
                
            }
            else if(priorityTable[lastRow][i] >= priority){
                priorityTable[lastRow][i]++;
            }
            else if(priorityTable[lastRow][i] == -1){
                priorityTable[lastRow][i] = priority;
            }
            else{
                throw new Exception("Critical error invalid value in horizontal filling");
            }
        }
        
        //Vertical Fllling
        xCounter = 0;
        for(int i=0;i<length;i++){
            if(priorityTable[i][lastCol] == 0){
                for(int j=0;j<length;j++)
                priorityTable[j][lastCol] = table[j][lastCol];
                break;
            }
            else if(priorityTable[i][lastCol] == 1){
                xCounter++;
                if(xCounter == 2){
                    for(int k=0;k<length;k++){
                        if((priorityTable[k][lastCol] != 0)&&(priorityTable[k][lastCol] != 1)){
                            starposition[0] = k;
                            starposition[1] = lastCol;
                        }
                    }
                    if(starposition[0]!=-1)
                    return starposition;
                }
            }
            else if(priorityTable[i][lastCol] >= priority){
                priorityTable[i][lastCol]++;
            }
            else if(priorityTable[i][lastCol] == -1){
                priorityTable[i][lastCol] = priority;
            }
            else{
                throw new Exception("Critical error invalid value in vertical filling");
            }
        }
        
        //Diagonal Filling
        xCounter = 0;
        if((lastRow + lastCol) == (length-1)){
            for(int i=0;i<length;i++){
                if(priorityTable[i][length-i-1] == 0){
                    for(int j=0;j<length;j++)
                    priorityTable[i][length-i-1] = table[i][length-i-1];
                    break;
                }
                else if(priorityTable[i][length-i-1] == 1){
                    xCounter++;
                    if(xCounter == 2){
                        for(int k=0;k<length;k++){
                            if((priorityTable[k][length-1-k]!=0)&&(priorityTable[k][length-k-1]!=1)){
                                starposition[0] = k;
                                starposition[1] = length-k-1;
                            }
                        }
                        if(starposition[0]!=-1)
                        return starposition;
                    }
                }
                else if(priorityTable[i][length-i-1] >= priority){
                    priorityTable[i][length-i-1]++;
                }
                else if(priorityTable[i][length-i-1] == -1){
                    priorityTable[i][length-i-1] = priority;
                }
                else{
                    throw new Exception("Critical error invalid value in diagonal filling");
                }
            }
        }
        
        xCounter = 0;
        if((lastRow - lastCol) == 0){
            for(int i=0;i<length;i++){
                if(priorityTable[i][i] == 0){
                    for(int j=0;j<length;j++)
                    priorityTable[i][i] = table[i][i];
                    break;
                }
                else if(priorityTable[i][i] == 1){
                    xCounter++;
                    if(xCounter == 2){
                        for(int k=0;k<length;k++){
                            if((priorityTable[k][k]!=0)&&(priorityTable[k][k]!=1)){
                                starposition[0] = k;
                                starposition[1] = k;
                            }
                        }
                        if(starposition[0]!=-1)
                        return starposition;
                    }
                }
                else if(priorityTable[i][i] >= priority){
                    priorityTable[i][i]++;
                }
                else if(priorityTable[i][i] == -1){
                    priorityTable[i][i] = priority;
                }
                else{
                    throw new Exception("Critical error invalid value in diagonal filling");
                }
            }
        }
        return starposition;
    }
    public int[] oFill(int lastRow,int lastCol) throws Exception{
        int priority = 2;
        int oCounter = 0;
        int[] starposition = {-1,-1};
        
        //Horizontal Filling
        int length = table.length;
        for(int i=0;i<length;i++){
            if(priorityTable[lastRow][i] == 1){
                for(int j=0;j<length;j++)
                priorityTable[lastRow][j] = table[lastRow][j];
                break;
            }
            else if(priorityTable[lastRow][i] == 0){
                oCounter++;
                if(oCounter == 2){
                    for(int k=0;k<length;k++){
                        if((priorityTable[lastRow][k]!=0)&&(priorityTable[lastRow][k]!=1)){
                            starposition[0] = lastRow;
                            starposition[1] = k;
                        }
                    }
                    if(starposition[0]!=-1)
                    return starposition;
                }
                
            }
            else if(priorityTable[lastRow][i] >= priority){
                priorityTable[lastRow][i]++;
            }
            else if(priorityTable[lastRow][i] == -1){
                priorityTable[lastRow][i] = priority;
            }
            else{
                throw new Exception("Critical error invalid value in horizontal filling");
            }
        }
        
        //Vertical Fllling
        oCounter = 0;
        for(int i=0;i<length;i++){
            if(priorityTable[i][lastCol] == 1){
                for(int j=0;j<length;j++)
                priorityTable[j][lastCol] = table[j][lastCol];
                break;
            }
            else if(priorityTable[i][lastCol] == 0){
                oCounter++;
                if(oCounter == 2){
                    for(int k=0;k<length;k++){
                        if((priorityTable[k][lastCol] != 0)&&(priorityTable[k][lastCol] != 1)){
                            starposition[0] = k;
                            starposition[1] = lastCol;
                        }
                    }
                    if(starposition[0]!=-1)
                    return starposition;
                }
            }
            else if(priorityTable[i][lastCol] >= priority){
                priorityTable[i][lastCol]++;
            }
            else if(priorityTable[i][lastCol] == -1){
                priorityTable[i][lastCol] = priority;
            }
            else{
                throw new Exception("Critical error invalid value in vertical filling");
            }
        }
        
        //Diagonal Filling
        oCounter = 0;
        if((lastRow + lastCol) == (length-1)){
            for(int i=0;i<length;i++){
                if(priorityTable[i][length-i-1] == 1){
                    for(int j=0;j<length;j++)
                    priorityTable[j][length-j-1] = table[j][length-j-1];
                    break;
                }
                else if(priorityTable[i][length-i-1] == 0){
                    oCounter++;
                    if(oCounter == 2){
                        for(int k=0;k<length;k++){
                            if((priorityTable[k][length-1-k]!=0)&&(priorityTable[k][length-k-1]!=1)){
                                starposition[0] = k;
                                starposition[1] = length-k-1;
                            }
                        }
                        if(starposition[0]!=-1)
                        return starposition;
                    }
                }
                else if(priorityTable[i][length-i-1] >= priority){
                    priorityTable[i][length-i-1]++;
                }
                else if(priorityTable[i][length-i-1] == -1){
                    priorityTable[i][length-i-1] = priority;
                }
                else{
                    throw new Exception("Critical error invalid value in diagonal filling");
                }
            }
        }
        
        else if((lastRow - lastCol) == 0){
            for(int i=0;i<length;i++){
                if(priorityTable[i][i] == 1){
                    for(int j=0;j<length;j++)
                    priorityTable[j][j] = table[j][j];
                    break;
                }
                else if(priorityTable[i][i] == 0){
                    oCounter++;
                    if(oCounter == 2){
                        for(int k=0;k<length;k++){
                            if((priorityTable[k][k]!=0)&&(priorityTable[k][k]!=1)){
                                starposition[0] = k;
                                starposition[1] = k;
                            }
                        }
                        if(starposition[0]!=-1)
                        return starposition;
                    }
                }
                else if(priorityTable[i][i] >= priority){
                    priorityTable[i][i]++;
                }
                else if(priorityTable[i][i] == -1){
                    priorityTable[i][i] = priority;
                }
                else{
                    throw new Exception("Critical error invalid value in diagonal filling");
                }
            }
        }
        return starposition;
    }
    
    
    public void fillTable(){
        int rows = table.length;
        int cols = table[0].length;
        for(int i=0;i<rows;i++){
            for(int j=0;j<cols;j++){
                priorityTable[i][j] = table[i][j];
            }
        }
    }
}
