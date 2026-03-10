package com.trust.amanat.controller;

import com.trust.amanat.entity.MembersEntity;
import com.trust.amanat.service.RegMembersService;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping ("members")
@RestController
public class RegMembersController {
    public static final Logger logger = LoggerFactory.getLogger(RegMembersController.class);
    @Autowired
    RegMembersService regMembersService;

    @GetMapping  ("/allmembers")
    public List <MembersEntity> getAllMembers() {
        logger.info("calling request to get all members");
        List <MembersEntity> members= regMembersService.getAllMembers();
        logger.info("getAllMembers method is called, total members found: {}", members != null ? members.size() : 0);
        return members;
    }

}
