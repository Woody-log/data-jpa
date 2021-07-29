package study.datajpa.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import study.datajpa.Entity.Member;
import study.datajpa.dto.MemberDto;
import study.datajpa.repository.MemberRepository;

import javax.annotation.PostConstruct;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;

    @GetMapping("/members/{id}")
    public String findMmeber(@PathVariable("id") Long id) {
        Member member = memberRepository.findById(id).get();
        return member.getUsername();
    }


    // 단순 조회용으로만 사용해라..
    // 엔티티를 변경해도 DB에 변경되지 않는다.
    @GetMapping("/members2/{id}")
    public String findMmeber2(@PathVariable("id") Member member) {
        return member.getUsername();
    }

    @GetMapping("/members")
    public Page<MemberDto> list(@PageableDefault(size = 5) Pageable paseable) {
        Page<Member> page = memberRepository.findAll(paseable);
        Page<MemberDto> map = page.map(MemberDto::new);
        return map;
    }

    @PostConstruct
    public void init() {
        //memberRepository.save(new Member(("userA")));

        for(int i = 0; i < 100; i++) {
            memberRepository.save(new Member("user" +i, i));
        }
    }

}
