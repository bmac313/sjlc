package org.sjlchatham.sjlcweb.models;

import javax.persistence.*;

@Entity
@Table(name = "AUTHORITIES")
public class Authorities {

    @Id
    @Column(name = "AUTHORITY")
    private String authority;

    @ManyToOne
    @JoinColumn(name = "ID")
    private User user;

    public Authorities() {}

    public Authorities(String authority, User user) {
        this.authority = authority;
        this.user = user;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}