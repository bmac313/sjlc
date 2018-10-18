package org.sjlchatham.sjlcweb.models;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

@Entity
public class PasswordResetToken {

    // Token will expire in 24 hours (60 minutes * 24)
    private static final int TIME_BEFORE_EXPIRATION = 60 * 24;

    public PasswordResetToken() {
        super();
    }

    public PasswordResetToken(User user, String token) {
        super();

        this.user = user;
        this.token = token;
        this.expirationDate = calculateExpirationDate(TIME_BEFORE_EXPIRATION);
    }

    @Id
    @GeneratedValue
    private int id;

    private String token;

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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    private Date calculateExpirationDate(final int expirationTimeInMinutes) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());
        calendar.add(Calendar.MINUTE, expirationTimeInMinutes);
        return new Date(calendar.getTime().getTime());
    }

    public void updateToken(final String token) {
        this.token = token;
        this.expirationDate = calculateExpirationDate(TIME_BEFORE_EXPIRATION);
    }

    @Override
    public String toString() {
        return "Token [String=" + token + "]" +
                "[Expires" + expirationDate + "]";
    }
}
