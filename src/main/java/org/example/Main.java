package org.example;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class Main {

    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("hello");

        EntityManager entityManager = entityManagerFactory.createEntityManager();

        EntityTransaction entityTransaction = entityManager.getTransaction();

        entityTransaction.begin();

        try {

            Team team = new Team();
            team.setName("teamA");
            entityManager.persist(team);

            Member  member = new Member();
            member.setUsername("member1");
            member.setAge(10);
            member.setTeam(team);
            member.setType(MemberType.ADMIN);

            entityManager.persist(member);

            entityManager.flush();
            entityManager.clear();

            String query = "select m.username, 'HELLO', true From Member m";


            List<Member> result = entityManager.createQuery(query, Member.class).getResultList();

            entityTransaction.commit();
        } catch (Exception e) {
            entityTransaction.rollback();
        } finally {
            entityManager.close(); // 엔티티 매니저를 닫아주는것이 중요함.
        }

        entityManagerFactory.close();
    }
}