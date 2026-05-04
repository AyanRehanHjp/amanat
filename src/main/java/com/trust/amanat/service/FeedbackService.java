package com.trust.amanat.service;

import com.trust.amanat.dto.FeedbackDTO;

import java.util.List;

public interface FeedbackService {
     String addFeedback (FeedbackDTO feedbackDTO);
     List<FeedbackDTO> getAllFeedback();


    }
