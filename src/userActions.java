import java.sql.*;
import java.util.Scanner;

public class userActions{
    public void bookTicket(Scanner sc, String email) throws Exception{
        Connection con =DBConnection.getConnection();

        System.out.println("Enter the train number for which you wish the ticket to be booked for: ");
        int trainNumber=sc.nextInt();
        String sql="SELECT COUNT(*) from train_info where train_number=?";
        PreparedStatement checkTrain=con.prepareStatement(sql);

        checkTrain.setInt(1, trainNumber);
        ResultSet rs=checkTrain.executeQuery();
        if(rs.next() && rs.getInt(1) > 0){
            System.out.println("Enter the number of seats you wish to book: ");
            int seatsToBook=sc.nextInt();
            System.out.println("Please select the coach type: ");
            System.out.println("1. Sleeper");
            System.out.println("2. AC");
            int coachType=sc.nextInt();
            String coachTypeString= (coachType==1) ? "Sleeper" : "AC";

            //To get the customer ID based on the email
            String getCustomerIDSql="SELECT cust_id FROM customers WHERE cust_email=?";
            PreparedStatement getCustomerIDStmt=con.prepareStatement(getCustomerIDSql);
            getCustomerIDStmt.setString(1, email);
            ResultSet customerIDResult = getCustomerIDStmt.executeQuery();
            int customerID=0;
            if(customerIDResult.next()){
                customerID=customerIDResult.getInt("cust_id");
            }

            String insert_sql="INSERT INTO tickets(train_number, cust_id, number_of_seats, coach_type) VALUES (?,?,?,?)";
            PreparedStatement bookTicket=con.prepareStatement(insert_sql);
            bookTicket.setInt(1, trainNumber);
            bookTicket.setInt(2, customerID);
            bookTicket.setInt(3, seatsToBook);
            bookTicket.setString(4, coachTypeString);
            bookTicket.executeUpdate();

            System.out.println("Your ticket has been booked successfully.");

            //To update the total number of seats available in the train
            String updateSeatsSql="UPDATE train_info SET total_seats=total_seats-? WHERE train_number=?";
            PreparedStatement seatssql=con.prepareStatement(updateSeatsSql);
            seatssql.setInt(1, seatsToBook);
            seatssql.setInt(2, trainNumber);
            seatssql.executeUpdate();
        }
        else{
            System.out.println("Invalid train number. Please try again.");
        }
        con.close();
    }

    public void viewBookings(String email) throws Exception{
        Connection con = DBConnection.getConnection();
        String sql="SELECT t.ticket_id, t.train_number, t.number_of_seats, t.coach_type FROM tickets t JOIN customers c ON t.cust_id=c.cust_id WHERE c.cust_email=?";
        PreparedStatement viewBookings=con.prepareStatement(sql);
        viewBookings.setString(1, email);
        ResultSet rs=viewBookings.executeQuery();
        System.out.println("Your Bookings:");
        while(rs.next()){
            System.out.println("Ticket ID: " + rs.getInt("ticket_id") + ", Train Number: " + rs.getInt("train_number") + ", Seats Booked: " + rs.getInt("number_of_seats") + ", Coach Type: " + rs.getString("coach_type"));
        }
        con.close();
    }
    public void cancelBooking(Scanner sc, String email) throws Exception{
        Connection con = DBConnection.getConnection();
        System.out.println("Enter the ticket ID you wish to cancel: ");
        int ticketID=sc.nextInt();

        //To get the customer ID based on the email
        String getCustomerIDSql="SELECT cust_id FROM customers WHERE cust_email=?";
        PreparedStatement getCustomerIDStmt=con.prepareStatement(getCustomerIDSql);
        getCustomerIDStmt.setString(1, email);
        ResultSet customerIDResult = getCustomerIDStmt.executeQuery();
        int customerID=0;
        if(customerIDResult.next()){
            customerID=customerIDResult.getInt("cust_id");
        }

        String sql="DELETE FROM tickets WHERE ticket_id=? AND cust_id=?";
        PreparedStatement cancelBooking=con.prepareStatement(sql);
        cancelBooking.setInt(1, ticketID);
        cancelBooking.setInt(2, customerID);
        int rowsAffected=cancelBooking.executeUpdate();
        if(rowsAffected>0){
            System.out.println("Your booking has been cancelled successfully.");
        }
        else{
            System.out.println("Invalid ticket ID or you do not have permission to cancel this booking. Please try again.");
        }
        con.close();
    }
}