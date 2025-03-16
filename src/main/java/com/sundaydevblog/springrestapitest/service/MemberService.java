package com.sundaydevblog.springrestapitest.service;

import com.sundaydevblog.springrestapitest.entity.Member;
import java.util.List;

public interface MemberService {
    List<Member> findAll();
    Member save(Member member);
    boolean existsById(Long id);
    Member getMemberById(Long id);   // Ajouté
    void removeMember(Long id);      // Ajouté
}