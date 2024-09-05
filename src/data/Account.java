
package data;

public class Account {

    private String accName; //ID
    private String pwd; //password
    private String role; 

    public Account(String accName, String pwd, String role) {
        this.accName = accName;
        this.pwd = pwd;
        this.role = role;
    }

    public String getAccName() {
        return accName;
    }

    public String getPwd() {
        return pwd;
    }

    public String getRole() {
        return role;
    }

}
