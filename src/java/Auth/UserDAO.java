package Auth;

import FHealth.DatabaseFactory;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Collection;
import java.util.Set;
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
                "SELECT id, user, admin, finance, "
                + "staff, doctor, patient "
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
                    Integer admin = ((Long)row.get("admin")).intValue();
                    if (admin != 0) {
                        bean.setRole("admin", admin);
                    }
                    Integer finance = ((Long)row.get("finance")).intValue();
                    if (finance != 0) {
                        bean.setRole("finance", finance);
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

        String crIdQuery =
                "SELECT id FROM credential WHERE user= ?";

        String credRoleQuery =
                "INSERT INTO credential_role "
                + "VALUES (?, ?)";

        String roleQuery =
                "INSERT INTO %s (first_name, last_name, credential_id)"
                + "VALUES (?, ?, ?)";

        String patientQuery =
                "INSERT INTO patient (first_name, last_name, OHIP, SIN, "
                + "address, phone, credential_id)"
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement credstmt = DatabaseFactory.getInstance().getStatement(addUserQuery);
            credstmt.setString(1, username);
            credstmt.setString(2, password);
            credstmt.setString(3, salt);

            System.out.println(credstmt.toString());
            int result = DatabaseFactory.getInstance().update(credstmt);
            System.out.println("Result: " + result);

            credstmt = DatabaseFactory.getInstance().getStatement(crIdQuery);
            credstmt.setString(1, username);

            System.out.println(credstmt.toString());
            HashMap rs = (HashMap) DatabaseFactory.getInstance().query(credstmt).get(0);
            System.out.println("Result: " + rs);
            int uid = (Integer) rs.get("id");

            Collection<Integer> roleIds = bean.getRoleIDs();
            Iterator<Integer> roleIdIter = roleIds.iterator();
            while (roleIdIter.hasNext()) {
                credstmt = DatabaseFactory.getInstance().getStatement(credRoleQuery);
                credstmt.setInt(1, uid);
                credstmt.setInt(2, roleIdIter.next());
                System.out.println(credstmt.toString());
                credstmt.addBatch();
            }
            result = DatabaseFactory.getInstance().update(credstmt, true);
            System.out.println("Add user roles: " + result);

            if (bean.hasRole("patient")) {
                credstmt = DatabaseFactory.getInstance().getStatement(patientQuery);
                credstmt.setString(1, bean.getFirstName());
                credstmt.setString(2, bean.getLastName());
                credstmt.setString(3, bean.getOhip());
                credstmt.setString(4, bean.getSin());
                credstmt.setString(5, bean.getAddress());
                credstmt.setString(6, bean.getPhone());
                credstmt.setInt(7, uid);
                result = DatabaseFactory.getInstance().update(credstmt);
                System.out.println("Add Patient: " + result);
            }
            if (bean.hasRole("doctor")) {
                credstmt = DatabaseFactory.getInstance().getStatement(
                        String.format(roleQuery, "doctor"));
                credstmt.setString(1, bean.getFirstName());
                credstmt.setString(2, bean.getLastName());
                credstmt.setInt(3, uid);
                result = DatabaseFactory.getInstance().update(credstmt);
                System.out.println("Add Doctor: " + result);
            }
            if (bean.hasRole("staff")) {
                credstmt = DatabaseFactory.getInstance().getStatement(
                        String.format(roleQuery, "staff"));
                credstmt.setString(1, bean.getFirstName());
                credstmt.setString(2, bean.getLastName());
                credstmt.setInt(3, uid);
                result = DatabaseFactory.getInstance().update(credstmt);
                System.out.println("Add Staff: " + result);
            }
            bean.setValid(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // If user is type-patient, we must insert additional fields!

        return bean;
    }
}