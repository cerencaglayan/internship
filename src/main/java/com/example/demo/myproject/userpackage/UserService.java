package com.example.demo.myproject.userpackage;

import com.example.demo.myproject.config.WebSecurityConfig;
import com.example.demo.myproject.departmentpackage.Department;
import com.example.demo.myproject.departmentpackage.DepartmentRepository;
import com.example.demo.myproject.userrolepackage.UserRole;
import com.example.demo.myproject.userrolepackage.UserRoleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final UserRoleRepository userRoleRepository;
    @Autowired
    private final DepartmentRepository departmentRepository;

    private final PasswordEncoder passwordencoder;

    public UserService(UserRepository userRepository, UserRoleRepository userRoleRepository, DepartmentRepository departmentRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.departmentRepository = departmentRepository;
        this.passwordencoder = passwordEncoder;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Integer userId) {
        return userRepository.findById(userId).orElse(null);
    }
    public User createUser(User user) {
        User newUser = new User();

        UserRole userRole = userRoleRepository.findById(user.getUserRole().getId()).orElse(null);
        Department department = departmentRepository.findById(user.getDepartment().getId()).orElse(null);

        newUser.setUserRole(userRole);
        newUser.setDepartment(department);
        newUser.setActive(user.isActive());
        newUser.setEmail(user.getEmail());
        newUser.setName(user.getName());
        newUser.setPassword(passwordencoder.encode(user.getPassword()));
        newUser.setSurname(user.getSurname());

        return userRepository.save(newUser);
    }


    public String signIn(User user) {
        User user1 = userRepository.findByEmail(user.getEmail());
        if (user1 != null) {
            if (user1.getPassword().equals(user.getPassword())) {
                return "Success";
            }

            return "wrong password";
        }
        ;
        return "This mail is  not registered the system";
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> u = Optional.ofNullable(userRepository.findByName(username));

        u.get().setPassword(passwordencoder.encode(u.get().getPassword()));

        return u.map(SecurityUser::new)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found " + username));
    }
}
