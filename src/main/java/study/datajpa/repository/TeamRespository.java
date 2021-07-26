package study.datajpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.datajpa.Entity.Team;

public interface TeamRespository extends JpaRepository<Team, Long> {

}
