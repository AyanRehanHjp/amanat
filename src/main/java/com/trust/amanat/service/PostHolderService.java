package com.trust.amanat.service;

import com.trust.amanat.dto.PostHolderDTO;
import com.trust.amanat.entity.PostHolderEntity;

import java.util.List;

public interface PostHolderService {
    public PostHolderEntity addPostHolder(PostHolderDTO postHolderDTO);
    public List <PostHolderEntity> getAllPostHolders();
    public String deletePostHolder(Long id);




    }
