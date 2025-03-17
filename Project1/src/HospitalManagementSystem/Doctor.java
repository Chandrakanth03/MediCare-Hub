package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Doctor {

    private Connection connection;
    private Scanner scanner;

    public Doctor(Connection connection, Scanner scanner) {
        this.connection = connection;
        this.scanner = scanner;
    }

    public void viewDoctors() {
        String query = "SELECT * FROM doctors";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            System.out.println("Doctor ID | Name         | Specialty");
            while (resultSet.next()) {
                int doctorId = resultSet.getInt("doctor_id");
                String name = resultSet.getString("name");
                String specialty = resultSet.getString("specialty");
                System.out.println(doctorId + "         | " + name + "    | " + specialty);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean getDoctorById(int doctorId) {
        String query = "SELECT * FROM doctors WHERE doctor_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, doctorId);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next(); // Returns true if doctor exists, false otherwise
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
