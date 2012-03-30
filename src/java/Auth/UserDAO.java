package Auth;

import FHealth.DatabaseFactory;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class UserDAO {

    public static UserBean login(UserBean bean)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {

        int uid;
        String username = bean.getUsername();
        String password = bean.getPassword();
        String salt;// = generateSalt();
        String pw_hash;// = generateSHA(password + salt);

        String searchQuery =
                "SELECT c.id AS id, user, pw_hash, salt"
                + "FROM credential AS c"
                + "WHERE user='"
                + username
                + "'";

        String roleQuery =
                "SELECT id, user, staff, doctor, patient"
                + "FROM v_user_role"
                + "WHERE user='"
                + username
                + "'";

        // "System.out.println" prints in the console; Normally used to trace the process
        System.out.println("Your user name is " + username);
        System.out.println("Your password is " + password);
        System.out.println("Query: " + searchQuery);

        try {
            ArrayList rs = DatabaseFactory.getInstance().query(searchQuery);
            if (rs.size() == 0) {
                System.out.println("Sorry, you are not a registered user! Please sign up first");
                bean.setValid(false);
            } //if user exists set the isValid variable to true
            else {
                HashMap row = (HashMap) rs.get(0);
                uid = (Integer) row.get("id");
                pw_hash = (String) row.get("pw_hash");
                salt = (String) row.get("salt");
                if (Cipher.generateSHA(password + salt).equals(pw_hash)) {
                    // Get roles and set to bean
                    rs = DatabaseFactory.getInstance().query(roleQuery);
                    row = (HashMap) rs.get(0);
                    Iterator<String> iter = row.keySet().iterator();
                    while (iter.hasNext()) {
                        String role = iter.next();
                        Integer role_id = (Integer)row.get(role);
                        if (role_id != null) {
                            bean.setRole(role, role_id);
                        }
                    }
                    bean.setUID(uid);
                    bean.setValid(true);
                }
            }
        } catch (Exception ex) {
            System.out.println("Log In failed: An Exception has occurred! " + ex);
        }
        bean.setPassword("foobar");
        return bean;
    }

}