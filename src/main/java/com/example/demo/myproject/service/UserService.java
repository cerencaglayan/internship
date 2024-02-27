package com.example.demo.myproject.service;

import com.example.demo.myproject.controller.dto.UserAddRequest;
import com.example.demo.myproject.controller.dto.UserDto;
import com.example.demo.myproject.models.Department;
import com.example.demo.myproject.models.User;
import com.example.demo.myproject.models.UserRole;
import com.example.demo.myproject.repository.DepartmentRepository;
import com.example.demo.myproject.repository.UserRepository;
import com.example.demo.myproject.repository.UserRoleRepository;
import com.example.demo.myproject.service.utils.ImageUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final DepartmentRepository departmentRepository;

    private final PasswordEncoder passwordencoder;

    private static final String UPLOAD_DIR = "src/main/uploads/photos/";


    public UserService(UserRepository userRepository, UserRoleRepository userRoleRepository, DepartmentRepository departmentRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.departmentRepository = departmentRepository;
        this.passwordencoder = passwordEncoder;
    }


    /*
     *  1- create new user
     *  2- find role
     *  3- find department
     *  4- add others
     *  5- save repository
     *
     * */
    public String addUser(UserAddRequest user) {
        User newUser = new User();

        UserRole userRole = userRoleRepository.findByName(user.getRole());
        Department department = departmentRepository.findByName(user.getDepartment());

        newUser.setUserRole(userRole);
        newUser.setDepartment(department);
        newUser.setActive(false);
        newUser.setEmail(user.getEmail());
        newUser.setName(user.getName());
        newUser.setPassword(null);
        newUser.setSurname(user.getSurname());


        userRepository.save(newUser);
        return "Success!";
    }

    /*
     * todo null değeri handle etme oonarılacak.
     *
     * */
    public ResponseEntity<List<UserDto>> getAllUsers() {

        List<User> users = userRepository.findAll();
        List<UserDto> userDtos = new ArrayList<>();

        for (User user : users) {
            UserDto userDto = getUserById(user.getId()).getBody();
            if (userDto != null) {
                userDtos.add(userDto);
            }

        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userDtos);
    }

    /*
     *  Finds user, if not, 404 code.
     *
     * */
    public ResponseEntity<UserDto> getUserById(Integer id) {
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isPresent()) {

            User user = userOptional.get();
            String roleName = user.getUserRole() != null ? user.getUserRole().getName() : null;
            String departmentName = user.getDepartment() != null ? user.getDepartment().getName() : null;
            byte[] imageBytes = user.getImage() != null ? ImageUtils.decodeBase64String(user.getImage()) : null;

            return ResponseEntity.status(HttpStatus.OK)
                    .body(UserDto.builder()
                            .name(user.getName())
                            .surname(user.getSurname())
                            .email(user.getEmail())
                            .role(roleName)
                            .department(departmentName)
                            .image(imageBytes)
                            .build());
        } else {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(null);
        }


    }


    public ResponseEntity<UserDto> landing(Integer id) {
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isPresent()) {

            User user = userOptional.get();
            String roleName = user.getUserRole() != null ? user.getUserRole().getName() : null;
            String departmentName = user.getDepartment() != null ? user.getDepartment().getName() : null;
            byte[] imageBytes = user.getImage() != null ? ImageUtils.decodeBase64String(user.getImage()) : null;

            return ResponseEntity.status(HttpStatus.OK)
                    .body(UserDto.builder()
                            .name(user.getName())
                            .surname(user.getSurname())
                            .email(user.getEmail())
                            .role(roleName)
                            .department(departmentName)
                            .image(imageBytes)
                            .build());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null);
        }
    }


    public String saveProfilePhoto(Integer id, MultipartFile photo) {
        try {
            String fileName = UUID.randomUUID().toString() + "-" + photo.getOriginalFilename();
            Path filePath = Paths.get(UPLOAD_DIR + fileName);

            Files.createDirectories(filePath.getParent());
            Files.write(filePath, photo.getBytes());

            Optional<User> userOptional = userRepository.findById(id);

            if (userOptional.isPresent()) {
                userOptional.get().setImage(ImageUtils.encodeFileToBase64String(photo.getBytes()));
                userRepository.save(userOptional.get());
            }

            return fileName;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
