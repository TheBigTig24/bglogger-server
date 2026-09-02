package com.example.bglogger.controllers;

import java.io.IOException;
import java.net.URI;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.bglogger.dto.UserProfileEditDTO;
import com.example.bglogger.dto.UserRegistrationDTO;
import com.example.bglogger.models.User;
import com.example.bglogger.services.CloudinaryService;
import com.example.bglogger.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final CloudinaryService cloudinaryService;

    public UserController(
        UserService userService,
        CloudinaryService cloudinaryService
    ) {
        this.userService = userService;
        this.cloudinaryService = cloudinaryService;
    }

    @PostMapping(value = "/", consumes = "application/json")
    public ResponseEntity<User> createUser(@Valid @RequestBody UserRegistrationDTO userRegDTO) {
        User savedUser = userService.registerNewUser(userRegDTO);

        URI location = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(savedUser.getId())
            .toUri();

        return ResponseEntity.created(location).body(savedUser);
    }

    @PutMapping(value = "/verify")
    public ResponseEntity<Void> verifyUserEmail(@RequestParam String token) {
        userService.verifyEmail(token);
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/edit-profile", consumes = "application/json")
    public ResponseEntity<User> editProfile(@Valid @RequestBody UserProfileEditDTO userDTO) {
        User editedUser = userService.editProfileDetails(userDTO);
        return ResponseEntity.ok().body(editedUser);
    }

    @PostMapping(value = "/{id}/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<User> uploadAvatar(
        @PathVariable Integer id,
        @RequestParam("file") MultipartFile file
    ) throws IOException {
        String imageUrl = cloudinaryService.uploadFile(file);

        User user = userService.updateAvatar(id, imageUrl);

        return ResponseEntity.ok().body(user);
    }
}
