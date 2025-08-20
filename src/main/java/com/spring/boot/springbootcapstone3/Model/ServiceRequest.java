package com.spring.boot.springbootcapstone3.Model;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ServiceRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "The title must not be empty")
    @Column(columnDefinition = "varchar(100) not null")
    private String title;

    @NotEmpty(message = "The description must not be empty")
    @Column(columnDefinition = "varchar(200) not null")
    private String description;


    private String status;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Column(insertable = false, updatable = false,
            columnDefinition = "datetime not null default current_timestamp")
    private LocalDateTime createdAt;

    @NotEmpty(message = "Location must not be empty")
    @Column(columnDefinition = "varchar(200) not null")
    private String location;

    @Column(columnDefinition = "varchar(500)")
    private String locationUrl;

    @ManyToOne
    @JsonIgnore
    private Organization organization;


    @OneToMany(mappedBy = "serviceRequest", cascade = CascadeType.ALL)
    private Set<Offer> offers;

    @OneToOne(mappedBy = "serviceRequest", cascade = CascadeType.ALL)
    @JsonIgnore
    private Contract contract;

}
