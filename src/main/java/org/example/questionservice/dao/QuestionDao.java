package org.example.questionservice.dao;


import org.example.questionservice.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionDao extends JpaRepository<Question, Integer> {

    List<Question> findByCategory(String category);

    @Query(value = "SELECT * FROM question q Where q.category=:category ORDER BY RAND() LIMIT :nOfQuestion", nativeQuery = true)
    List<Question> getRandomQuestionsByCategory(String category,int nOfQuestion);

    List<Integer> findRandomQuestionsByCategory(String categoryName, Integer noOfQuestions);
}
