package hellojpa;


import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

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

/*            //Proxy
            Member member = new Member();
            member.setUsername("hello");

            em.persist(member);

            em.flush();
            em.clear();

            Member findMemberReference = em.getReference(Member.class, member.getId());
            System.out.println("findMemberReference = " + findMemberReference.getClass());
            System.out.println("findMemberReference.getId() = " + findMemberReference.getId());

//            em.detach(findMemberReference);

            System.out.println("findMemberReference.getUsername() = " + findMemberReference.getUsername());
            System.out.println("isLoaded = " + emf.getPersistenceUnitUtil().isLoaded(findMemberReference));*/

/*            //cascade, orphanRemoval
            Child child1 = new Child();
            Child child2 = new Child();

            Parent parent = new Parent();
            parent.addChild(child1);
            parent.addChild(child2);

            em.persist(parent);

            em.flush();
            em.clear();

            Parent findParent = em.find(Parent.class, parent.getId());
            findParent.getChildren().remove(0);*/


/*            //immutable object
            Address address = new Address("city", "street", "100");

            Member member = new Member();
            member.setUsername("hello");
            member.setAddress(address);
            member.setPeriod(new Period());
            em.persist(member);

            Address newAddress = new Address("newcity", address.getStreet(), address.getZipcode());

            member.setAddress(newAddress);*/

/*            //값 타입
            Member member = new Member();
            member.setUsername("hello");
            member.setHomeAddress(new Address("homeCity", "street", "100"));

            //값 타입 컬렉션
            member.getFavoriteFoods().add("치킨");
            member.getFavoriteFoods().add("족발");
            member.getFavoriteFoods().add("피자");

            //값 타입 컬렉션 대안 -> 엔티티 사용
            member.getAddressHistory().add(new AddressEntity("old1", "street", "100"));
            member.getAddressHistory().add(new AddressEntity("old2", "street", "100"));

            em.persist(member);

            em.flush();
            em.clear();*/

/*           //값 타입 컬랙션 조회,수정
            Member findMember = em.find(Member.class, member.getId());
            List<Address> history = findMember.getAddressHistory();
            for (Address address : history) {
                System.out.println("address = " + address);
            }

            Set<String> favoriteFoods = findMember.getFavoriteFoods();
            for (String favoriteFood : favoriteFoods) {
                System.out.println("favoriteFood = " + favoriteFood);
            }

            Address a = findMember.getHomeAddress();
            findMember.setHomeAddress(new Address("newCity", a.getStreet(), a.getZipcode()));

            findMember.getFavoriteFoods().remove("치킨");
            findMember.getFavoriteFoods().add("짜장면");

            //equals()를 기준으로 하기 때문에 제대로 들어가져 있어야 함!
            findMember.getAddressHistory().remove(new Address("old1", "street", "100"));
            findMember.getAddressHistory().add(new Address("newCity1", "street", "100"));*/

            //JPQL
            em.createQuery("select m from Member as m where m.username like '%kim%'", Member.class).getResultList();

            //Criteria
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Member> query = cb.createQuery(Member.class);

            Root<Member> m = query.from(Member.class);

            CriteriaQuery<Member> cq = query.select(m).where(cb.equal(m.get("username"), "kim"));
            em.createQuery(cq).getResultList();

            em.createNativeQuery("select MEMBER_ID, city, street, zipcode from MEMBER").getResultList();


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
