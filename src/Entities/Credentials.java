package Entities;

public class Credentials {
    private String mail;
    private String password;

    public Credentials(String mail, String password) {
        this.mail = mail;
        this.password = password;
    }
    public Credentials() {

    }

    public String getMail() {
        return mail;
    }
    public void setMail(String mail) {
        this.mail = mail;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
