package com.trust.amanat.controller;

import com.trust.amanat.dto.PostHolderDTO;
import com.trust.amanat.entity.PostHolderEntity;
import com.trust.amanat.service.PostHolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping ("/postholder")
public class PostHolderController {
    @Autowired
    PostHolderService postHolderService;

    @PostMapping("/addPostHolder")
    public ResponseEntity<?> addPostHolder(@RequestBody PostHolderDTO postHolderDTO) {
        PostHolderEntity postHolder = postHolderService.addPostHolder(postHolderDTO);
        if (postHolder != null) {
            return new ResponseEntity<>("post holder added successfully", HttpStatus.OK);
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/getAllPostHolders")
    public ResponseEntity<?> getAllPostHolders() {
        List<PostHolderEntity> postHolderEntity = postHolderService.getAllPostHolders();
        if (postHolderEntity != null) {
            return new ResponseEntity<>(postHolderEntity, HttpStatus.OK);

        }
        return new ResponseEntity<>("Something went error", HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/deletePostHolder/{id}")
    public ResponseEntity<?> deletePostHolder(@PathVariable Long id) {
        String response = postHolderService.deletePostHolder(id);
        if (response != null) {
            return new ResponseEntity<>("Post Holder Deleted Successfully ", HttpStatus.OK);
        }
        return new ResponseEntity<>("Post Holder Not Found with id: " + id, HttpStatus.BAD_REQUEST);
    }
}