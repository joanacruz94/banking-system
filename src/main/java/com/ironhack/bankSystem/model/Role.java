package com.ironhack.bankSystem.model;

import com.ironhack.bankSystem.enums.SystemRole;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(value = EnumType.STRING)
    private SystemRole role;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userRole;

    public Role() {
    }

    public Role(SystemRole role, User user) {
        this.role = role;
        this.userRole = user;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", authority='" + role + '\'';
    }
}