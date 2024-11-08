package com.hsm.controller;

import com.hsm.entity.AppUser;
import com.hsm.payload.AppUserDto;
import com.hsm.payload.LoginDto;
import com.hsm.service.AppUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users")
public class AppUserController {

    private final AppUserService appUserService;

    public AppUserController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    // ----------------------- SignUp ----------------------- //

    @PostMapping("/signup-owner")
    public ResponseEntity<?> addPropertyOwner(@RequestBody AppUserDto appUserDto) {
        if (appUserService.verifySignupUsername(appUserDto)) {
            return new ResponseEntity<>("Username already taken", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (appUserService.verifySignupEmail(appUserDto)) {
            return new ResponseEntity<>("Email already taken", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        appUserService.encryptPassword(appUserDto);
        appUserService.setRoleForOwner(appUserDto);
        return new ResponseEntity<>(appUserService.addUser(appUserDto),HttpStatus.CREATED);
    }

    @PostMapping("/signup-user")
    public ResponseEntity<?> addNewUser(@RequestBody AppUserDto appUserDto) {
        if (appUserService.verifySignupUsername(appUserDto)) {
            return new ResponseEntity<>("Username already taken", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (appUserService.verifySignupEmail(appUserDto)) {
            return new ResponseEntity<>("Email already taken", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        appUserService.encryptPassword(appUserDto);
        appUserService.setRoleForUser(appUserDto);
        return new ResponseEntity<>(appUserService.addUser(appUserDto),HttpStatus.CREATED);
    }

    // ----------------------- SignIn ----------------------- //

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginDto loginDto) {
        String token = appUserService.verifyLogin(loginDto);
        if (token != null) {
            return new ResponseEntity<>(appUserService.tokenNumber(token), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Invalid username/password", HttpStatus.FORBIDDEN);
        }
    }

    // ------------------- GetUserDetails ------------------- //

    @GetMapping("/username")
    public ResponseEntity<AppUserDto> getByUsername(@RequestParam String username) {
       return new ResponseEntity<>(appUserService.findUsername(username), HttpStatus.OK);
    }

    @GetMapping("/email")
    public ResponseEntity<AppUserDto> getByEmail(@RequestParam String email) {
        return new ResponseEntity<>(appUserService.findEmail(email), HttpStatus.OK);
    }

    @GetMapping("/all/data")
    public ResponseEntity<List<AppUserDto>> getAllInfo() {
        return new ResponseEntity<>(appUserService.getAllUserDetails(), HttpStatus.OK);
    }

    // ----------------------- Update ----------------------- //

    // ----------------------- Delete ----------------------- //

}