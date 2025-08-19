package com.spring.boot.springbootcapstone3.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Organization {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "The name must not be empty")
    @Column(columnDefinition = "varchar(20) not null")
    private String name;

    @Email
    @NotEmpty(message = "The Email must not be empty")
    @Column(columnDefinition = "varchar(150) not null")
    private String email;


    @NotEmpty(message = "The phone number must not be empty")
    @Column(columnDefinition = "varchar(20) not null")
    private String phone;

    @NotEmpty(message = "The Commercial Register must not be empty")
    @Column(columnDefinition = "varchar(150) not null")
    private String commercialRegister;

    @OneToMany(cascade = CascadeType.ALL , mappedBy = "organization")
    private Set<ServiceRequest> serviceRequests;



}
