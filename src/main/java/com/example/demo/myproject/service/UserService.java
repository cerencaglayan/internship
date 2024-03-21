package com.example.demo.myproject.service;

import com.example.demo.myproject.controller.dto.UserAddRequest;
import com.example.demo.myproject.controller.dto.UserDto;
import com.example.demo.myproject.mail.token.ConfirmationToken;
import com.example.demo.myproject.mail.token.ConfirmationTokenRepository;
import com.example.demo.myproject.models.*;
import com.example.demo.myproject.repository.CompanyRepository;
import com.example.demo.myproject.repository.DepartmentRepository;
import com.example.demo.myproject.repository.UserRepository;
import com.example.demo.myproject.repository.UserRoleRepository;
import com.example.demo.myproject.service.utils.ImageUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
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

    private final CompanyRepository companyRepository;

    private final JWTService jwtService;

    private final PasswordEncoder passwordencoder;

    private static final String UPLOAD_DIR = "src/main/uploads/photos/";
    private final ConfirmationTokenRepository confirmationTokenRepository;


    public UserService(UserRepository userRepository, UserRoleRepository userRoleRepository, DepartmentRepository departmentRepository, CompanyRepository companyRepository, JWTService jwtService, PasswordEncoder passwordEncoder,
                       ConfirmationTokenRepository confirmationTokenRepository) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.departmentRepository = departmentRepository;
        this.companyRepository = companyRepository;
        this.jwtService = jwtService;
        this.passwordencoder = passwordEncoder;
        this.confirmationTokenRepository = confirmationTokenRepository;
    }


    /*
     *  1- create new user
     *  2- find role
     *  3- find department
     *  4- add others
     *  5- save repository
     *
     * */


    public ResponseEntity<UserDto> addUser(UserAddRequest user) {
        User newUser = new User();

        UserRole userRole = userRoleRepository.findByName(user.getRole());
        Department department = departmentRepository.findByName(user.getDepartment());
        Company company = companyRepository.findByName(user.getCompany());

        newUser.setUserRole(userRole);
        newUser.setDepartment(department);
        newUser.setActive(false);
        newUser.setEmail(user.getEmail());
        newUser.setName(user.getName());
        newUser.setPassword(null);
        newUser.setSurname(user.getSurname());
        newUser.setCompany(company);


        userRepository.save(newUser);


        return ResponseEntity
                .status(HttpStatus.OK)
                .body(getUserById(newUser.getId()).getBody());
    }

    /*
     * todo null değeri handle etme onarılacak.
     *
     * */
    public ResponseEntity<List<UserDto>> getAllUsers(@NonNull HttpServletRequest request) {

        List<UserDto> userDtos = new ArrayList<>();

        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;
        jwt = authHeader.substring(7);

        List<User> users;

        userEmail = jwtService.extractUsername(jwt);
        Optional<User> userOptional = userRepository.findByEmail(userEmail);

        if(userOptional.get().getUserRole().getName().equals("ROLE_USER")){
            users = userRepository.findAllByUserRole(userOptional.get().getUserRole());
        }
        else{
            users = userRepository.findAll();
        }

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

            return ResponseEntity.status(HttpStatus.OK)
                    .body(returnDto(user.getId()));
        } else {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(null);
        }


    }


    public ResponseEntity<UserDto> landing(Integer id, @NonNull HttpServletRequest request) {

        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;
        jwt = authHeader.substring(7);

        userEmail = jwtService.extractUsername(jwt);
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isPresent()) {
            if (!userOptional.get().getEmail().equals(userEmail)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(null);
            }

            User user = userOptional.get();

            return ResponseEntity.status(HttpStatus.OK)
                    .body(returnDto(user.getId()));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null);
        }
    }


    public String saveProfilePhoto(Integer id, MultipartFile photo) {
        try {

            Optional<User> userOptional = userRepository.findById(id);

            if (userOptional.isPresent()) {
                User user = userOptional.get();
                String base64EncodedPhoto = ImageUtils.encodeFileToBase64String(photo.getBytes());
                user.setImage(base64EncodedPhoto);
                userRepository.save(user);
            }

            return "";
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ResponseEntity<UserDto> updateUserById(Integer id, UserDto user) {
        Optional<User> userOptional = userRepository.findById(id);

        if(userOptional.isPresent()) {

            User newUser = userOptional.get();

            UserRole userRole = userRoleRepository.findByName(user.getRole());
            Department department = departmentRepository.findByName(user.getDepartment());
            Company company = companyRepository.findByName(user.getCompany());

            newUser.setUserRole(userRole);
            newUser.setDepartment(department);
            newUser.setEmail(user.getEmail());
            newUser.setName(user.getName());
            newUser.setSurname(user.getSurname());
            newUser.setCompany(company);

            userRepository.save(newUser);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(getUserById(newUser.getId()).getBody());
        }
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(null);

    }

    public ResponseEntity<UserDto> deleteUserById(Integer id) {

        /*
        delete all tokens
        * */




        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {

            List<ConfirmationToken>  tokens = confirmationTokenRepository.findAllByUser(user.get());
            confirmationTokenRepository.deleteAll(tokens);

            userRepository.delete(user.get());
            return ResponseEntity.ok()
                    .body(getUserById(id).getBody());
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(null);
    }

    /*
    * tekrarlayan userdto oluşturma işlemini tek celsede yapmak için
    * */

    private UserDto returnDto(Integer id){
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isPresent()) {

            User user = userOptional.get();
            String roleName = user.getUserRole() != null ? user.getUserRole().getName() : null;
            String departmentName = user.getDepartment() != null ? user.getDepartment().getName() : null;
            String companyName = user.getCompany() != null ? user.getCompany().getName() : null;
            byte[] imageBytes = user.getImage() != null ? ImageUtils.decodeBase64String(user.getImage()) : null;

            return UserDto.builder()
                            .id(user.getId())
                            .name(user.getName())
                            .surname(user.getSurname())
                            .email(user.getEmail())
                            .role(roleName)
                            .department(departmentName)
                            .company(companyName)
                            .image(imageBytes)
                            .build();
    }
        return null;
    }
}
