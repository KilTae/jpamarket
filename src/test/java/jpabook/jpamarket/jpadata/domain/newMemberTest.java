package jpabook.jpamarket.jpadata.domain;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.constraints.Past;
import java.util.List;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@SpringBootTest
public class newMemberTest {

    @PersistenceContext
    EntityManager em;

    @Test
    @Transactional
    @Rollback(false)
    public void testEntity() {
        newTeam teamA = new newTeam("teamA");
        newTeam teamB = new newTeam("teamB");
        em.persist(teamA);
        em.persist(teamB);

        newMember member1 = new newMember("member1",10,teamA);
        newMember member2 = new newMember("member2",20,teamA);
        newMember member3 = new newMember("member3",30,teamB);
        newMember member4 = new newMember("member4",40,teamB);

        em.persist(member1);
        em.persist(member2);
        em.persist(member3);
        em.persist(member4);

        em.flush();
        em.clear();

        List<newMember> members = em.createQuery("select m from Member m", newMember.class)
                .getResultList();

        for(newMember member : members) {
            System.out.println("member =" + member);
            System.out.println("member..team =" + member.getNewteam());

        }

    }

}