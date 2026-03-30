package com.budgetmanagement.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "Name is required")
    @Column(nullable = false)
	private String name ;
	
	@Email(message= "Enter a valid email")
	@NotBlank(message="Email is required")
	@Column(nullable = false , unique = true)
	private String email;
	
	@NotBlank(message = "Password is required")
	@Column(nullable = false)
	private String password;
	
	@Column(name= "created_At" , updatable = false)
	private LocalDateTime createdAt;
	
	 @PrePersist
	    protected void onCreate() {
	        this.createdAt = LocalDateTime.now();
	 }
}
