package com.spring.boot.springbootcapstone3.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "requestId should not be empty")
    @Column(columnDefinition = "int not null")
    private Integer requestId;

    @NotNull(message = "vendorId should not be empty")
    @Column(columnDefinition = "int not null")
    private Integer vendorId;

    // calculated by the system
    @Column(columnDefinition = "datetime not null")
    private LocalDateTime scheduledAt;

    @NotEmpty(message = "status should not be empty")
    @Column(columnDefinition = "varchar(30) not null")
    private String status;

    @NotEmpty(message = "status should not be empty")
    @Column(columnDefinition = "varchar(255) not null")
    private String notes;

}
