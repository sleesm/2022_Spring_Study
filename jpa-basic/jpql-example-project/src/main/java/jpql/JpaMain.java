package jpql;


import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
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

            Team team = new Team();
            team.setName("team1");
            em.persist(team);

            Member member = new Member();
            member.setUsername("관리자");//(null);//("member1");
            member.setAge(10);
            member.setType(MemberType.ADMIN);
            member.changeTeam(team);

            em.persist(member);

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
