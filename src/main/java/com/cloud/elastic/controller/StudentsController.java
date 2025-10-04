package com.cloud.elastic.controller;


import com.cloud.elastic.model.Student;
import com.cloud.elastic.model.UserLogin;
import com.cloud.elastic.security.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class StudentsController {

    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public StudentsController(JwtUtil jwtUtil, AuthenticationManager authenticationManager) {
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLogin userLogin){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLogin.getUsername(),userLogin.getPassword()));
        String token = jwtUtil.generateToken(authentication.getName(),authentication.getAuthorities().iterator().next().getAuthority());
        return ResponseEntity.ok(Map.of("token",token));
    }

    @GetMapping("/students")
    public ResponseEntity<List<Student>> students(){
        List<Student> students = List.of(new Student("Maheedhar", "04E11A0520", "Hyderabad"),
                new Student("Ravi", "04E11A0521", "Chennai"),
                new Student("Sai", "04E11A0522", "Bangalore"),
                new Student("Sudhakar", "04E11A0523", "Mumbai"));
        return new ResponseEntity<>(students, HttpStatus.OK);
    }


}
