package br.com.food_manager.foodmanager.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String login;
    private String password;
    private Date lastUpdated;
    private String address;
    private UserType userType;

    public User() {}

    public User(String name,
                String email,
                String login,
                String password,
                Date lastUpdated,
                String address) {
        this.name = name;
        this.email = email;
        this.login = login;
        this.password = password;
        this.lastUpdated = lastUpdated;
        this.address = address;
    }

}

