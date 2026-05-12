package com.trust.amanat.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NonNull;

@Entity
@Data
@Table (name = "members")
public class MembersEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String prefix;

    @Column(name = "first_name")
    @NotBlank(message = "First Name is required")
    @Size(min = 3, max = 24, message = "First Name must be in Letters and minimum 3 characters and maximum 24 characters")
    private String firstname;

    @NotBlank(message = "Last Name is required")
    @Size(min = 3, max = 24, message = "Last Name must be in Letters and minimum 3 characters and maximum 24 characters")
    @Column(name = "last_name")
    private String lastname;

    @NotNull ( message = "Joining Year  is required")
    @Column(name = "joining_year")
    private Integer joiningYear;

    @Pattern(regexp = "^\\+[0-9]{1,3}[0-9]{10}$",
            message = "Mobile Number must be 10 digits")
    @Column(name = "mobile", unique = true)
    private String mobile;

    @NotBlank(message = "Address is required")
    private String address;

    @Column(name = "member_id", unique = true)
    private String memberId;

    private String status;

    @NotBlank(message = "Approval Flag is required")
    @Column(name = "approval_flag")
    private String approvalFlag;


    @Column(name = "joined_by")
    private String joinedBy;

    @JsonIgnore
    @OneToOne(mappedBy = "member")
    private UserEntity user;
}
