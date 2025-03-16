package com.sundaydevblog.springrestapitest.service.impl;

import com.sundaydevblog.springrestapitest.entity.Member;
import com.sundaydevblog.springrestapitest.service.MemberService;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class MemberServiceImpl implements MemberService {

    private List<Member> members = new ArrayList<>();
    private Long nextId = 1L;

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(members);
    }

    @Override
    public Member save(Member member) {
        if (member.getId() == null) {
            member.setId(nextId++);
            members.add(member);
        } else {
            members.removeIf(m -> m.getId().equals(member.getId()));
            members.add(member);
        }
        return member;
    }

    @Override
    public boolean existsById(Long id) {
        return members.stream().anyMatch(m -> m.getId().equals(id));
    }

    @Override
    public Member getMemberById(Long id) {
        return members.stream()
                      .filter(m -> m.getId().equals(id))
                      .findFirst()
                      .orElse(null);
    }

    @Override
    public void removeMember(Long id) {
        members.removeIf(m -> m.getId().equals(id));
    }
}