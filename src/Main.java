import java.sql.*;
import java.util.Scanner;

public class Main {

    private static Project project = new Project();
    private static Employee[] employees = new Employee[30];
    private static ProjectManager projectManager = null;


    public static Connection con; // connercting to the database
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

        // sjdkhsjhshfksdjhfjksdhfkjsdhfsjdk
        System.out.println("Welcome to an employee-gathering-to-a-project(also calculating total cost) app!");

        System.out.println("Project's name:"); //setting name of the project
        project.setProjectName(sc.next());
        System.out.println("Project's id:");
        project.setProjectId(sc.nextInt());

        System.out.println("Choose project's manager by id:");
        { //this part shows all project managers to choose from
            PreparedStatement pst = con.prepareStatement("select * from employees where position = 'project manager'");
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                System.out.println(rs.getInt("id") + " " + rs.getString("name") + " " + rs.getString("surname") + " dollars per hour: " + rs.getInt("incomePerHour"));
            }//shows all project managers from the database
        }

        {//in this part id of a needed manager is input
            int id = sc.nextInt();
            PreparedStatement pst = con.prepareStatement("select * from employees where id = " + id);
            ResultSet rs = pst.executeQuery();
            rs.next();
            projectManager = new ProjectManager(rs.getInt("id"), rs.getString("name"), rs.getString("surname"), rs.getInt("incomePerHour")); // initializing the project manager with the data from database
            project.setProjectManager(projectManager); //setting the manager of the projects
        }


        System.out.println("Hours allocated to the project:");
        project.setGivenHours(sc.nextInt()); //number of hours that will be dedicated to the project

        //here are the prepatations to enter the project into the table 'projects' to be saved
        PreparedStatement pst = con.prepareStatement("insert into projects (projectId, projectName, totalCost, givenHours, projectManagerId) values (?, ?, ?, ?, ?)");
        pst.setInt(1, project.getProjectId());
        pst.setString(2, project.getProjectName());
        pst.setInt(4, project.getGivenHours());
        pst.setInt(5, project.getProjectManagerId());


        System.out.println("Choose employees by id and/or press '0' to finish");
        {//this part shows all employees with different positions and incomePerHour to choose from
            PreparedStatement st = con.prepareStatement("select * from employees where position != 'project manager'");
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                System.out.println(rs.getInt("id") + " " + rs.getString("name") + " " + rs.getString("surname") + " " + rs.getString("position") + " dollars per hour: " + rs.getInt("incomePerHour"));
            }
        }
        int counter = 0;
        while (true) { //if 0 is not pressed one can continue to select needed employees for the project
            int id = sc.nextInt();
            if (id == 0) {
                break;
            }
            PreparedStatement pst2 = con.prepareStatement("select * from employees where id =" + id);
            ResultSet rs = pst2.executeQuery();
            rs.next();
            employees[counter] = new Employee(rs.getInt("id"), rs.getString("name"), rs.getString("surname"), rs.getString("position"), rs.getInt("incomePerHour"));
            counter++;
        }

        int cost = 0;
        for (int i = 0; i < counter; i++) {
            cost += employees[i].getIncomePerHour();
        }
        cost += projectManager.getIncomePerHour();   //calculating total spendings on manager and employees, considering their incomePerHour and hours for the project, to carry out the project
        cost *= project.getGivenHours();
        project.setTotalCost(cost);
        pst.setInt(3, project.getTotalCost()); //setting last parameter(totalCost) of preparedStatement
        pst.executeUpdate(); //finally inserting project into the table 'projects'


        PreparedStatement preparedStatement = con.prepareStatement("insert into employeesToProjects(projectId, employeeId) values (?, ?)"); //employeesToProjects is a bridge table that connects projects and employees that are participating in it
        preparedStatement.setInt(1, project.getProjectId()); //setting project's id
        for (int i = 0; i < counter; i++) {
            preparedStatement.setInt(2, employees[i].getId());
            preparedStatement.executeUpdate();
        } //in this loop inserting employees and corresponding to it projectId into the table 'employeesToProjects' thus saving to the database

        System.out.println("========================");
        System.out.println("Total cost of the project is " + project.getTotalCost()); //showing total spendings of the project on all emplouyees and manager

    }
}