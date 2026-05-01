package com.trust.amanat.serviceImpl;

import com.trust.amanat.common.constants.AppConstants;
import com.trust.amanat.dto.PostHolderDTO;
import com.trust.amanat.entity.PostHolderEntity;
import com.trust.amanat.repository.PostHolderRepository;
import com.trust.amanat.service.PostHolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Service
public class PostHolderServiceImpl implements PostHolderService {

    @Autowired
    PostHolderRepository postHolderRepository;
    @Override
    public PostHolderEntity addPostHolder( PostHolderDTO postHolderDTO){
        PostHolderEntity postHolderEntity = new PostHolderEntity();
        postHolderEntity.setName(postHolderDTO.getName());
        postHolderEntity.setAddress(postHolderDTO.getAddress());
        postHolderEntity.setContactNo(postHolderDTO.getContactNo());
        postHolderEntity.setPost(postHolderDTO.getPost());
         return postHolderRepository.save(postHolderEntity);
    }


    public List< PostHolderEntity > getAllPostHolders(){
        return postHolderRepository.findAll();
    }

    public String deletePostHolder(Long id){
        if (postHolderRepository.existsById(id)){
            postHolderRepository.deleteById(id);
            return AppConstants.Message.POST_HOLDER_DELETED_SUCCESSFULLY;
        }
        return AppConstants.Message.POST_HOLDER_NOT_FOUND + id;
    }

}
