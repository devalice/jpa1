package jpabook.jpashop;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class MemberRepository {

    //해당 어노테이션 사용 시, springboot가 EntityManager 주입해줌
    @PersistenceContext
    private EntityManager em;
    //entitymanger를 통한 모든 데이터 변경은 항상 트랜잭션 안에서 이루어져야 한다

    public Long save(Member member){
        em.persist(member);
        return member.getId();
    }

    public Member find(Long id){
        return em.find(Member.class, id);
    }

}
