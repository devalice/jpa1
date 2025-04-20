package jpabook.jpashop;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class MemberTestRepositoryTest2 {

    @Autowired
    MemberRepositoryTest memberRepository;

    @Test //테스트케이스는 마지막에 rollback 시킴
    @Rollback(false)
    public void testMember() throws Exception{
        //given
        MemberTest member = new MemberTest();
        member.setUsername("memberA");

        //when
        Long saveId = memberRepository.save(member);
        MemberTest findMember = memberRepository.find(saveId);

        //then
        Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
        Assertions.assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
        Assertions.assertThat(findMember).isEqualTo(member); // 같은 영속성 컨텍스트 안에서 같은 id 값을 가지고 있으면 같은 엔티티로 판단
    }
}