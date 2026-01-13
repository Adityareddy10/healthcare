package com.healthcare.backend.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "access_logs")
public class AccessLog {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "authorization_id")
    private Authorization authorization;
    
    @Column(nullable = false)
    private String actionType;
    
    @Column(nullable = false)
    private String resourceType;
    
    @Column(nullable = false)
    private String ipAddress;
    
    @Column(columnDefinition = "TEXT")
    private String details;
    
    @Column(nullable = false)
    private String status;
    
    @Column(name = "access_time", nullable = false)
    private LocalDateTime accessTime;
    
    @PrePersist
    protected void onCreate() {
        if (accessTime == null) {
            accessTime = LocalDateTime.now();
        }
    }
    
    // Constructors
    public AccessLog() {}
    
    public AccessLog(User user, Patient patient, String actionType, 
                     String resourceType, String ipAddress) {
        this.user = user;
        this.patient = patient;
        this.actionType = actionType;
        this.resourceType = resourceType;
        this.ipAddress = ipAddress;
        this.status = "SUCCESS";
        this.accessTime = LocalDateTime.now();
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    public Patient getPatient() {
        return patient;
    }
    
    public void setPatient(Patient patient) {
        this.patient = patient;
    }
    
    public Authorization getAuthorization() {
        return authorization;
    }
    
    public void setAuthorization(Authorization authorization) {
        this.authorization = authorization;
    }
    
    public String getActionType() {
        return actionType;
    }
    
    public void setActionType(String actionType) {
        this.actionType = actionType;
    }
    
    public String getResourceType() {
        return resourceType;
    }
    
    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }
    
    public String getIpAddress() {
        return ipAddress;
    }
    
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
    
    public String getDetails() {
        return details;
    }
    
    public void setDetails(String details) {
        this.details = details;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public LocalDateTime getAccessTime() {
        return accessTime;
    }
    
    public void setAccessTime(LocalDateTime accessTime) {
        this.accessTime = accessTime;
    }
}
