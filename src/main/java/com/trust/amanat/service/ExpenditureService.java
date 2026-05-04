package com.trust.amanat.service;


import com.trust.amanat.dto.ExpenditureDTO;
import com.trust.amanat.entity.ExpenditureEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ExpenditureService  {
    public List <ExpenditureEntity> getAllExpenditures();
    public String addExpenditure(ExpenditureDTO dto, MultipartFile file) ;
}
