package com.spring.security.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee  {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long  id;
    private String password;
    private String email;
    private String role;
    @OneToMany
    private List<Authority> authorities;
}
