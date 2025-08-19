package com.spring.boot.springbootcapstone3.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Check;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Check(constraints = "approval_status IN ('PENDING','APPROVED','REJECTED','SUSPENDED')")
@Table(name = "vendor",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "email"),
                @UniqueConstraint(columnNames = "phone")
        })
public class Vendor {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty @Size(min = 3, max = 100)
    @Column(columnDefinition = "varchar(100) not null")
    private String name;

    @NotEmpty @Email
    @Column(columnDefinition = "varchar(120) not null", unique = true)
    private String email;

    @NotEmpty @Size(min = 7, max = 20)
    @Pattern(regexp = "^[0-9+()\\-\\s]+$", message = "Phone contains invalid characters")
    @Column(columnDefinition = "varchar(20) not null", unique = true)
    private String phone;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(name = "approval_status",columnDefinition = "varchar(20) not null default 'PENDING'")
    @Pattern(
            regexp = "^(PENDING|APPROVED|REJECTED|SUSPENDED)$",
            message = "Status must be PENDING, APPROVED, REJECTED, or SUSPENDED")
    private String approvalStatus = "PENDING";

    @OneToMany(cascade = CascadeType.ALL , mappedBy = "vendor")
    private Set<Contract> contracts;
}
