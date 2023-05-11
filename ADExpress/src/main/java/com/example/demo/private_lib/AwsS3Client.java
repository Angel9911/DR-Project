package com.example.demo.private_lib;

import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.nio.file.Path;

public class AwsS3Client {

    private String bucketName;

    private MultipartFile file;

    private final Region region;

    private S3Client s3Client;

    public AwsS3Client(Region region) {
        this.region = region;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public S3Client buildS3Client(){
        try{
             this.s3Client = S3Client.builder()
                    .region(this.region)
                    .credentialsProvider(DefaultCredentialsProvider.create())
                    .build();

            return s3Client;
        }catch (Exception e) {
            throw new RuntimeException("Failed to create s3 client: " + e.getMessage());
        } finally {
            this.s3Client.close();
        }
    }

    public String uploadImgFile(){

        try{

            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket(this.bucketName)
                    .key(this.file.getOriginalFilename())
                    .build();

            PutObjectResponse response = s3Client.putObject(request, (Path) this.file.getInputStream());
            return "Upload successfully. Tag is: "+response.eTag();
        }catch(Exception e){
            e.printStackTrace();
            return "Image upload failed";
        } finally {
            s3Client.close();
        }
    }
}
