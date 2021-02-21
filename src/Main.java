import java.sql.*;
import java.util.Scanner;

public class Main {




    private static Project project = new Project();
    private static Employee[] employees = new Employee[30];
    private static ProjectManager projectManager = null;





    public static Connection con;
    static {
        try {
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "kuka");
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }


    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Scanner sc = new Scanner(System.in);



        System.out.println("Welcome to an employee-gathering-to-a-project(also calculating total cost) app!");

        System.out.println("Project's name:");
        project.setProjectName(sc.next());


        System.out.println("Choose project's manager by id:");
        {
            PreparedStatement pst = con.prepareStatement("select * from employees where position = 'project manager'");
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                System.out.println(rs.getInt("id") + " " + rs.getString("name") + " " + rs.getString("surname") + " dollars per hour: " + rs.getInt("incomePerHour"));
            }
        }

        {
            int id = sc.nextInt();
            PreparedStatement pst = con.prepareStatement("select * from employees where id = "+id);
            ResultSet rs = pst.executeQuery();
            rs.next();
            projectManager = new ProjectManager(rs.getInt("id"), rs.getString("name"), rs.getString("surname"), rs.getInt("incomePerHour"));
            project.setProjectManager(projectManager);
        }




        System.out.println("Hours allocated to the project:");
        project.setGivenHours(sc.nextInt());


        System.out.println("Choose employees by id and/or press '0' to finish");
        {
            PreparedStatement pst = con.prepareStatement("select * from employees where position != 'project manager'");
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                System.out.println(rs.getInt("id") + " " + rs.getString("name") + " " + rs.getString("surname")+" "+rs.getString("position") + " dollars per hour: " + rs.getInt("incomePerHour"));
            }
        }
        int counter = 0;
        while (true) {
            int id = sc.nextInt();
            if (id == 0) {break;}
            PreparedStatement pst = con.prepareStatement("select * from employees where id ="+id);
            ResultSet rs = pst.executeQuery();
            rs.next();
            employees[counter] = new Employee(rs.getInt("id"), rs.getString("name"), rs.getString("surname"), rs.getString("position"), rs.getInt("incomePerHour"));
            counter++;
        }



        int cost = 0;
        for (int i = 0; i < counter; i++) {
            cost += employees[i].getIncomePerHour();
        }
        cost += projectManager.getIncomePerHour();
        cost *= project.getGivenHours();
        project.setGivenHours(cost);
        System.out.println("Total cost of the project is "+cost);
    }



}
