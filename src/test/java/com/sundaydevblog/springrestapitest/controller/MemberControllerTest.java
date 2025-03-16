package com.sundaydevblog.springrestapitest.controller;

import com.sundaydevblog.springrestapitest.entity.Member;
import com.sundaydevblog.springrestapitest.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Arrays;
import static org.hamcrest.Matchers.hasSize; // Import ajouté
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MemberController.class)
public class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemberService memberService;

    @BeforeEach
    void setUp() {
        when(memberService.findAll()).thenReturn(Arrays.asList(
            new Member(1L, "John", "Doe"),
            new Member(2L, "Jane", "Doe")
        ));
        when(memberService.save(any(Member.class))).thenAnswer(invocation -> {
            Member member = invocation.getArgument(0);
            if (member.getId() == null) member.setId(1L);
            return member;
        });
        when(memberService.existsById(1L)).thenReturn(true);
        when(memberService.existsById(999L)).thenReturn(false);
    }

    @Test
    void shouldFetchAllMembers() throws Exception {
        mockMvc.perform(get("/members"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void shouldSaveMember() throws Exception {
        String memberJson = "{\"firstName\":\"John\",\"lastName\":\"Doe\"}";
        mockMvc.perform(post("/members")
               .contentType(MediaType.APPLICATION_JSON)
               .content(memberJson))
               .andExpect(status().isCreated())
               .andExpect(jsonPath("$.id").exists());
    }

    @Test
    void shouldUpdateMember() throws Exception {
        String memberJson = "{\"firstName\":\"John\",\"lastName\":\"Updated\"}";
        mockMvc.perform(put("/members/1")
               .contentType(MediaType.APPLICATION_JSON)
               .content(memberJson))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.id").exists());
    }

    @Test
    void shouldVerifyInvalidUpdateMemberId() throws Exception {
        String memberJson = "{\"firstName\":\"John\",\"lastName\":\"Updated\"}";
        mockMvc.perform(put("/members/999")
               .contentType(MediaType.APPLICATION_JSON)
               .content(memberJson))
               .andExpect(status().isNotFound());
    }
}