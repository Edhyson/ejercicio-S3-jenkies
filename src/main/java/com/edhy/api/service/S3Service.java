/**
 * 
 */
package com.edhy.api.service;


import java.util.List;

import org.springframework.stereotype.Service;

import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.core.sync.ResponseTransformer;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Response;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Object;

@Service
public class S3Service {

    private final S3Client s3Client;
    private final String bucketName = "mi-bucket-prueba";

    public S3Service(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    public void uploadFile(String key, String content) {
        s3Client.putObject(
            PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build(),
            RequestBody.fromString(content)
        );
    }

    public List<S3Object> listFiles() {
        ListObjectsV2Response response = s3Client.listObjectsV2(
            ListObjectsV2Request.builder().bucket(bucketName).build()
        );
        return response.contents();
    }
    
    public byte[] downloadFile(String key) {
        ResponseBytes<GetObjectResponse> response = s3Client.getObject(
            GetObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build(),
            ResponseTransformer.toBytes()
        );
        return response.asByteArray();
    }

}

