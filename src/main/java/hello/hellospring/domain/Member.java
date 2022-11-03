package hello.hellospring.domain;

import javax.persistence.*;

@Entity
public class Member {


    /* PK */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /* @Column(name = "username") db안에서 username으로 되어 있을 경우*/
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
