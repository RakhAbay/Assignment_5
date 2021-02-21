import java.util.List;

public class Project {
    //all fieds are exactly like in the table 'projects' in postgre
    private int projectId;
    private String projectName;
    private int totalCost;
    private int givenHours;
    private ProjectManager projectManager; //this field in postre is 'projectManagerId'

    public Project(){}


    public Project(String projectName, ProjectManager projectManager) {
        this.projectName = projectName;
        this.projectManager = projectManager;
    }
    public ProjectManager getProjectManager() {
        return projectManager;
    }
    public int getProjectManagerId() {
        return projectManager.getId();
    }

    public void setProjectManager(ProjectManager projectManager) {
        this.projectManager = projectManager;
    }
    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }
    public int getProjectId() {
        return projectId;
    }
    public int getTotalCost() {
        return totalCost;
    }
    public void setTotalCost(int totalCost) {
        this.totalCost = totalCost;
    }
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
    public String getProjectName() {
        return projectName;
    }
    public void setGivenHours(int givenHours) {
        this.givenHours = givenHours;
    }
    public int getGivenHours() {
        return givenHours;
    }
}