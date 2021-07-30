package study.datajpa.repository;

import study.datajpa.Entity.Member;

import java.util.List;

public interface MemberProjection {
    Long getId();

    String getUsername();

    String getTeamName();
}
