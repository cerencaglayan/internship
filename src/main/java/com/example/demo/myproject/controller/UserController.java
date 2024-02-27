package com.example.demo.myproject.controller;

import com.example.demo.myproject.controller.dto.UserAddRequest;
import com.example.demo.myproject.controller.dto.UserDto;
import com.example.demo.myproject.service.UserService;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<UserDto> landing(@PathVariable Integer id) {

        return userService.landing(id);
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

    /*
     * ADMIN AREA
     *
     * */

    @PostMapping("/users/addUser")
    public String addUser(
            @RequestBody UserAddRequest userAddRequest
    ) {
        return userService.addUser(userAddRequest);
    }


    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable("id") Integer id) {
        return userService.getUserById(id);
    }


}
