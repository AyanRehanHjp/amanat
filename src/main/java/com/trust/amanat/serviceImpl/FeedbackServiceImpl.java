package com.trust.amanat.serviceImpl;

import com.trust.amanat.dto.FeedbackDTO;
import com.trust.amanat.entity.FeedbackEntity;
import com.trust.amanat.repository.FeedbackRepository;
import com.trust.amanat.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    private static final Logger logger = LoggerFactory.getLogger(FeedbackServiceImpl.class);

    @Autowired
    FeedbackRepository feedbackRepository;

    @Override
    @Transactional
    public String addFeedback(FeedbackDTO feedbackDTO){

        logger.info("Saving feedback: name={}, mobile={}",
                feedbackDTO != null ? feedbackDTO.getFullName() : null,
                feedbackDTO != null ? feedbackDTO.getMobile() : null);

        FeedbackEntity feedbackEntity = new FeedbackEntity();
        feedbackEntity.setFullName(feedbackDTO.getFullName());
        feedbackEntity.setMobile(feedbackDTO.getMobile());
        feedbackEntity.setFeedback(feedbackDTO.getFeedback());

        FeedbackEntity savedFeedback = feedbackRepository.save(feedbackEntity);

        logger.info("Feedback saved successfully with id={}",
                savedFeedback != null ? savedFeedback.getId() : null);

        return "Your Feedback saved";
    }

    @Override
    public List<FeedbackDTO> getAllFeedback() {

        logger.info("Fetching all feedback from DB");

        List<FeedbackDTO> list = feedbackRepository.findAll()
                .stream()
                .map(feed -> {
                    FeedbackDTO dto = new FeedbackDTO();
                    dto.setId(feed.getId());
                    dto.setFullName(feed.getFullName());
                    dto.setMobile(feed.getMobile());
                    dto.setFeedback(feed.getFeedback());
                    return dto;
                })
                .toList();

        logger.info("Total feedback records fetched = {}", list != null ? list.size() : 0);

        return list;
    }
}