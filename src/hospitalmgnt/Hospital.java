package hospitalmgnt;

import java.sql.*;
import java.util.Scanner;

public class Hospital {
    //"jdbc:mysql://localhost:3306/bankmgm", "root", ""
    private static final String url="jdbc:mysql://localhost:3306/hospital";
    private static final String uname="root";
    private static final String passwd="";

    public static void main(String[] args) {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");


        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            Scanner sc = new Scanner(System.in);
            Connection conn = DriverManager.getConnection(url, uname, passwd);
            Patient patient = new Patient(conn, sc);
            Docter docter = new Docter(conn);
            while (true) {
                System.out.println("HOSPITAL MANAGEMENT SYSTEM ");
                System.out.println("1. Add Patient");
                System.out.println("2. View Patients");
                System.out.println("3. View Doctors");
                System.out.println("4. Book Appointment");
                System.out.println("5. Exit");
                System.out.println("Enter your choice: ");
                int choice = sc.nextInt();

                switch (choice) {
                    case 1:
                        System.out.println();
                        // Add Patient
                        patient.addpatient();

                        break;
                    case 2:
                        // View Patient
                        patient.viewPatients();
                        System.out.println();
                        break;
                    case 3:
                        // View Doctors
                        docter.viewDoctor();
                        System.out.println();
                        break;
                    case 4:
                        // Book Appointment
                        bookAppointment(patient, docter, conn, sc);
                        System.out.println();
                        break;
                    case 5:
                        System.out.println("THANK YOU! FOR USING HOSPITAL MANAGEMENT SYSTEM!!");
                        return;
                    default:
                        System.out.println("Enter valid choice!!!");
                        break;
                }
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public static void bookAppointment(Patient patient,Docter doctor,Connection connection,Scanner scanner){
        System.out.println("Enter patient id:- ");
        int pID=scanner.nextInt();
        System.out.println("Enter Doctor id:-  ");
        int dID=scanner.nextInt();
        System.out.print("Enter appointment date (YYYY-MM-DD): ");
        String appointmentDate = scanner.next();
            if(patient.getPatientById(pID)&&doctor.getDoctorById(dID)){
                if(checkDoctorAvailability(dID, appointmentDate, connection)) {
                String q = "INSERT INTO appointments(patient_id, doctor_id, appointment_date) VALUES(?, ?, ?)";
                    try {
                        PreparedStatement preparedStatement = connection.prepareStatement(q);
                        preparedStatement.setInt(1, pID);
                        preparedStatement.setInt(2, dID);
                        preparedStatement.setString(3, appointmentDate);
                        int rowsAffected = preparedStatement.executeUpdate();
                        if(rowsAffected>0){
                            System.out.println("Appointment Booked!");
                        }else{
                            System.out.println("Failed to Book Appointment!");
                        }
                    }catch (SQLException e){
                        e.printStackTrace();
                    }
                }
                else {
                    System.out.println("Doctor not available...!");
                }
            }
                else {
                    System.out.println("Check Doctor id and patient id ...!");
                }
    }

    public static boolean checkDoctorAvailability(int doctorId, String appointmentDate, Connection connection){
        String query = "SELECT COUNT(*) FROM appointments WHERE doctor_id = ? AND appointment_date = ?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, doctorId);
            preparedStatement.setString(2, appointmentDate);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                int count = resultSet.getInt(1);
                if(count==0){
                    return true;
                }else{
                    return false;
                }
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

}
