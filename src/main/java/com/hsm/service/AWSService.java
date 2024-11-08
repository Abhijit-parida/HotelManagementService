package com.hsm.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
public class AWSService {

    @Autowired
    private AmazonS3 amazonS3;

    // -------------------- FileUpload --------------------- //

    public String uploadFile(MultipartFile file, String bucketName) {
        if (file.isEmpty()) {
            throw new IllegalStateException("Cannot upload empty file");
        }
        try {
            File convFile = new File(System.getProperty("java.io.tmpdir") + "/" + file.getOriginalFilename());
            file.transferTo(convFile);
            try {
                amazonS3.putObject(bucketName, convFile.getName(), convFile);
                return amazonS3.getUrl(bucketName, file.getOriginalFilename()).toString();
            } catch (AmazonS3Exception s3Exception) {
                return "Unable to upload file :" + s3Exception.getMessage();
            }
        } catch (Exception e) {
            throw new IllegalStateException("Failed to upload file", e);
        }
    }

    // ------------------- GetFileLists -------------------- //

    public List<String> listFiles(String bucketName) {
        List<String> fileUrls = new ArrayList<>();

        try {
            ObjectListing objectListing = amazonS3.listObjects(bucketName);
            for (S3ObjectSummary summary : objectListing.getObjectSummaries()) {
                String fileUrl = amazonS3.getUrl(bucketName, summary.getKey()).toString();
                fileUrls.add(fileUrl);
            }
        } catch (AmazonS3Exception e) {
            throw new RuntimeException("Error fetching files from S3: " + e.getMessage());
        }
        return fileUrls;
    }

    // -------------------- FileDelete --------------------- //

    public void deleteFile(String bucketName, String fileUrl) {
        String urlPrefix = String.format("https://%s.s3.%s.amazonaws.com/", bucketName, amazonS3.getRegionName());
        if (!fileUrl.startsWith(urlPrefix)) {
            throw new IllegalArgumentException("Invalid file URL format for the specified bucket.");
        }
        String fileKey = fileUrl.substring(urlPrefix.length());
        amazonS3.deleteObject(new DeleteObjectRequest(bucketName, fileKey));
    }

    public void deleteFilePermanently(String bucketName, String fileKey) {
        ListVersionsRequest listVersionsRequest = new ListVersionsRequest()
                .withBucketName(bucketName)
                .withPrefix(fileKey);
        VersionListing versionListing = amazonS3.listVersions(listVersionsRequest);
        for (S3VersionSummary versionSummary : versionListing.getVersionSummaries()) {
            String versionId = versionSummary.getVersionId();
            amazonS3.deleteVersion(bucketName, versionSummary.getKey(), versionId);
        }
    }
}
