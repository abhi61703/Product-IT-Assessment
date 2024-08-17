package com.example.AssessmentMicro.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Assessment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long setId;
    @Column(unique = true)
    private String setName;
    private String createdBy = "admin";
    private String updatedBy = "admin";
    private String domain;

    @Enumerated(EnumType.STRING)
    private AssessmentStatus status = AssessmentStatus.PENDING;

    private LocalDateTime createdTimestamp;
    private LocalDateTime updatedTimestamp;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Question> questions;

    @PrePersist
    protected void onCreate() {
        createdTimestamp = LocalDateTime.now();
        updatedTimestamp = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedTimestamp = LocalDateTime.now();
    }

    // Getters and setters (if not using Lombok)
}
