package com.preproject.springbootcrud.model;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private long id;

    @Column(name = "username")
    private String userName;

    @Column(name = "lastname")
    private String lastName;

    @Column(name = "age")
    private int age;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "non_expired")
    private byte isAccountNonExpired;

    @Column(name = "non_locked")
    private byte isAccountNonLocked;

    @Column(name = "credentials_non_expired")
    private byte isCredentialsNonExpired;

    @Column(name = "enabled")
    private byte isEnabled;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER) //
    @JoinTable(
            name="users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    //@ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles = new HashSet<>();

    public User(){

    }

    public User(String userName, String lastName,int age, String email, String password, Set<Role> roles){
        this.userName = userName;
        this.lastName = lastName;
        this.age = age;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    public long getId(){
        return this.id;
    }

    public void setId(long id){
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        this.password = password;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Role> getRoles(){
        return this.roles;
    }

    public String rolesToString(){
        StringBuilder stringBuilder = new StringBuilder();
        for (Role r: this.roles){
            if (r.getRoleName().equals("ROLE_ADMIN")){
                stringBuilder.append("ADMIN ");
            } else if(r.getRoleName().equals("ROLE_USER")){
                stringBuilder.append("USER ");
            }
        }
        return stringBuilder.toString();
    }

    public void setRoles(Set<Role> roles){
        this.roles = roles;
    }

    public void addRole(Role role){
        roles.add(role);
        role.getUsers().add(this);
    }

    public void removeRole(Role role){
        roles.remove(role);
        role.getUsers().remove(this);
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public boolean isAccountNonExpired() {
        if(this.isAccountNonExpired == 1){
            return true;
        }
        return false;
    }

    public void setIsAccountNonExpired() {
        this.isAccountNonExpired = (byte) 1;
    }

    @Override
    public boolean isAccountNonLocked() {
        if(this.isAccountNonLocked == 1){
            return true;
        }
        return false;
    }

    public void setIsAccountNonLocked() {
        this.isAccountNonLocked = (byte) 1;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        if(this.isCredentialsNonExpired == 1){
            return true;
        }
        return false;
    }

    public void setIsCredentialsNonExpired() {
        this.isCredentialsNonExpired = (byte) 1;
    }

    @Override
    public boolean isEnabled() {
        if(this.isEnabled == 1){
            return true;
        }
        return false;
    }

    public void setIsEnabled() {
        this.isEnabled = (byte) 1;
    }
}
