package study.datajpa.Entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id", "name", "age"})
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    private String name;
    private int age;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name= "team_id")
    private Team team;


    public Member(String name) {
        this.name = name;
    }

    public void chageTeam(Team team) {
        this.team = team;
        team.getMembers().add(this);
    }
}
