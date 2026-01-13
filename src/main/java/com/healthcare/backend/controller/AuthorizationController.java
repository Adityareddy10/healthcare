package com.healthcare.backend.controller;

import com.healthcare.backend.entity.Authorization;
import com.healthcare.backend.service.AuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;
import java.util.List;

@RestController
@RequestMapping("/api/authorizations")
public class AuthorizationController {
    
    @Autowired
    private AuthorizationService authorizationService;
    
    @PostMapping
    public ResponseEntity<Authorization> createAuthorization(@RequestBody Authorization authorization) {
        try {
            Authorization createdAuthorization = authorizationService.createAuthorization(authorization);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdAuthorization);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Authorization> getAuthorization(@PathVariable Long id) {
        Optional<Authorization> authorization = authorizationService.getAuthorizationById(id);
        if (authorization.isPresent()) {
            return ResponseEntity.ok(authorization.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    
    @GetMapping
    public ResponseEntity<List<Authorization>> getAllAuthorizations() {
        List<Authorization> authorizations = authorizationService.getAllAuthorizations();
        return ResponseEntity.ok(authorizations);
    }
    
    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<Authorization>> getAuthorizationsByPatient(@PathVariable Long patientId) {
        try {
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    
    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<List<Authorization>> getAuthorizationsByDoctor(@PathVariable Long doctorId) {
        try {
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Authorization> updateAuthorization(@PathVariable Long id, 
                                                              @RequestBody Authorization authorizationDetails) {
        try {
            Optional<Authorization> authorization = authorizationService.getAuthorizationById(id);
            if (authorization.isPresent()) {
                Authorization auth = authorization.get();
                if (authorizationDetails.getStatus() != null) {
                    auth.setStatus(authorizationDetails.getStatus());
                }
                if (authorizationDetails.getEndDate() != null) {
                    auth.setEndDate(authorizationDetails.getEndDate());
                }
                if (authorizationDetails.getPurpose() != null) {
                    auth.setPurpose(authorizationDetails.getPurpose());
                }
                Authorization updatedAuthorization = authorizationService.createAuthorization(auth);
                return ResponseEntity.ok(updatedAuthorization);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthorization(@PathVariable Long id) {
        try {
            authorizationService.deleteAuthorization(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    
    @PutMapping("/{id}/revoke")
    public ResponseEntity<Authorization> revokeAuthorization(@PathVariable Long id) {
        try {
            Authorization revokedAuthorization = authorizationService.revokeAuthorization(id);
            if (revokedAuthorization != null) {
                return ResponseEntity.ok(revokedAuthorization);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
