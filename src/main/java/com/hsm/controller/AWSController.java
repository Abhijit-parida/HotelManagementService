package com.hsm.controller;

import com.hsm.entity.AppUser;
import com.hsm.service.AWSService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/images")
public class AWSController {

    private final AWSService awsService;

    public AWSController(AWSService awsService) {
        this.awsService = awsService;
    }

    // -------------------- ImageUpload --------------------- //

    @PostMapping(path = "/upload/file/{bucketName}",
                    consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> uploadFile(@RequestParam MultipartFile file,
                                        @PathVariable String bucketName,
                                        @AuthenticationPrincipal AppUser user) {
        String s = awsService.uploadFile(file, bucketName);
        return new ResponseEntity<>(s, HttpStatus.OK);
    }
}
