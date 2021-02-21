import java.util.List;

public class Project {

    private String projectName;
    private int totalCost;
    private int givenHours;
    private int counter = 0;

    private ProjectManager projectManager;

    public Project(){}

    public void setProjectManager(ProjectManager projectManager) {
        this.projectManager = projectManager;
    }

    public Project(String projectName, ProjectManager projectManager) {
        this.projectName = projectName;
        this.projectManager = projectManager;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
    public void setGivenHours(int givenHours) {
        this.givenHours = givenHours;
    }
    public int getGivenHours() {
        return givenHours;
    }
}


//List<SoftwareEngineer> softwareEngineers