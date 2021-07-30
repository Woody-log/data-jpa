package study.datajpa.repository;

import org.springframework.stereotype.Repository;
import study.datajpa.Entity.Member;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class MembmerJpaRepository {

    @PersistenceContext
    EntityManager em;

    public Member save(Member member) {
        em.persist(member);
        return member;
    }

    public void delete(Member member) {
        em.remove(member);
    }

    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    public Optional<Member> findById(Long id) {
        Member member =  em.find(Member.class, id);
        return Optional.ofNullable(member);
    }

    public long count() {
        return em.createQuery("select count(m) from Member m", Long.class).getSingleResult();
    }

    public Member find(Long id) {
        return em.find(Member.class, id);
    }

    public List<Member> findByUsernameAndGreaterThen(String username, int age) {
        return em.createQuery("select m from Member m where m.username =: username and m.age =:age")
                .setParameter("username", username)
                .setParameter("age", age)
                .getResultList();
    }

    public int bulkAgeUpdate(int age) {
        return em.createQuery("update Member m set m.age = m.age + 1 where m.age >=  :age")
                .setParameter("age", age)
                .executeUpdate();
    }
}
