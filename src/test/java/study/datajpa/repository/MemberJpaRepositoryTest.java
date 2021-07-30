package study.datajpa.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.Entity.Member;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Rollback(false)
class MemberJpaRepositoryTest {

    @Autowired MembmerJpaRepository membmerJpaRepository;

    @Test
    public void testMember() {
        Member member = new Member("member1");
        Member saveMember = membmerJpaRepository.save(member);

        Member findMember = membmerJpaRepository.findById(saveMember.getId()).get();

        assertThat(findMember.getId()).isEqualTo(member.getId());
        assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
    }

    @Test
    public void baiscCRUD() {
        Member member1 = new Member("member1");
        Member member2 = new Member("member2");
        membmerJpaRepository.save(member1);
        membmerJpaRepository.save(member2);

        // 단건 조회
        Member findMember1 = membmerJpaRepository.findById(member1.getId()).get();
        Member findMember2 = membmerJpaRepository.findById(member2.getId()).get();
        assertThat(member1).isEqualTo(findMember1);
        assertThat(member2).isEqualTo(findMember2);

        // 리스트
        List<Member> all = membmerJpaRepository.findAll();
        assertThat(all.size()).isEqualTo(2);

        // 카운트
        long count = membmerJpaRepository.count();
        assertThat(count).isEqualTo(2);

        // 삭제
        membmerJpaRepository.delete(member1);
        membmerJpaRepository.delete(member2);
        long deletedCount = membmerJpaRepository.count();
        assertThat(deletedCount).isEqualTo(0);
    }

    @Test
    public void bulkUpdae() throws Exception {
        //given
        membmerJpaRepository.save(new Member("member1", 10));
        membmerJpaRepository.save(new Member("member1", 119));
        membmerJpaRepository.save(new Member("member1", 20));
        membmerJpaRepository.save(new Member("member1", 21));
        membmerJpaRepository.save(new Member("member1", 40));

        //when
        int resultCount = membmerJpaRepository.bulkAgeUpdate(20);

        //then
        assertThat(resultCount).isEqualTo(3);
     }
}