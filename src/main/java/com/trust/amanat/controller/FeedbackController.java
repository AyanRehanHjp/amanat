package com.trust.amanat.controller;

import com.trust.amanat.common.constants.AppConstants;
import com.trust.amanat.dto.FeedbackDTO;
import com.trust.amanat.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@RequestMapping("/feedback")
public class FeedbackController {

    private static final Logger logger = LoggerFactory.getLogger(FeedbackController.class);

    @Autowired
    FeedbackService feedbackService;

    @PostMapping("/addFeedback")
    public String addFeedback(@RequestBody FeedbackDTO feedbackDTO) {

        logger.info("Received feedback: name={}, mobile={}",
                feedbackDTO != null ? feedbackDTO.getFullName() : null,
                feedbackDTO != null ? feedbackDTO.getMobile() : null);

        feedbackService.addFeedback(feedbackDTO);
        logger.info("Feedback saved successfully");
        return AppConstants.Message.FEEDBACK_RECEIVED;
    }

    @GetMapping("/allFeedback")
    public List<FeedbackDTO> getAllFeedback(){

        logger.info("Fetching all feedback records");
        List<FeedbackDTO> list = feedbackService.getAllFeedback();
        logger.info("Total feedback records fetched = {}", list != null ? list.size() : 0);
        return list;
    }
}