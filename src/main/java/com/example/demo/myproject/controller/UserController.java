package com.example.demo.myproject.controller;

import com.example.demo.myproject.controller.dto.UserAddRequest;
import com.example.demo.myproject.controller.dto.UserDto;
import com.example.demo.myproject.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    private final MessageSource messageSource;


    public UserController(UserService userService, MessageSource messageSource) {
        this.userService = userService;
        this.messageSource = messageSource;
    }


    @GetMapping("/logout")
    public String logout() {
        return "redirect:/login";
    }

    @GetMapping("/landing/{id}")
    public ResponseEntity<UserDto> landing(@PathVariable Integer id, @NonNull HttpServletRequest request) {

        return userService.landing(id,request);
    }

    @PostMapping("/{id}/upload-profile-photo")
    public ResponseEntity<String> uploadProfilePhoto(@PathVariable Integer id, @RequestPart("photo") MultipartFile photo) {
        if (photo.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(messageSource.getMessage("image.message.not.found",
                            null, LocaleContextHolder.getLocale()));
        }

        String photoUrl = userService.saveProfilePhoto(id, photo);
        if (photoUrl != null) {
            return ResponseEntity.ok().body(photoUrl);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(messageSource.getMessage("image.message.something.went.wrong",
                            null, LocaleContextHolder.getLocale()));
        }
    }


    @PostMapping("/users/addUser")
    public ResponseEntity<UserDto> addUser(
            @RequestBody UserAddRequest userAddRequest
    ) {
        return userService.addUser(userAddRequest);
    }


    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getAllUsers(@NonNull HttpServletRequest request) {
        return userService.getAllUsers(request);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable("id") Integer id) {
        return userService.getUserById(id);
    }
    @PutMapping("/users/{id}")
    public ResponseEntity<UserDto> updateUserById(@PathVariable("id") Integer id,@RequestBody UserDto userDto) {
        return userService.updateUserById(id,userDto);
    }
    @DeleteMapping("/users/{id}")
    public ResponseEntity<UserDto> deleteUserById(@PathVariable("id") Integer id) {
        return userService.deleteUserById(id);
    }

}
