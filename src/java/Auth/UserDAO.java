package Auth;

import FHealth.DatabaseFactory;
import FHealth.DatabaseFactory;
import java.math.BigInteger;
import java.text.*;
import java.util.*;
import java.sql.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.io.UnsupportedEncodingException;

public class UserDAO {

    static Connection currentCon = null;
    static ResultSet rs = null;

    public static String generateSalt() {
        SecureRandom random = new SecureRandom();
        // Generate 20 char salt (100 bits / 5 <-- 2^5 = 32
        return new BigInteger(100, random).toString(32);
    }

    // This belongs somewhere else
    public static String generateSHA(String text)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance("SHA1");
        byte[] digest = new byte[64];
        md.update(text.getBytes("utf-16le"), 0, text.length());
        digest = md.digest();

        BigInteger bi = new BigInteger(1, digest);
        return String.format("%0" + (digest.length << 1) + "X", bi);
    }

    private static void getName(UserBean bean, int uid, String role)
    {
        //preparing some objects for connection 
        Statement stmt = null;
        
        String roleQuery = String.format(
                "SELECT first_name, last_name"
                + "FROM %s AS role_table"
                + "WHERE credential_id=%d", role, uid);
        
        try {
            //connect to DB 
            currentCon = DatabaseFactory.getInstance().getConnection();
            stmt = currentCon.createStatement();
            rs = stmt.executeQuery(roleQuery);
            boolean more = rs.next();
            if (!more) {
                // Couldn't find matching role entry for user
                bean.setFirstName(bean.getUsername());
            }
            else if (more) {
                bean.setFirstName(rs.getString("first_name"));
                bean.setLastName(rs.getString("last_name"));
            }
        } catch (Exception ex) {
            System.out.println("Log In failed: An Exception has occurred! " + ex);
        }
        finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception e) {
                }
                rs = null;
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (Exception e) {
                }
                stmt = null;
            }
            if (currentCon != null) {
                try {
                    currentCon.close();
                } catch (Exception e) {
                }
                currentCon = null;
            }
        }
    }
    
    public static UserBean login(UserBean bean)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {

        //preparing some objects for connection 
        Statement stmt = null;

        int uid;
        String username = bean.getUsername();
        String password = bean.getPassword();
        String salt;// = generateSalt();
        String pw_hash;// = generateSHA(password + salt);
        String role;
        String first_name;
        String last_name;

        String searchQuery =
                "SELECT c.id AS id, user, pw_hash, salt, role"
                + "FROM credential AS c"
                + "JOIN credential_role AS cr"
                + "ON c.id=cr.credential_id"
                + "JOIN role AS r"
                + "ON r.id=cr.role_id"
                + "WHERE user='"
                + username
                + "'";

        // "System.out.println" prints in the console; Normally used to trace the process
        System.out.println("Your user name is " + username);
        System.out.println("Your password is " + password);
        System.out.println("Query: " + searchQuery);

        try {
            //connect to DB 
            currentCon = DatabaseFactory.getInstance().getConnection();
            stmt = currentCon.createStatement();
            rs = stmt.executeQuery(searchQuery);
            boolean more = rs.next();

            // if user does not exist set the isValid variable to false
            if (!more) {
                System.out.println("Sorry, you are not a registered user! Please sign up first");
                bean.setValid(false);
            } //if user exists set the isValid variable to true
            else if (more) {
                uid = rs.getInt("id");
                pw_hash = rs.getString("pw_hash");
                salt = rs.getString("salt");
                role = rs.getString("role");
                if (generateSHA(password + salt).equals(pw_hash)) {
                    bean.setValid(true);
                }
                // Authenticated, need to get name
            }
        } catch (Exception ex) {
            System.out.println("Log In failed: An Exception has occurred! " + ex);
        }
        finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception e) {
                }
                rs = null;
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (Exception e) {
                }
                stmt = null;
            }
            if (currentCon != null) {
                try {
                    currentCon.close();
                } catch (Exception e) {
                }
                currentCon = null;
            }
        }
        return bean;
    }
}