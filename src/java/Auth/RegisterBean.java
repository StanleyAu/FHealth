/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Auth;

/**
 *
 * @author Stan
 */
public class RegisterBean extends UserBean {

    private String firstName;
    private String lastName;
    private String ohip;
    private String sin;
    private String address;
    private String phone;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String newFirstName) {
        firstName = newFirstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String newLastName) {
        lastName = newLastName;
    }

    public String getOhip() {
        return ohip;
    }

    public void setOhip(String newOhip) {
        ohip = newOhip;
    }

    public String getSin() {
        return sin;
    }

    public void setSin(String newSin) {
        sin = newSin;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String newAddress) {
        address = newAddress;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String newPhone) {
        phone = newPhone;
    }
}
