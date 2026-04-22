package com.trust.amanat.controller;

import com.trust.amanat.dto.PostHolderDTO;
import com.trust.amanat.entity.MembersEntity;
import com.trust.amanat.entity.PostHolderEntity;
import com.trust.amanat.service.RegMembersService;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping("/addMember")
    public ResponseEntity<?> addMember(@RequestBody MembersEntity member) {
        MembersEntity addedMember = regMembersService.addMember(member);

        if (addedMember != null) {
            return ResponseEntity.ok("Member added successfully");
        }

        return ResponseEntity.badRequest().body("Failed to add member");
    }
    @PutMapping("/updateMember/{memberId}")
    public ResponseEntity<?> updateMember(@PathVariable String memberId, @RequestBody MembersEntity member) {
        MembersEntity updatedMember = regMembersService.updateMember(memberId, member);
        return ResponseEntity.ok( updatedMember.getMemberId()+ "Member updated successfully" );
    }

}
