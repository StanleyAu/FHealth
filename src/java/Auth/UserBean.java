package Auth;

import java.util.HashMap;
import java.util.Set;

public class UserBean {

    private int uid;
    private String username;
    private String password; // Should clear after login
    private HashMap<String, Integer> roles;
    public boolean valid;
    
    public int getUID() {
        return uid;
    }
    
    public void setUID(int newUid) {
        uid = newUid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String newPassword) {
        password = newPassword;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String newUsername) {
        username = newUsername;
    }
    
    public boolean hasRole(String role) {
        return roles.containsKey(role);
    }
    
    public Set getRoles() {
        return roles.keySet();
    }
    
    public int getRoleId(String role) {
        return roles.get(role);
    }
    
    public void setRole(String role) {
        roles.put(role, -1);
    }
    
    public void setRole(String role, int roleId) {
        roles.put(role, roleId);
    }
    
    public int removeRole(String role, int roleId) {
        return roles.remove(role);
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean newValid) {
        valid = newValid;
    }
}