package com.trust.amanat.serviceImpl;

import com.trust.amanat.repository.ExpenditureRepository;
import com.trust.amanat.service.FullReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FullReportServiceImpl implements FullReportService {

    @Autowired
    private ExpenditureRepository expenditureRepository;

    @Override
    public Double getTotalExpenditure(){
        Double total = expenditureRepository.getTotalExpenditure();
        return total != null ? total : 0;
    }
}