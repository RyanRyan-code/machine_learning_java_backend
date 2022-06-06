package ryan.mlbackend.entity;

import com.sun.istack.NotNull;


import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name="sessions")
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Column(unique=true)
    private String email;

    @NotNull
    private String sessionPassword;

    @NotNull
    private LocalDate expirationDate;


    public Session() {
    }

    public Session(String email, String sessionPassword, LocalDate expirationDate) {
        this.email = email;
        this.sessionPassword = sessionPassword;
        this.expirationDate = expirationDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSessionPassword() {
        return sessionPassword;
    }

    public void setSessionPassword(String sessionPassword) {
        this.sessionPassword = sessionPassword;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpiration_date(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }
}
