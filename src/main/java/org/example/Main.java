package org.example;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

public class Main {

    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("hello");

        EntityManager entityManager = entityManagerFactory.createEntityManager();

        EntityTransaction entityTransaction = entityManager.getTransaction();

        entityTransaction.begin();

        try {

            Member  member = new Member();
            member.setUsername("member1");
            member.setAge(10);
            entityManager.persist(member);

            entityManager.flush();
            entityManager.clear();

            List<MemberDTO> result =  entityManager.createQuery("select new jpql.MemberDTO(m.username, m.age) from Member m", MemberDTO.class)
                .getResultList();

            MemberDTO memberDTO = result.get(0);
            System.out.println("memberDTO : " + memberDTO.getUsername());
            System.out.println("memberDTO : " + memberDTO.getAge());

            entityTransaction.commit();
        } catch (Exception e) {
            entityTransaction.rollback();
        } finally {
            entityManager.close(); // 엔티티 매니저를 닫아주는것이 중요함.
        }

        entityManagerFactory.close();
    }
}