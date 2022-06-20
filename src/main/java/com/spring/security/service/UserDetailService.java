package com.spring.security.service;

import com.spring.security.model.Employee;
import com.spring.security.model.SecurityEmployee;
import com.spring.security.repository.EmployeeRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public class UserDetailService implements UserDetailsService {
    private EmployeeRepository repository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<Employee> emp=repository.findByEmail(username);
        return new SecurityEmployee(emp.get(0));
    }
}
