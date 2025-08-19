package com.spring.boot.springbootcapstone3.Model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
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
public class ServiceRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

//    @NotEmpty(message = "The name must not be empty")
//    @Column(columnDefinition = "varchar(20) not null")
//    private Integer organizationId;


    @NotEmpty(message = "The title must not be empty")
    @Column(columnDefinition = "varchar(100) not null")
    private String title;

    @NotEmpty(message = "The description must not be empty")
    @Column(columnDefinition = "varchar(200) not null")
    private String description;



//    @Pattern(
//            regexp = "(?i)^(pending|approved|paid|released|rejected|attended)$",
//            message = "Status must be one of: Pending, Approved, Paid, Released, Rejected, Attended"
//    )
    @Column(columnDefinition = "varchar(20) not null")
    private String status;



    @Column(columnDefinition = "timestamp default current_timestamp")
    private LocalDateTime createdAt;

    @NotEmpty(message = "Location must not be empty")
    @Column(columnDefinition = "varchar(200) not null")
    private String location;



    @Column(columnDefinition = "varchar(500)")
    private String locationUrl;






    @ManyToOne
    //هاذي اختياري
//    @JoinColumn(name = "merchantId" , referencedColumnName = "id")
    @JsonIgnore
    private Organization organization;
}
