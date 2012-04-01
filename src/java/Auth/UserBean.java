package Auth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.Collection;
import Util.WebUtil;

public class UserBean {
    public static ArrayList role_actions = WebUtil.gson.fromJson(
            "["
            + "['admin', ["
                + "['register user', 'register']"
            + "]],"
            + "['finance',["
                + "['doctor summary', 'finance']"
            + "]],"
            + "['doctor',["
                + "['search patient', 'Doctor?doctor_id=$(uid)'],"
                + "['search appointment', 'Doctor?doctor_id=$(uid)']"
            + "]],"
            + "['staff',["
                + "['update patient', ''],"
                + "['book appointment', '']"
            + "]],"
            + "['patient',["
                + "['update profile',''],"
                + "['view appointment', '']"
            + "]]"
            + "]"
            , ArrayList.class);
    
    private int uid;
    private String username;
    private String password; // Should clear after login
    private HashMap<String, Integer> roles;
    public boolean valid;
    
    public UserBean() {
        roles = new HashMap();
    }
    
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
    
    // Only useful during registration
    // IDs are supposed to be id in role records
    // But for registration, it will be id of role
    public Collection<Integer> getRoleIDs() {
        return roles.values();
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
    
    public ArrayList menuItems(){
        ArrayList menuItems = new ArrayList();
        for (int i = 0; i < role_actions.size(); i++){
            String role = (String)
                    (((ArrayList)role_actions.get(i)).get(0));
            if (hasRole(role)){
                menuItems.add(
                        ((ArrayList)role_actions.get(i)).clone());
            }
        }
        return menuItems;
    }
    
    
}