package hellojpa;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
/*            //비영속
            Member member = new Member();
            member.setId(1L);
            member.setName("HelloJPA");

            //영속
            em.persist(member);*/

/*            //1차 캐시
            Member findMember1 = em.find(Member.class, 1L);
            Member findMember2 = em.find(Member.class, 1L);

            //동일성 보장
            System.out.println(findMember1 == findMember2);*/

/*            //쓰기 지연
            Member member1 = new Member(150L, "A");
            Member member2 = new Member(160L, "B");

            em.persist(member1);
            em.persist(member2);

            System.out.println("===================================");*/

/*            //변경 감지
            Member member = em.find(Member.class, 150L);
            member.setName("ZZZZ");*/

/*            //플러쉬
            Member member = new Member(200L, "member200");
            em.persist(member);
            em.flush();
            System.out.println("===================================");*/

            Member member1 = new Member();
            member1.setUsername("C");
            Member member2 = new Member();
            member2.setUsername("C");
            Member member3 = new Member();
            member3.setUsername("C");


            System.out.println("===================================");
            em.persist(member1); //1, 51로 맞춤
            em.persist(member2); //Memory 에서 호출 2
            em.persist(member3); //Memory 에서 호출 3

            System.out.println("===================================");
            System.out.println("member1.getId() = " + member1.getId());
            System.out.println("member2.getId() = " + member2.getId());
            System.out.println("member2.getId() = " + member3.getId());
            System.out.println("===================================");

            tx.commit();
        }catch (Exception e){
            tx.rollback();
        }finally {
            em.close();
        }

        emf.close();
    }
}
