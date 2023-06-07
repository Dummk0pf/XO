import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        Scanner s = new Scanner(System.in);
        while(true){
            System.out.println("********************************");
            System.out.println("                XO              ");
            System.out.println("                                ");
            System.out.println("            1. 1-Player         ");
            System.out.println("            2. 2-Player         ");
            System.out.println("            3. End              ");
            System.out.println("********************************");
            System.out.println("Enter your choice(1/2/3):       ");
            int choice = s.nextInt();
            if(choice == 1){
                //TODO
            }
            else if(choice == 2){

            }
            else if(choice == 3){
                break;
            }
            else{
                System.out.println("Invalid choice... :(");
            }
        }
        s.close();
    }


    public static void twoPlayerMode(){
        Scanner s = new Scanner(System.in);
        int[][] table = {{-1,-1,-1},{-1,-1,-1},{-1,-1,-1}};
        boolean winFlag = false;
        //-1 indicates that the position is empty
        // 0 indicates that the position is a O
        // 1 indicates that the position is a X
        displayTable(table);

        while(!winFlag){
            System.out.print("Enter the position for marking 'X'(row column): ");
            int rowPos = s.nextInt();
            int colPos = s.nextInt();

        }

        s.close();
    }


    public static void displayTable(int[][] table){
        int rows = table.length;
        int columns = table[0].length;
        for(int i=0; i<rows; i++){
            for(int j=0; j<columns ;j++){
                char symbol = (table[i][j] == 0)?'O':((table[i][j] == 1)?'X':' ');
                System.out.print(symbol+'|');
            }
            System.out.println();
        }
    }

    public static boolean checkPosition(int[][] table,int lastRow,int lastCol){
        boolean result = false;
        
        return result;
    }
}
