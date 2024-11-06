package com.hsm.workTest;

import com.hsm.entity.AppUser;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v2/test")
public class CheckTest {

    @PostMapping("/add")
    public AppUser addAnything(@AuthenticationPrincipal AppUser appUser) {
        return appUser;
    }
}
