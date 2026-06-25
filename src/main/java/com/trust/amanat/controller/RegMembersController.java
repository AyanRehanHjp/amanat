package com.trust.amanat.controller;

import com.trust.amanat.common.constants.AppConstants;
import com.trust.amanat.entity.MembersEntity;
import com.trust.amanat.service.RegMembersService;
import jakarta.validation.Valid;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
    public ResponseEntity<Page<MembersEntity>> getAllMembers(@RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "10") int size) {
        logger.info("calling request to get all members");
        Page <MembersEntity> members= regMembersService.getAllMembers( page, size);
        logger.info("getAllMembers method is called, total members found: {}", members != null ? members.getNumberOfElements() : 0);
        return new ResponseEntity<>(members, members != null && members.hasContent() ? org.springframework.http.HttpStatus.OK : org.springframework.http.HttpStatus.NO_CONTENT);
    }

    @PostMapping("/addMember")
    public ResponseEntity<?> addMember( @Valid @RequestBody MembersEntity member) {
        MembersEntity addedMember = regMembersService.addMember(member);

        if (addedMember != null) {
            logger.info("Member added successfully");
            return ResponseEntity.ok(AppConstants.Message.MEMBER_ADDED);
        }
            logger.error("Failed to add member with memberId: {}", member != null ? member.getMemberId() : null);
        return ResponseEntity.badRequest().body(AppConstants.Message.MEMBER_ADDING_FAILED);
    }

    @PutMapping("/updateMember/{memberId}")
    public ResponseEntity<?> updateMember(@PathVariable String memberId, @RequestBody MembersEntity member) {
        MembersEntity updatedMember = regMembersService.updateMember(memberId, member);
        logger.info("updateMember method called for memberId: {}", memberId);
        return ResponseEntity.ok( updatedMember.getMemberId()+" "+AppConstants.Message.MEMBER_UPDATED );
    }

    @GetMapping("/searchByName")
    public ResponseEntity<?> searchMembersByName(@RequestParam String name) {
        logger.info("searchMembersByName method called with name: {}", name);
        if (name == null || name.trim().isEmpty()) {
            logger.warn("Name parameter is empty");
            return ResponseEntity.badRequest().body(AppConstants.Validation.NAME_REQUIRED);
        }

        List <MembersEntity> members = regMembersService.searchMembersByName(name.trim());
        if(members == null || members.isEmpty()){
            logger.warn("No members found with name: {}", name);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body
                    (AppConstants.Validation.NO_MEMBERS_FOUND_NAME);
        }
        return ResponseEntity.ok(members);
    }

    @GetMapping ("/searchByMobile")
    public ResponseEntity<?> searchMembersByMobile(@RequestParam String mobile) {
        logger.info("searchMembersByMobile method called with mobile: {}", mobile);
        if (mobile == null || mobile.trim().isEmpty()) {
            logger.warn("Mobile parameter is empty");
            return ResponseEntity.badRequest().body(AppConstants.Validation.MOBILE_REQUIRED);
        }

        List <MembersEntity> membersMob = regMembersService.searchByMobile(mobile.trim());
        if( membersMob.isEmpty()){
            logger.warn("No members found with mobile: {}", mobile);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body
                    (AppConstants.Validation.NO_MEMBERS_FOUND_MOBILE);
        }
        return ResponseEntity.ok(membersMob);
    }


}
