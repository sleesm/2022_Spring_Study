package hellojpa;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.time.LocalDateTime;
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

/*            //시퀀스
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
            System.out.println("===================================");*/


/*            //양방향 연관관계
            Team team = new Team();
            team.setName("TeamA");
            em.persist(team);

            Member member = new Member();
            member.setUsername("member1");

*//*            //연관관계 편의 메서드 추가하여 생략
            team.getMembers().add(member);
            member.setTeam(team);*//*
            member.changeTeam(team);
            em.persist(member);

//            em.flush();
//            em.clear();

            Member findMember = em.find(Member.class, member.getId());
            List<Member> members = findMember.getTeam().getMembers();
            for (Member m : members) {
                System.out.println("m.getUsername() = " + m.getUsername());
            }*/

/*            //상속관계매핑
            Movie movie = new Movie();
            movie.setDirector("aaaa");
            movie.setActor("bbbb");
            movie.setName("바람과함께사라지다");
            movie.setPrice(10000);

            em.persist(movie);

            em.flush();
            em.clear();

            Movie findMovie = em.find(Movie.class, movie.getId());
            System.out.println("findMovie = " + findMovie);*/

/*            //@@MappedSuperclass
            Member member = new Member();
            member.setUsername("user1");
            member.setCreatedBy("kim");
            member.setCreatedDate(LocalDateTime.now());
            em.persist(member);

            em.flush();
            em.clear();*/

            tx.commit();
        }catch (Exception e){
            tx.rollback();
        }finally {
            em.close();
        }

        emf.close();
    }
}
