package com.trust.amanat.controller;

import com.trust.amanat.common.constants.AppConstants;
import com.trust.amanat.entity.MembersEntity;
import com.trust.amanat.service.RegMembersService;
import jakarta.validation.Valid;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
    public Page<MembersEntity> getAllMembers(@RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "10") int size) {
        logger.info("calling request to get all members");
        Page <MembersEntity> members= regMembersService.getAllMembers( page, size);
        logger.info("getAllMembers method is called, total members found: {}", members != null ? members.getNumberOfElements() : 0);
        return members;
    }
    @PostMapping("/addMember")
    public ResponseEntity<?> addMember( @Valid @RequestBody MembersEntity member) {
        MembersEntity addedMember = regMembersService.addMember(member);

        if (addedMember != null) {
            return ResponseEntity.ok(AppConstants.Message.MEMBER_ADDED);
        }

        return ResponseEntity.badRequest().body(AppConstants.Message.MEMBER_ADDING_FAILED);
    }
    @PutMapping("/updateMember/{memberId}")
    public ResponseEntity<?> updateMember(@PathVariable String memberId, @RequestBody MembersEntity member) {
        MembersEntity updatedMember = regMembersService.updateMember(memberId, member);
        return ResponseEntity.ok( updatedMember.getMemberId()+" "+AppConstants.Message.MEMBER_UPDATED );
    }

}
