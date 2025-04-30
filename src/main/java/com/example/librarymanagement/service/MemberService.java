package com.example.librarymanagement.service;
import com.example.librarymanagement.model.Member;
import com.example.librarymanagement.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService {
    private final MemberRepository memberRepository;

    // Constructor injection
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    // Tüm üyeleri getir
    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    // ID ile üye getir
    public Optional<Member> getMemberById(Long id) {
        return memberRepository.findById(id);
    }

    // Yeni üye oluştur
    public Member createMember(Member member) {
        return memberRepository.save(member);
    }

    // Üye güncelle
    public Member updateMember(Long id, Member member) {
        if (memberRepository.existsById(id)) {
            member.setId(id); // ID güncellenmeli
            return memberRepository.save(member);
        }
        return null;
    }

    // Üye sil
    public boolean deleteMember(Long id) {
        if (memberRepository.existsById(id)) {
            memberRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
