package com.syansoft.school_management_system.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Collection;
import jakarta.persistence.*;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "users") 
public class User implements UserDetails {

    /**
	 * 
	 */
	private static final long serialVersionUID = 5832992732606189748L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;

    // Default constructor
    public User() {}

    // Constructor
    public User(String username, String password, Role role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

   
    public Long getId() { return id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    

    public String getEmail() {return email;}

	public void setEmail(String email) {this.email = email;}

	public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }

    // Spring Security methods
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        System.out.println("🔹 Granted Role: " + role.name()); 
        return List.of(new SimpleGrantedAuthority(role.name())); 
    }

    @Override
    public boolean isAccountNonExpired() { return true; }
    @Override
    public boolean isAccountNonLocked() { return true; }
    @Override
    public boolean isCredentialsNonExpired() { return true; }
    @Override
    public boolean isEnabled() { return true; }


}
