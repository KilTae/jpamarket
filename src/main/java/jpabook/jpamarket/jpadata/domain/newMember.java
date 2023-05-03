package jpabook.jpamarket.jpadata.domain;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import java.util.List;
@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id", "username", "age"})
public class newMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;
    private String username;
    private int age;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private newTeam newteam;

    public newMember(String username) {
        this.username = username;
    }

    public newMember(String username, int age) {
        this.username = username;
        this.age = age;
    }

    public newMember(String username,int age, newTeam newteam) {
        this.username = username;
        this.age = age;
        if(newteam !=null) {
            chageTeam(newteam);
        }
    }

    public void chageTeam(newTeam newteam) {
        this.newteam = newteam;
        newteam.getMembers().add(this);
    }





}
