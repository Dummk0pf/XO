import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        Scanner s = new Scanner(System.in);
        char onePlayerTurn;
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
                System.out.print("Do you want to Move First (X) or Second (O): ");
                onePlayerTurn = s.next().charAt(0);
                onePlayerTurn = Character.toUpperCase(onePlayerTurn);
                while((onePlayerTurn != 'X')&&(onePlayerTurn != 'O')){
                    System.out.println("Enter a valid character between (X) and (O)");
                }
                table.onePlayerMode(onePlayerTurn);
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
