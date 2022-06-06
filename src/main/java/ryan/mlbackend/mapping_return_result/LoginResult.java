package ryan.mlbackend.mapping_return_result;

public class LoginResult {

    private boolean loginDetailsRight=false;
    private String sessionPassword="";
    private String email="";




    public boolean isLoginDetailsRight() {
        return loginDetailsRight;
    }

    public void setLoginDetailsRight(boolean loginDetailsRight) {
        this.loginDetailsRight = loginDetailsRight;
    }

    public String getSessionPassword() {
        return sessionPassword;
    }

    public void setSessionPassword(String sessionPassword) {
        this.sessionPassword = sessionPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
