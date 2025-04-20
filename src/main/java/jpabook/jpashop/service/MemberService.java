package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    //필드 주입 방식
    //@Autowired
    private final MemberRepository memberRepository;

    //메서드 주입 방식
    //단점: 애플리케이션 런타임 시점에 누군가가 바꿀 수 있음
    /*
    @Autowired
    public void setMemberRepository(MemberRepository memberRepository){
        this.memberRepository = memberRepository;
    }
    */

    //생성자 주입 방식
    //요즘 잘 사용하는 방식
    //테스트 케이스 작성시 명확하게 가능
    //autowired 어노테이션이 없어도 스프링이 자동으로 주입해줌
    //필드는 final로 생성
    /*
    @Autowired
    public MemberService(MemberRepository memberRepository){
        this.memberRepository = memberRepository;
    }
     */

    //회원 가입
    @Transactional
    public Long join(Member member){
        validateDuplicateMember(member);         //중복 회원 검증
        memberRepository.save(member);
        return member.getId(); //영속성컨텍스트에 pk기준으로 저장하기에 값이 꼭 있음
    }

    //아래 메서드가 있어도 동시에 트랜잭션이 들어오면 PK오류가 날 수 있기 때문에
    //DB에도 UNIQUE 제약조건 잡아주기
    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByNames(member.getName());
        if(!findMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    //회원 전체 조회
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    //회원 한 건 조회
    public Member findOne(Long memberId){
        return memberRepository.findOne(memberId);
    }
}
