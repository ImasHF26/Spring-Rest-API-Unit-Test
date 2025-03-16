package com.sundaydevblog.springrestapitest.service;

import com.sundaydevblog.springrestapitest.entity.Member;
import com.sundaydevblog.springrestapitest.service.impl.MemberServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MemberServiceTest {

    private MemberServiceImpl memberService;

    @BeforeEach
    void setUp() {
        memberService = new MemberServiceImpl();
    }

    @Test
    void testFindAll() {
        // Ajouter des données pour le test
        memberService.save(new Member("John", "Doe"));
        memberService.save(new Member("Jane", "Doe"));
        assertEquals(2, memberService.findAll().size());
    }

    @Test
    void testGetMemberById() {
        Member member = new Member("John", "Doe");
        memberService.save(member);
        Member found = memberService.getMemberById(member.getId());
        assertNotNull(found);
        assertEquals("John", found.getFirstName());
    }

    @Test
    void testSave() {
        Member member = new Member("John", "Doe");
        Member saved = memberService.save(member);
        assertNotNull(saved.getId());
        assertEquals("John", saved.getFirstName());
    }

    @Test
    void testRemoveMember() {
        Member member = new Member("John", "Doe");
        memberService.save(member);
        memberService.removeMember(member.getId());
        assertEquals(0, memberService.findAll().size());
    }
}