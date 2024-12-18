package fr.parisnanterre.greentrip.backend.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "invalidated_tokens")
public class InvalidatedToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String token;

    @Column(nullable = false)
    private Date invalidatedAt;

    public InvalidatedToken() {
    }

    public InvalidatedToken(String token, Date invalidatedAt) {
        this.token = token;
        this.invalidatedAt = invalidatedAt;
    }

    public Long getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public Date getInvalidatedAt() {
        return invalidatedAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setInvalidatedAt(Date invalidatedAt) {
        this.invalidatedAt = invalidatedAt;
    }

}

