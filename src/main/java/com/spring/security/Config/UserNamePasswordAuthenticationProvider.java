package com.spring.security.Config;

import com.spring.security.model.Authority;
import com.spring.security.model.Employee;
import com.spring.security.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserNamePasswordAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String name = authentication.getName();
        String pwd=authentication.getCredentials().toString();
        List<Employee> employees=employeeRepository.findByEmail(name);
        if(employees.size()!=0) {
            if (passwordEncoder.matches(pwd, employees.get(0).getPassword())) {
                return new UsernamePasswordAuthenticationToken(name, pwd, getAuthorities(employees.get(0).getAuthorities()));
            }else {
                throw new BadCredentialsException("invalid password");
            }
            }else{
                throw  new BadCredentialsException("no user details found");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

    private List<SimpleGrantedAuthority> getAuthorities(List<Authority> authorities) {
        List<SimpleGrantedAuthority> list = new ArrayList<>();
        for (Authority ls:authorities) {
            list.add(new SimpleGrantedAuthority(ls.getName()));
        }
        return list;
    }
}
