package hospitalmgnt;
import java.sql.*;
import java.util.*;

public class Patient {
    private final Connection connection;
    private  Scanner scanner;
    Patient(Connection connection, Scanner scanner){
        this.connection=connection;
        this.scanner=scanner;
    }
    public void addpatient(){
        System.out.println("Enter patient Name:- ");
        String name = scanner.nextLine();
        System.out.println("Enter patient age:- ");
        int age=scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter patient gender:- ");
        String gender = scanner.next();
        try{
            String q="INSERT INTO patients(name,age,gender) VALUES(?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(q);
            ps.setString(1,name);
            ps.setInt(2,age);
            ps.setString(3,gender);
            int affectedrows=ps.executeUpdate();
            if(affectedrows>0){
                System.out.println("Added successfully...");
            }
            else {
                System.out.println("Failed...!");
            }


        }
        catch (SQLException e){
            e.printStackTrace();
        }

    }

    public void viewPatients() {
        try {
            String q = "SELECT * FROM patients";
            PreparedStatement ps = connection.prepareStatement(q);
            ResultSet resultSet = ps.executeQuery();

            System.out.println("Patients:  ");
            System.out.println("+----------------+-------------------+--------+-------+");
            System.out.println("| patient ID     | Name               | Age    |Gender|");
            System.out.println("+----------------+-------------------+--------+-------+");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                String gender = resultSet.getString("gender");

                // Use printf() for formatting output
                System.out.printf("|%-16d|%-20s|%-8d|%-7s|%n", id, name, age, gender);
            }
            System.out.println("+----------------+-------------------+--------+-------+");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public boolean getPatientById(int id){
        String q="SELECT * FROM patients WHERE id = ?";
        try{
            PreparedStatement ps = connection.prepareStatement(q);
            ps.setInt(1,id);
            ResultSet rs =ps.executeQuery();
            if(rs.next()){
               return true;
            }else {
                return false;
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return false;

    }






}
