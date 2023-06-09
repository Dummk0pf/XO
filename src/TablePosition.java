public interface TablePosition {
    static int[][] table = new int[3][3];
    static int[] moveCounter = new int[1];
    static int[][] xPosition = new int[9][2]; 
    static int[][] oPosition = new int[9][2];
}
//under current implementation xPosition and oPosition is also updated in the two player mode which is unnecessary
//after finishing first draft try to bypass the waste of memory
