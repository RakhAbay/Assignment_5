public class Employee {


    //all fieds are exactly like in the table 'projects' in postgre
    private int id;
    private String name;
    private String surname;
    private String position;
    private int incomePerHour;


    //Constructor (not used in the Main class)
    public Employee(){}
    public Employee(int id, String name, String surname, String position, int incomePerHour) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.position = position;
        this.incomePerHour = incomePerHour;
    }


    //Setters and Getters
    public  int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getSurname() {
        return surname;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }
    public int getIncomePerHour() {
        return incomePerHour;
    }
    public void setIncomePerHour(int incomePerHour) {
        this.incomePerHour = incomePerHour;
    }
    public String getPosition() {
        return position;
    }
    public void setPosition(String position) {
        this.position = position;
    }
}
