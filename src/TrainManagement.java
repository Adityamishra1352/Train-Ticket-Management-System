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

        String sql="INSERT INTO train_info(train_name, source_station, destination_station, time_of departure, time_of_arrival, total_seats) VALUES (?,?,?,?,?,?)";
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
}