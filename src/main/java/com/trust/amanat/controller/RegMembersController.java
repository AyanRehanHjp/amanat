package com.trust.amanat.controller;

import com.trust.amanat.entity.MembersEntity;
import com.trust.amanat.service.RegMembersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping ("members")
@RestController
public class RegMembersController {

    @Autowired
    RegMembersService regMembersService;

    @GetMapping  ("/allmembers")
    public List <MembersEntity> getAllMembers() {

        List <MembersEntity> members= regMembersService.getAllMembers();

        return members;
    }

}
