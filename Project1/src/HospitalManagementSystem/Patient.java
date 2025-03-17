package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Patient {

    private Connection connection;
    private Scanner scanner;

    public Patient(Connection connection, Scanner scanner) {
        this.connection = connection;
        this.scanner = scanner;
    }

    public void addPatient() {
        System.out.print("Enter Patient Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Age: ");
        int age = scanner.nextInt();
        scanner.nextLine();  // Consume the newline character
        System.out.print("Enter Address: ");
        String address = scanner.nextLine();

        String query = "INSERT INTO patients (name, age, address) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, age);
            preparedStatement.setString(3, address);
            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println(rowsAffected > 0 ? "Patient Added Successfully!" : "Failed to Add Patient.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void viewPatients() {
        String query = "SELECT * FROM patients";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            System.out.println("Patient ID | Name         | Age | Address");
            while (resultSet.next()) {
                int patientId = resultSet.getInt("patient_id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                String address = resultSet.getString("address");
                System.out.println(patientId + "         | " + name + "    | " + age + " | " + address);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean getPatientById(int patientId) {
        String query = "SELECT * FROM patients WHERE patient_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, patientId);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next(); // Returns true if patient exists, false otherwise
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
