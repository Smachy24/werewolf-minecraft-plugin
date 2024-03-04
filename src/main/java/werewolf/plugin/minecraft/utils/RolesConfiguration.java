package werewolf.plugin.minecraft.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import werewolf.plugin.minecraft.Main;
import werewolf.plugin.minecraft.roles.Role;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class RolesConfiguration {
    private static final String ROLES_FILE = "/roles-list.yml";
    private List<Role> configRoles = new ArrayList<>();

    public List<Role> getConfigRoles() {
        return configRoles;
    }

    public void setConfigRoles(List<Role> configRoles) {
        this.configRoles = configRoles;
    }


    public RolesConfiguration() {
        getFileRoles();
    }

    private void getFileRoles() {
            try (InputStream inputStream = Main.class.getResourceAsStream(ROLES_FILE)) {
                ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
                List<Role> roles = mapper.readValue(inputStream, mapper.getTypeFactory().constructCollectionType(List.class, Role.class));
                for (Role role : roles) {
                    createEachRole(role);
                }
            } catch (IOException e) {
                throw new RuntimeException("Erreur lors de la lecture du fichier de rôles", e);
            }
        }

    private void createEachRole(Role role) {
        try {
            String className = "werewolf.plugin.minecraft.roles." + role.getName();
            Class<?> roleClass = Class.forName(className);
            Role instantiatedRole = (Role) roleClass.getDeclaredConstructor().newInstance();
            configRoles.add(instantiatedRole);
        } catch (Exception e) {
            throw new RuntimeException("Error creating role: " + role.getName(), e);
        }
    }


}
