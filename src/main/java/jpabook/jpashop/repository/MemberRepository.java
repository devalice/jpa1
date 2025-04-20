package jpabook.jpashop.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import jpabook.jpashop.domain.Member;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    //스프링부트JPA가 아래 어노테이션 없이 @Autowired로도 주입 가능하게 해줌
    //@PersistenceContext
    private final EntityManager em;

    public void save(Member member){
        em.persist(member); //영속성 컨텍스트에 객체 담고, 나중에 트랜잭션이 커밋되는 시점에 DB INSERT
                            //persist한다고 DB INSERT하는 게 아님

    }

    public Member findOne(Long id){
        return em.find(Member.class,id); //(TYPE, PK)
    }

    public List<Member> findAll(){
        return em.createQuery("select m from Member m", Member.class) //FROM의 대상이 엔티티 객체를 대상으로 조회
                .getResultList();
    }

    public List<Member> findByNames(String name){
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }
}
