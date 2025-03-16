package com.sundaydevblog.springrestapitest.controller;

import com.sundaydevblog.springrestapitest.entity.Member;
import com.sundaydevblog.springrestapitest.service.MemberService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/members")
public class MemberController {

    private static final Logger logger = LoggerFactory.getLogger(MemberController.class);
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping
    public List<Member> getAllMembers() {
        return memberService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Member createMember(@Valid @RequestBody Member member) {
        return memberService.save(member);
    }

    @PutMapping("/{id}")
    public Member updateMember(@PathVariable Long id, @Valid @RequestBody Member member) {
        logger.info("Received PUT request for ID: {}", id);
        logger.info("Body received: id={}, firstName={}, lastName={}", 
                    member.getId(), member.getFirstName(), member.getLastName());
        logger.info("Checking if member exists for ID: {}", id);
        boolean exists = memberService.existsById(id);
        logger.info("Member exists: {}", exists);
        if (!exists) {
            logger.info("Throwing 404 for non-existent ID: {}", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Member not found");
        }
        member.setId(id);
        logger.info("Updating member with ID: {}", id);
        return memberService.save(member);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Map<String, Object>> handleResponseStatusException(ResponseStatusException ex) {
        logger.info("Handling ResponseStatusException with status: {}", ex.getStatusCode());
        Map<String, Object> response = Map.of(
            "status", ex.getStatusCode().value(),
            "message", ex.getReason()
        );
        return new ResponseEntity<>(response, ex.getStatusCode());
    }
    @GetMapping(value = "/hello")
    public String sayHi(){
    return "Say Hello Mr sami HFIDH BDIA";
    }
}