package org.sjlchatham.sjlcweb.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "USERS")
public class User {

    @Id
    @NotNull
    @Size(min = 1, message = "Please enter a username.")
    @Column(name = "USERNAME", length = 50, nullable = false, unique = true)
    private String username;

    @NotNull
    @Size(min = 5, message = "Please enter a password of at least 5 characters.")
    @Column(name = "PASSWORD", length = 60, nullable = false)
    private String password;

    @NotNull
    @Size(min = 1, message = "Please enter a valid email address.")
    private String email;

    @Column(name = "ENABLED", nullable = false)
    private boolean enabled = true;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "user")
    private Set<Authorities> authorities = new HashSet<>(0);

    public User(){}

    public User(String username, String password, String email, boolean enabled) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.enabled = enabled;
    }

    public User(String username, String password, String email, boolean enabled, Set<Authorities> authorities) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.enabled = enabled;
        this.authorities = authorities;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Set<Authorities> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Authorities> authorities) {
        this.authorities = authorities;
    }

    public void addAuthority(Authorities authority) {
        this.authorities.add(authority);
    }
}
