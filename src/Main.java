import java.util.Scanner;
public class Main{
    public static void main(String[] args) throws Exception{
        Scanner sc = new Scanner(System.in);
        ReservationService reservationService = new ReservationService();
        TrainManagement trainManagement = new TrainManagement();

        while(true){
            System.out.println("Welcome to the Train Reservation System");
            System.out.println("1. Admin Login");
            System.out.println("2. User Registration");
            System.out.println("3. User Login");
            System.out.println("4. Search Trains");
            System.out.println("5. Exit");
            System.out.print("Please select an option: ");
            int choice = sc.nextInt();

            switch(choice){
                case 1:
                    reservationService.adminLogin(sc);
                    break;
                case 2:
                    reservationService.newUser(sc);
                    break;
                case 3:
                    reservationService.userLogin(sc);
                    break;
                case 4:
                    reservationService.searchTrains(sc);
                    break;
                case 5:
                    System.out.println("Thank you for using the Train Reservation System. Goodbye!");
                    sc.close();
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}