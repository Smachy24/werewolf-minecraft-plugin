package werewolf.plugin.minecraft.roles;

public class Role {
    private String name;
    private String team;
    private String frenchName;

    private String description;

    public Role() {
    }

    public Role(String name, String team, String frenchName, String description) {
        this.name = name;
        this.team = team;
        this.frenchName = frenchName;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getTeam() {
        return team;
    }

    public String getFrenchName() {
        return frenchName;
    }

    public void setFrenchName(String frenchName) {
        this.frenchName = frenchName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
