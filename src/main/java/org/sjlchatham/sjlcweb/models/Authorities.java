package org.sjlchatham.sjlcweb.models;

import javax.persistence.*;

@Entity
@Table(name = "AUTHORITIES", uniqueConstraints = @UniqueConstraint(columnNames = {"authority", "username"}))
public class Authorities {

    @Id
    @GeneratedValue
    @Column(name = "USER_AUTHORITY_ID", unique = true, nullable = false)
    private Integer userAuthorityId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USERNAME", nullable = false)
    private User user;

    @Column(name = "AUTHORITY", nullable = false, length = 45)
    private String authority;

    public Authorities() {}

    public Authorities(User user, String authority) {
        this.user = user;
        this.authority = authority;
    }

    public Integer getUserAuthorityId() {
        return userAuthorityId;
    }

    public void setUserAuthorityId(Integer userAuthorityId) {
        this.userAuthorityId = userAuthorityId;
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
