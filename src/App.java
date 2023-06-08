import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        Scanner s = new Scanner(System.in);
        char twoPlayerTurn = 'X';
        while(true){
            Table table = new Table(s);
            System.out.println("********************************");
            System.out.println("                XO              ");
            System.out.println("                                ");
            System.out.println("            1. 1-Player         ");
            System.out.println("            2. 2-Player         ");
            System.out.println("            3. End              ");
            System.out.println("********************************");
            System.out.print("Enter your choice(1/2/3): ");
            int choice = s.nextInt();
            if(choice == 1){
                table.onePlayerMode();
            }
            else if(choice == 2){
                twoPlayerTurn = table.twoPlayerMode(twoPlayerTurn);
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
}
