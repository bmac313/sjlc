package org.sjlchatham.sjlcweb.models;

import javax.persistence.*;
import java.util.Date;

@Entity
public class PasswordResetToken {

    // Token will expire in 24 hours (60 minutes * 24)
    private static final int TIME_BEFORE_EXPIRATION = 60 * 24;

    public PasswordResetToken() {}

    public PasswordResetToken(int id, User user, Date expirationDate) {
        this.id = id;
        this.user = user;
        this.expirationDate = expirationDate;
    }

    @Id
    @GeneratedValue
    private int id;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    private Date expirationDate;

    public static int getTimeBeforeExpiraton() {
        return TIME_BEFORE_EXPIRATION;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }
}
