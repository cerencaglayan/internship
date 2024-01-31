package com.example.demo.myproject.userpackage;

import com.example.demo.myproject.departmentpackage.Department;
import com.example.demo.myproject.departmentpackage.DepartmentRepository;
import com.example.demo.myproject.userrolepackage.UserRole;
import com.example.demo.myproject.userrolepackage.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final UserRoleRepository userRoleRepository;
    @Autowired
    private final DepartmentRepository departmentRepository;

    public UserService(UserRepository userRepository, UserRoleRepository userRoleRepository, DepartmentRepository departmentRepository) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.departmentRepository = departmentRepository;
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
        newUser.setPassword(user.getPassword());
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


}
