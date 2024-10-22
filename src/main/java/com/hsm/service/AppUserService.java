package com.hsm.service;

import com.hsm.entity.AppUser;
import com.hsm.payload.AppUserDto;
import com.hsm.payload.LoginDto;
import com.hsm.repository.AppUserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AppUserService {

    private AppUserRepository appUserRepository;
    private ModelMapper modelMapper;
    private JWTService jwtService;

    public AppUserService(AppUserRepository appUserRepository, ModelMapper modelMapper, JWTService jwtService) {
        this.appUserRepository = appUserRepository;
        this.modelMapper = modelMapper;
        this.jwtService = jwtService;
    }

    // ----------------------- Mapping ----------------------- //

    AppUser mapToEntity(AppUserDto appUserDto) {
        return modelMapper.map(appUserDto, AppUser.class);
    }
    AppUserDto mapToDto(AppUser appUser) {
        return modelMapper.map(appUser, AppUserDto.class);
    }

    // ----------------------- SignUp ----------------------- //

    public Optional<AppUser> verifySignupUsername(AppUserDto appUserDto) {
        return appUserRepository.findByUsername(appUserDto.getUsername());
    }
    public Optional<AppUser> verifySignupEmail(AppUserDto appUserDto) {
        return appUserRepository.findByEmail(appUserDto.getEmail());
    }
    public void encryptPassword(AppUserDto appUserDto) {
        appUserDto.setPassword(BCrypt.hashpw(appUserDto.getPassword(), BCrypt.gensalt(5)));
    }
    public AppUserDto addUser(AppUserDto appUserDto) {
        return mapToDto(appUserRepository.save(mapToEntity(appUserDto)));
    }

    // ----------------------- SignIn ----------------------- //

    public String verifyLogin(LoginDto loginDto) {
        Optional<AppUser> opUsername = appUserRepository.findByUsername(loginDto.getUsername());
        if (opUsername.isPresent()) {
            AppUser appUser = opUsername.get();
            if (BCrypt.checkpw(loginDto.getPassword(), appUser.getPassword())) {
                // Generate Token
                return jwtService.generateToken(appUser.getUsername());
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    // ------------------- GetUserDetails ------------------- //

    public AppUserDto findUsername(String username) {
        return mapToDto(appUserRepository.findByUsername(username).get());
    }
    public AppUserDto findEmail(String email) {
        return mapToDto(appUserRepository.findByEmail(email).get());
    }
}
