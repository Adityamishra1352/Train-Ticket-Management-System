import java.sql.*;
import java.util.Scanner;

public class TrainManagement{
    public void addTrain(Scanner sc) throws Exception{
        Connection conn= DBConnection.getConnection();

        System.out.println("Enter the train name: ");
        String trainName=sc.next();

        System.out.println("Enter the soucre station: ");
        String trainSource=sc.next();

        System.out.println("Enter the destination station: ");
        String trainDestination=sc.next();

        System.out.println("Enter the time of departure: ");
        String trainDepartureTime=sc.next();

        System.out.println("Enter the time of arrival: ");
        String trainArrivalTime=sc.next();

        System.out.println("Enter the total number of seats: ");
        int trainTotalSeats=sc.nextInt();

        String sql="INSERT INTO train_info(train_name, source_station, destination_station, time_of_departure, time_of_arrival, total_seats) VALUES (?,?,?,?,?,?)";
        PreparedStatement addTrain= conn.prepareStatement(sql);
        addTrain.setString(1, trainName);
        addTrain.setString(2, trainSource);
        addTrain.setString(3, trainDestination);
        addTrain.setString(4, trainDepartureTime);
        addTrain.setString(5, trainArrivalTime);
        addTrain.setInt(6, trainTotalSeats);

        int rowsAffected = addTrain.executeUpdate();
        if(rowsAffected > 0){
            System.out.println("Train added successfully.");
        }
        else{
            System.out.println("Failed to add train. Please try again.");
        }
        conn.close();
    }
    public void updateTrain(Scanner sc) throws Exception{
        Connection conn = DBConnection.getConnection();
        System.out.println("Enter the trainn ID you wish to update: ");
        int trainID=sc.nextInt();
        
        String sql="SELECT COUNT(*) FROM train_info WHERE train_number=?";
        PreparedStatement checkTrain=conn.prepareStatement(sql);
        checkTrain.setInt(1, trainID);
        ResultSet rs = checkTrain.executeQuery();
        if(rs.next() && rs.getInt(1) > 0){
            System.out.println("Enter the new train name: ");
            String trainName=sc.next();

            System.out.println("Enter the new source station: ");
            String trainSource=sc.next();

            System.out.println("Enter the new destination station: ");
            String trainDestination=sc.next();

            System.out.println("Enter the new time of departure: ");
            String trainDepartureTime=sc.next();

            System.out.println("Enter the new time of arrival: ");
            String trainArrivalTime=sc.next();

            System.out.println("Enter the new total number of seats: ");
            int trainTotalSeats=sc.nextInt();

            sql="UPDATE train_info SET train_name=?, source_station=?, destination_station=?, time_of_departure=?, time_of_arrival=?, total_seats=? WHERE train_number=?";
            PreparedStatement updateTrain= conn.prepareStatement(sql);
            updateTrain.setString(1, trainName);
            updateTrain.setString(2, trainSource);
            updateTrain.setString(3, trainDestination);
            updateTrain.setString(4, trainDepartureTime);
            updateTrain.setString(5, trainArrivalTime);
            updateTrain.setInt(6, trainTotalSeats);
            updateTrain.setInt(7, trainID);

            int rowsAffected = updateTrain.executeUpdate();
            if(rowsAffected > 0){
                System.out.println("Train updated successfully.");
            }
            else{
                System.out.println("Failed to update train. Please try again.");
            }
        }
        else{
            System.out.println("Train with the given ID does not exist. Please try again.");
        }
        conn.close();
    }
    public void deleteTrain(Scanner sc) throws Exception{
        Connection conn= DBConnection.getConnection();
        System.out.println("Enter the train ID you wish to delete: ");
        int trainID=sc.nextInt();
        String sql="Select * from train_info where train_number=?";
        PreparedStatement checkTrain=conn.prepareStatement(sql);
        checkTrain.setInt(1, trainID);
        int rs=checkTrain.executeUpdate();

        if(rs >0){
            sql="UPDATE train_info SET del_flag='Y' WHERE train_number=?";
            PreparedStatement deleteTrain=conn.prepareStatement(sql);
            deleteTrain.setInt(1, trainID);
            int rowsAffected=deleteTrain.executeUpdate();
            if(rowsAffected>0){
                System.out.println("Train deleted successfully.");
            }
            else{
                System.out.println("Failed to delete the train.");
            }
        }
        else{
            System.out.println("Train with this ID doesn't exist");
        }
        conn.close();
    }
}