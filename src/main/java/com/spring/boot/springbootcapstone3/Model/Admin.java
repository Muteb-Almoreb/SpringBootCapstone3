package com.spring.boot.springbootcapstone3.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Email cannot be empty")
    @Email(message = "Invalid email format")
    @Column(columnDefinition = "varchar(120) not null", unique = true)
    private String email;


    @NotEmpty(message = "Name cannot be empty")
    @Size(min = 3, max = 80, message = "Name must be 3 to 80 characters")
    @Column(columnDefinition = "varchar(80) not null")
    private String name;


    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Column(insertable = false,
            updatable = false,
            columnDefinition = "datetime not null default current_timestamp")
    private LocalDateTime createdAt;

}
