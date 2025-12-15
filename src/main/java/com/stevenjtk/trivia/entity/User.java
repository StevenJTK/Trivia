package com.stevenjtk.trivia.entity;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "trivia_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String username;
    private String password;
    private Integer discordId;

    public User() {}

    public User(String username, String password, Integer discordId) {
        this.username = username;
        this.password = password;
        this.discordId = discordId;
    }

    @Override
    public String toString() {
        return "Username id = " + id + ", Username = " + username + ", Discord Id = " + discordId;
    }

    /**
     * This creates a parent table in the database that binds Role to User
     * Allowing admins to verify which roles and permissions a user has
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role_id",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )


    private Set<Roles> roles = new HashSet<>();

    public Set<Roles> getAllRoles() {
        return roles;
    }


    // Getters and Setters Section

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void SetUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void SetPassword(String password) {
        this.password = password;
    }

    public Integer getDiscordId() {
        return discordId;
    }

    public void SetDiscordId(Integer discordId) {
        this.discordId = discordId;
    }
}
