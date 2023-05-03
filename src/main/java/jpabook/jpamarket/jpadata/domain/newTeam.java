package jpabook.jpamarket.jpadata.domain;


import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id", "name"})
public class newTeam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id")
    private Long id;
    private String name;

    @OneToMany(mappedBy = "newteam")
    List<newMember> members = new ArrayList<>();

    public newTeam(String name) {
        this.name = name;
    }
}
