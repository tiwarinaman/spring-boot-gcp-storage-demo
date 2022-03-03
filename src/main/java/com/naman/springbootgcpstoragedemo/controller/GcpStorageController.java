package com.naman.springbootgcpstoragedemo.controller;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/gcp")
public class GcpStorageController {

    private final Storage storage;

    @Value("${gcp.bucket.name}")
    private String bucket;

    public GcpStorageController(Storage storage) {
        this.storage = storage;
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadFile(@RequestPart MultipartFile file) throws IOException {
        BlobId blobId = BlobId.of(bucket, file.getOriginalFilename());
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
                .setContentType(file.getContentType())
                .build();
        storage.create(blobInfo, file.getBytes());
        return ResponseEntity.ok("File successfully uploaded");
    }

    @GetMapping("/read/{fileName}")
    public byte[] readFile(@PathVariable("fileName") String fileName) throws IOException {
        Blob blob = storage.get(BlobId.of(bucket, fileName));
        return blob.getContent();
    }

    @DeleteMapping("/delete/{fileName}")
    public String deleteFile(@PathVariable("fileName") String fileName) {
        return storage
                .delete(bucket, fileName) ? "File " + fileName + " deleted" : "Failed to delete file";
    }

}
