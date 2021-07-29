package study.datajpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import study.datajpa.Entity.Member;
import study.datajpa.dto.MemberDto;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {

    List<Member> findByUsernameAndAgeGreaterThan(String name, int age);

    @Query("select m from Member m where m.username =: username and m.age =: age")
    List<Member> findUser(@Param("username") String username, @Param("age") int age);

    @Query("select m.username from Member m where m.username =: username")
    public List<String> findUserNameList(@Param("username") String username);

    @Query("select new study.datajpa.dto.MemberDto(m.id, m.username, t.name) from Member m join m.team t")
    public List<MemberDto> findMemberDto();

    @Query("select m from Member m where m.username in :names")
    List<Member> findByNames(@Param("names") Collection<String> names);

    List<Member> findListByUsername(String username); // 리스트
    Member findMemberByUsername(String username);   // 단건
    Optional<Member> findOptionalByUsername(String username);   // 단건

    @Query(value ="select m from Member m  left join fetch m.team t", countQuery = "select count(m) from Member m")
    Page<Member> findByAge(int age, Pageable pageable);

    @Query("select m from Member m join fetch m.team")
    List<Member> findMemberFetchJoin();

    @Override
    @EntityGraph(attributePaths = {"team"})
    List<Member> findAll();

    @EntityGraph(attributePaths = {"team"})
    @Query("select m from Member m")
    List<Member> findMemberEntityGraph();

    @EntityGraph(attributePaths = {"team"})
    List<Member> findEntityGraphByUsername(@Param("usenName") String userName);

    @QueryHints(value = @QueryHint(name="org.hibername.readOnly", value="true"))
    Member findReadOnlyByUsername(String username);

    // select for update
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<Member> findLockByUsername(String username);
}
