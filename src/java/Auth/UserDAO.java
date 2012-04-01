package Auth;

import FHealth.DatabaseFactory;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.sql.PreparedStatement;
import java.sql.CallableStatement;
import java.sql.SQLException;

public class UserDAO {

    public static UserBean login(UserBean bean) {

        int uid;
        String username = bean.getUsername();
        String password = bean.getPassword();
        String salt;// = generateSalt();
        String pw_hash;// = generateSHA(password + salt);

        String searchQuery =
                "SELECT c.id AS id, user, pw_hash, salt "
                + "FROM credential AS c "
                + "WHERE user= ?";

        String roleQuery =
                "SELECT id, user, staff, doctor, patient "
                + "FROM v_user_role "
                + "WHERE user= ?";

        // "System.out.println" prints in the console; Normally used to trace the process
        System.out.println("Your user name is " + username);
        System.out.println("Your password is " + password);
        System.out.println("Query: " + searchQuery);

        try {
            PreparedStatement pms = DatabaseFactory.getInstance().getStatement(searchQuery);
            pms.setString(1, username);
            ArrayList rs = DatabaseFactory.getInstance().query(pms);
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
                    pms = DatabaseFactory.getInstance().getStatement(roleQuery);
                    pms.setString(1, username);
                    rs = DatabaseFactory.getInstance().query(pms);
                    row = (HashMap) rs.get(0);
                    System.out.println("Row: " + row);
                    Integer staff = (Integer) row.get("staff");
                    if (staff != null) {
                        bean.setRole("staff", staff);
                    }
                    Integer doctor = (Integer) row.get("doctor");
                    if (doctor != null) {
                        bean.setRole("doctor", doctor);
                    }
                    Integer patient = (Integer) row.get("patient");
                    if (patient != null) {
                        bean.setRole("patient", patient);
                    }
                    bean.setUID(uid);
                    bean.setValid(true);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        bean.setPassword("foobar");
        return bean;
    }

    public static UserBean register(RegisterBean bean) {
        String addUserQuery =
                "INSERT INTO credential (user, pw_hash, salt) "
                + "VALUES (?, ?, ?)";
        String username = bean.getUsername();
        String salt = Cipher.generateSalt();
        String password = Cipher.generateSHA(bean.getPassword() + salt);

        String getCredentialId =
                "SELECT id FROM credential WHERE user= ?";

        String setRoleId =
                "INSERT INTO credential_role "
                + "VALUES (?, ?)";

        try {
            PreparedStatement credstmt = DatabaseFactory.getInstance().getStatement(addUserQuery);
            credstmt.setString(1, username);
            credstmt.setString(2, password);
            credstmt.setString(3, salt);

            System.out.println(credstmt.toString());
            int result = DatabaseFactory.getInstance().update(credstmt);
            System.out.println("Result: " + result);

            credstmt = DatabaseFactory.getInstance().getStatement(getCredentialId);
            credstmt.setString(1, username);

            System.out.println(credstmt.toString());
            HashMap rs = (HashMap) DatabaseFactory.getInstance().query(credstmt).get(0);
            System.out.println("Result: " + rs);
            int uid = (Integer) rs.get("id");
            int roleId = 1; //DEBUG: STAN

            credstmt = DatabaseFactory.getInstance().getStatement(setRoleId);
            credstmt.setInt(1, uid);
            credstmt.setInt(2, roleId);
            System.out.println(credstmt.toString());
            result = DatabaseFactory.getInstance().update(credstmt);
            System.out.println("Result: " + result);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // If user is type-patient, we must insert additional fields!

        return new UserBean();
    }
}