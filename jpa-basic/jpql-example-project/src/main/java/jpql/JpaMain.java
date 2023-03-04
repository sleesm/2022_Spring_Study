package jpql;


import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Collection;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
/*          //== jpql 예시 ==/
            Member member = new Member();
            member.setUsername("member1");
            em.persist(member);

            em.flush();
            em.clear();

            List<Member> resultList = em.createQuery("select m from Member m where m.username = :username", Member.class)
                    .setParameter("username", "member1")
                    .getResultList();

            for (Member member1 : resultList) {
                System.out.println("member1 = " + member1.getId());
            }

            Member findMember = resultList.get(0);
            findMember.setAge(20);
            em.flush();
            em.clear();*/

/*          //== 임베디드 값 조회 ==/
            List<Address> resultList1 = em.createQuery("select o.address from Order o", Address.class)
                    .getResultList();*/

/*          //== 여러 값 조회 1 - Object[] ==/
            List resultList2 = em.createQuery("select m.username, m.age from Member m")
                    .getResultList();

            Object o = resultList2.get(0);
            Object[] result = (Object[]) o;
            System.out.println("result[0] = " + result[0]);
            System.out.println("result[1] = " + result[1]);

            //== 여러 값 조회 2 - Object[] ==/
            List<Object[]> resultList3 = em.createQuery("select m.username, m.age from Member m")
                    .getResultList();

            for (Object[] objects : resultList3) {
                System.out.println("objects[0] = " + objects[0]);
                System.out.println("objects[1] = " + objects[1]);
            }

            //== 여러 값 조회 3 - new 명령어 ==/
            List<MemberDTO> resultList4 = em.createQuery("select new jpql.MemberDTO(m.username, m.age) from Member m", MemberDTO.class)
                    .getResultList();

            for (MemberDTO memberDTO : resultList4) {
                System.out.println("memberDTO.getUsername() = " + memberDTO.getUsername());
                System.out.println("memberDTO.getAge() = " + memberDTO.getAge());
            }*/

/*            //== 페이징 ==//
            for(int i = 0; i< 100; i++) {
                Member member = new Member();
                member.setUsername("member" + i);
                member.setAge(i);
                em.persist(member);
            }

            em.flush();
            em.clear();

            List<Member> resultList = em.createQuery("select m from Member m order by m.age desc", Member.class)
                    .setFirstResult(1)
                    .setMaxResults(10)
                    .getResultList();

            System.out.println("resultList.size() = " + resultList.size());
            for (Member member1 : resultList) {
                System.out.println("member1 = " + member1);
            }*/

            Team teamA = new Team();
            teamA.setName("팀A");
            em.persist(teamA);

            Team teamB = new Team();
            teamB.setName("팀B");
            em.persist(teamB);

            Member member1 = new Member();
            member1.setUsername("회원1");//(null);//("member1");
            member1.setAge(10);
            member1.setType(MemberType.ADMIN);
            member1.changeTeam(teamA);
            em.persist(member1);

            Member member2 = new Member();
            member2.setUsername("회원2");
            member2.setAge(10);
            member2.changeTeam(teamA);
            em.persist(member2);

            Member member3 = new Member();
            member3.setUsername("회원3");
            member3.setAge(10);
            member3.changeTeam(teamB);
            em.persist(member3);


            em.flush();
            em.clear();

/*            //Join
            String query = "select m from Member m, Team t where m.username = t.name";
            List<Member> result = em.createQuery(query, Member.class).getResultList();

            //JPQL TYPE
            query = "select m.username, 'HELLO', true from Member m where m.type = jpql.MemberType.ADMIN";
            List<Object[]> resultList = em.createQuery(query).getResultList();

            for (Object[] objects : resultList) {
                System.out.println("objects[0] = " + objects[0]);
                System.out.println("objects[1] = " + objects[1]);
                System.out.println("objects[2] = " + objects[2]);
            }*/

/*            //case문, coalesce, nullif
            String query = "select " +
                            "case when m.age <= 10 then '학생요금'" +
                            "       when m.age >= 60 then '경로요금'" +
                            "       else '일반요금'" +
                            "end"
                    + " from Member m";


            query = "select coalesce(m.username, '이름 없는 회원') from Member m";

            query = "select nullif(m.username, '관리자') from Member m";

            List<String> result = em.createQuery(query, String.class).getResultList();
            for (String s : result) {
                System.out.println("s = " + s);
            }*/

/*            //컬랙션 값 조인
            String query = "select t.members.size from Team t";
            query = "select m.username from Team t join t.members m";
            List result = em.createQuery(query).getResultList();

            for (Object o : result) {
                System.out.println("o = " + o);
            }*/

            /**
             * 1번 쿼리 결과 :
             * 회원1, 팀A(SQL - proxy)
             * 회원2, 팀A(영속성 컨텍스트 - 1차 캐시)
             * 회원3, 팀B(SQL - proxy)
             * ...
             * 회원 100명
             * -> N + 1 번 문제 발생
             *
             * 2번 쿼리 결과 :
             * join fetch 사용
             * -> proxy 가 아닌 실제 entity 반환
             *
             * 3번 쿼리 결과 :
             * 팀A 에 대하여 회원1, 회원2 존재 (결과가 중복된 결과)
             *
             * 4번 쿼리 결과 :
             * distinct로 중복 제거
             */
/*            String query = "select m from Member m"; //1번 쿼리 후 - member.getTeam().getName() 조회 시, N + 1 문제 발생
            query = "select m from Member m join fetch m.team"; //2번 쿼리 - fetch join (N : 1)
            query = "select t from Team  t join fetch t.members"; //3번 쿼리 - collection fetch join (1 : N)
            query = "select distinct t from Team  t join fetch t.members"; //4번 쿼리 - distinct 로 엔티티 중복 제거
            query = "select t from Team t"; //5번 쿼리 - paging 하고 싶을 때 @BatchSize 혹은 persistence 에서 글로벌로 설정

            List<Team> result = em.createQuery(query, Team.class)
                    .setFirstResult(0)
                    .setMaxResults(2)
                    .getResultList();

            for (Team team : result) {
                System.out.println("member = " + team.getName());
                for(Member member : team.getMembers()){
                    System.out.println("member.getUsername() = " + member.getUsername());
                }
            }*/

/*            //namedQuery 사용
            List<Member> result = em.createNamedQuery("Member.findByUserName", Member.class)
                    .setParameter("username", "회원1")
                    .getResultList();

            for (Member member : result) {
                System.out.println("member = " + member);
            }*/

            //벌크 연산
            int resultCount = em.createQuery("update Member m set m.age = 20").executeUpdate();

            System.out.println("resultCount = " + resultCount);

            //영속성 컨텍스트 업데이트 안되어 있음.
            System.out.println("member1.getAge() = " + member1.getAge());
            System.out.println("member2.getAge() = " + member2.getAge());
            System.out.println("member3.getAge() = " + member3.getAge());

            //영속성 컨텍스트 초기화 후 다시 조회 필요.
            em.clear();
            Member findMember = em.find(Member.class, member1.getId());
            System.out.println("findMember.getAge() = " + findMember.getAge());


            tx.commit();
        }catch (Exception e){
            tx.rollback();
            e.printStackTrace();
        }finally {
            em.close();
        }

        emf.close();
    }
}
