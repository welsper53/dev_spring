package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import java.util.*;

public class MemoryMemberRepository implements MemberRepository{

    private static Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0L;  // 키값

    @Override
    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(), member);

        return member;
    }

    // Optional : 루프를 돌려서 찾는다
    @Override
    public Optional<Member> findById(Long id) {
        // Optional.ofNullable() : 반환값이 null일지라도 우선 클라이언트로 반환한다
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Member> findByName(String name) {
        return store.values().stream()
                // name파라미터와 동일한 객체를 찾은 뒤 반환한다
                .filter(member -> member.getName().equals(name))
                // findAny : 하나라도 찾는다
                .findAny();
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    // 각 메소드 실행 후 데이터들을 클리어하는 메소드 생성
    public void clearStore() {
        store.clear();
    }
}
