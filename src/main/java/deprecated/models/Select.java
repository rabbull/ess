package deprecated.models;


public class Select {
    private Project project;

    private Expert expert;

    public Select (Project project, Expert expert) {
        this.project = project;
        this.expert = expert;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Expert getExpert() {
        return expert;
    }

    public void setExpert(Expert expert) {
        this.expert = expert;
    }

    public String[] toStringArray() {
        return new String[]{project.getCode(), expert.getName()};
    }
}
