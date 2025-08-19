package com.spring.boot.springbootcapstone3.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Organizations {
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



}
