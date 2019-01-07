package com.qwerty.mircoservices.userservice.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    private Long id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;

    @ElementCollection(fetch = FetchType.EAGER)
    @JsonIgnore
    private List<String> roles;

    private boolean accountNonExpired, accountNonLocked, credentialsNonExpired, enabled;

    public User() {
    }

    public User(String username, String password, String firstName, String lastName, String email) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

 /*   @JsonCreator
    public User(boolean accountNonExpired, boolean accountNonLocked, boolean credentialsNonExpired, boolean enabled) {
        this.accountNonExpired = accountNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.credentialsNonExpired = credentialsNonExpired;
        this.enabled = enabled;
    }*/

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public void grantAuthority(String authority) {
        if ( roles == null ) roles = new ArrayList<>();
        roles.add(authority);
    }

    @Override
    @JsonIgnore
    public List<GrantedAuthority> getAuthorities(){
        List<GrantedAuthority> authorities = new ArrayList<>();
        roles.forEach(role -> authorities.add(new SimpleGrantedAuthority(role)));
        return authorities;
    }

    @JsonIgnore
    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }



    public String getPassword() {
       return password;
    }



    public void setPassword(String password) {

        this.password = password;

    }



    public Long getId() {

        return id;

    }



    public void setId(Long id) {

        this.id = id;

    }



    public void setAccountNonExpired(boolean accountNonExpired) {

        this.accountNonExpired = accountNonExpired;

    }



    public void setAccountNonLocked(boolean accountNonLocked) {

        this.accountNonLocked = accountNonLocked;

    }



    public void setCredentialsNonExpired(boolean credentialsNonExpired) {

        this.credentialsNonExpired = credentialsNonExpired;

    }



    public void setEnabled(boolean enabled) {

        this.enabled = enabled;

    }



    public String getUsername() {

        return username;

    }



    public void setUsername(String username) {

        this.username = username;

    }



    public String getFirstName() {

        return firstName;

    }



    public void setFirstName(String firstName) {

        this.firstName = firstName;

    }



    public String getLastName() {

        return lastName;

    }



    public void setLastName(String lastName) {

        this.lastName = lastName;

    }



    public String getEmail() {

        return email;

    }



    public void setEmail(String email) {

        this.email = email;

    }

}