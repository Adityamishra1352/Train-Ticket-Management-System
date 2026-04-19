import java.sql.*;
import java.util.Scanner;

public class ReservationService{
    public void newUser(Scanner sc) throws Exception{
        Connection conn = DBConnection.getConnection();

        System.out.println("Enter your email address:");
        String email=sc.next();

        System.out.println("Enter password of your choice");
        String password=sc.next();

        System.out.println("Enter your password again");
        String confirmPassword=sc.next();

        if(password.equals(confirmPassword)){
            System.out.println("You have been registered");
            System.out.println("Enter your name");
            String name = sc.next();

            System.out.println("Enter your date of birth");
            String dob=sc.next();

            System.out.println("Enter your mobile number");
            int mobileNumber =sc.nextInt();

            String sql="INSERT INTO customers(cust_name, cust_dob, cust_mobile_number, cust_email, cust_password) VALUES (?,?,?,?,?)";
            PreparedStatement insertUser = conn.prepareStatement(sql);
            insertUser.setString(1,name);
            insertUser.setString(2,dob);
            insertUser.setInt(3,mobileNumber);
            insertUser.setString(4,email);
            insertUser.setString(5,password);
            insertUser.executeUpdate();

            System.out.println("Your details have been updated successfully.");
        }
        else{
            System.out.println("Passwords do not match. Please try again.");
        }
        conn.close();
    }
    public void adminLogin(Scanner sc) throws Exception{
        TrainManagement TrainManagement = new TrainManagement();
        Connection conn = DBConnection.getConnection();
        System.out.println("Enter your email address :");
        String email=sc.next();

        System.out.println("Enter your password: ");
        String password=sc.next();

        String sql="SELECT COUNT(*) FROM customers WHERE cust_email=? and password=? and admin='Y'";
        PreparedStatement adminLogin = conn.prepareStatement(sql);
        adminLogin.setString(1, email);
        adminLogin.setString(2, password);
        ResultSet rs = adminLogin.executeQuery();
        if(rs.next() && rs.getInt(1) > 0){
            System.out.println("Admin login successful.");
            while(true){
            System.out.println("\n---Train Management---");
            System.out.println("1. Add Train");
            System.out.println("2. Update Train information");
            System.out.println("Delete train information");
            int choice=sc.nextInt();
            switch(choice) 
            {
                case 1->TrainManagement.addTrain(sc);
                case 2->TrainManagement.updateTrain(sc);
                case 3->TrainManagement.deleteTrain(sc);
                case 4-> {
                    System.out.println("Exiting admin panel");
                    return;
                }
                default-> System.out.println("Invalid choice");
            }
        }
        }
        else{
            System.out.println("Invalid email or password. Please try again.");
        }
    }
    public void searchTrains(Scanner sc) throws Exception{
        Connection conn = DBConnection.getConnection();

        System.out.println("Enter the source station:");
        String source = sc.next();

        System.out.println("Enter the destination station");
        String destination = sc.next();

        String sql="SELECT * FROM train_info WHERE source_station=? and destination_station=?";
        PreparedStatement searchTrains = conn.prepareStatement(sql);
        searchTrains.setString(1,source);
        searchTrains.setString(2,destination);
        ResultSet rs = searchTrains.executeQuery();
        if(rs.next()){
            System.out.println("Train Name: "+rs.getString("train_name"));
            System.out.println("Train Number: "+rs.getInt("train_number"));
            System.out.println("Source Station: "+rs.getString("source_station"));
            System.out.println("Destination Station: "+rs.getString("destination_station"));
            System.out.println("Departure Time: "+rs.getString("time_of_departure"));
            System.out.println("Arrival Time: "+rs.getString("time_of_arrival"));
            System.out.println("Total Seats: "+rs.getInt("total_seats"));
        }
        else{
            System.out.println("No trains found for the given source and destination.");
        }
        conn.close();
    }
}