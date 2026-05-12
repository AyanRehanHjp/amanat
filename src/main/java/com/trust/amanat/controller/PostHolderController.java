package com.trust.amanat.controller;

import com.trust.amanat.common.constants.AppConstants;
import com.trust.amanat.dto.PostHolderDTO;
import com.trust.amanat.entity.PostHolderEntity;
import com.trust.amanat.service.PostHolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@RequestMapping ("/postholder")
public class PostHolderController {
    private static final Logger logger = LoggerFactory.getLogger(PostHolderController.class);
    @Autowired
    PostHolderService postHolderService;

    @PostMapping("/addPostHolder")
    public ResponseEntity<?> addPostHolder(@RequestBody PostHolderDTO postHolderDTO) {
        logger.info("addPostHolder called for name={}", postHolderDTO != null ? postHolderDTO.getName() : null);
        PostHolderEntity postHolder = postHolderService.addPostHolder(postHolderDTO);
        if (postHolder != null) {
            logger.info("PostHolder added successfully with id={}", postHolder.getId());
            return new ResponseEntity<>(AppConstants.Message.POST_HOLDER_ADDED_SUCCESSFULLY, HttpStatus.OK);
        }
        logger.error("Failed to add PostHolder for name={}", postHolderDTO != null ? postHolderDTO.getName() : null);
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/getAllPostHolders")
    public ResponseEntity<?> getAllPostHolders() {
        logger.info("getAllPostHolders called");
        List<PostHolderEntity> postHolderEntity = postHolderService.getAllPostHolders();
        if (postHolderEntity != null) {
            logger.info("getAllPostHolders returned {} post holders", postHolderEntity.size());
            return new ResponseEntity<>(postHolderEntity, HttpStatus.OK);

        }
        logger.error("getAllPostHolders failed to retrieve post holders");
        return new ResponseEntity<>(AppConstants.Message.SOMETHING_WENT_ERROR, HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/deletePostHolder/{id}")
    public ResponseEntity<?> deletePostHolder(@PathVariable Long id) {
        String response = postHolderService.deletePostHolder(id);
        if (response != null) {
            logger.info("deletePostHolder called for id={}, result={}", id, response);
            return new ResponseEntity<>(AppConstants.Message.POST_HOLDER_DELETED_SUCCESSFULLY, HttpStatus.OK);
        }
        logger.error("deletePostHolder failed for id={}", id);
        return new ResponseEntity<>(AppConstants.Message.POST_HOLDER_NOT_FOUND + id, HttpStatus.BAD_REQUEST);
    }
}