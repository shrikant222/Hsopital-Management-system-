package hospitalmgnt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Docter {
    private Connection connection;
    Docter(Connection connection){
        this.connection=connection;
    }

    public void viewDoctor() {
        try {
            String q = "SELECT * FROM doctors";
            PreparedStatement ps = connection.prepareStatement(q);
            ResultSet resultSet = ps.executeQuery();

            System.out.println("Doctor:  ");
            System.out.println("+----------------+-------------------+--------+--------+");
            System.out.println("| Doctor ID     | Name                 | Specialization|");
            System.out.println("+----------------+-------------------+--------+--------+");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String Specialization=resultSet.getString("Specialization");
                // Use printf() for formatting output
                System.out.printf("|%-16d|%-20s|%-15s|\n", id, name,Specialization );
            }
            System.out.println("+----------------+-------------------+--------+--------+");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public boolean getDoctorById(int id){
        String q="SELECT * FROM doctors WHERE id = ?";
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
