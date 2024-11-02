package com.hsm.service;

import com.hsm.entity.AppUser;
import com.hsm.payload.AppUserDto;
import com.hsm.payload.LoginDto;
import com.hsm.payload.TokenDto;
import com.hsm.repository.AppUserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AppUserService {

    private final AppUserRepository appUserRepository;
    private final ModelMapper modelMapper;
    private final JWTService jwtService;

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

    public Optional<AppUser> verifySignupUsername(AppUser appUser) {
        return appUserRepository.findByUsername(appUser.getUsername());
    }
    public Optional<AppUser> verifySignupEmail(AppUser appUser) {
        return appUserRepository.findByEmail(appUser.getEmail());
    }
    public void roleSetForOwner(AppUser appUser) {
        appUser.setRole("ROLE_OWNER");
    }
    public void roleSetForUser(AppUser appUser) {
        appUser.setRole("ROLE_USER");
    }
    public void encryptPassword(AppUser appUser) {
        appUser.setPassword(BCrypt.hashpw(appUser.getPassword(), BCrypt.gensalt(5)));
    }
    public AppUserDto addUser(AppUser appUser) {
        return mapToDto(appUserRepository.save(appUser));
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

    public Object tokenNumber(String token) {
        TokenDto tokenDto = new TokenDto();
        tokenDto.setToken(token);
        tokenDto.setType("JWT");
        return tokenDto;
    }

    // ------------------- GetUserDetails ------------------- //

    public AppUserDto findUsername(String username) {
        return mapToDto(appUserRepository.findByUsername(username).get());
    }
    public AppUserDto findEmail(String email) {
        return mapToDto(appUserRepository.findByEmail(email).get());
    }
    public List<AppUserDto> getAllUserDetails() {
        return appUserRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    // ----------------------- Update ----------------------- //

    // ----------------------- Delete ----------------------- //
}
