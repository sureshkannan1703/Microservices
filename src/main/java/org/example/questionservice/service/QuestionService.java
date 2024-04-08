package org.example.questionservice.service;

import org.example.questionservice.dao.QuestionDao;
import org.example.questionservice.model.Question;
import org.example.questionservice.model.QuestionWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.example.questionservice.model.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {

    @Autowired
    QuestionDao questionDao;
    public ResponseEntity<List<Question>> getAllQuestions(){

        try {
            List<Question> questions_list =  (questionDao.findAll());
            return new ResponseEntity<>(questions_list, HttpStatus.OK);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<List<Question>> getAllQuestions(String catagory){

        try {
            List<Question> catagorized_questions = questionDao.findByCategory(catagory);
            return new ResponseEntity<>(catagorized_questions, HttpStatus.OK);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<String>  addQuestion(Question question) {

        try {
            questionDao.save(question);
            return new ResponseEntity<>("Success",HttpStatus.CREATED);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return ResponseEntity.notFound().build();

    }

    public ResponseEntity<String> deleteQuestionById(int id) {

        try {
            questionDao.deleteById(id);
            return new ResponseEntity<>("deleted",HttpStatus.NO_CONTENT);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<List<Integer>> getQuestionsForQuiz(String categoryName, Integer noOfQuestions){

        List<Integer> questions = questionDao.findRandomQuestionsByCategory(categoryName,noOfQuestions);
        return new ResponseEntity<>(questions,HttpStatus.OK);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(List<Integer> questionIds){

            List<QuestionWrapper> questionWrappers = new ArrayList<>();
            List<Question> questions = new ArrayList<>();

            for(Integer id : questionIds){
                questions.add(questionDao.findById(id).get());
            }

            for(Question question : questions){
                QuestionWrapper wrapper = new QuestionWrapper();
                wrapper.setQuestion_title(question.getQuestion_title());
                wrapper.setId(question.getId());
                wrapper.setOption_1(question.getOption_1());
                wrapper.setOption_2(question.getOption_2());
                wrapper.setOption_3(question.getOption_3());
                wrapper.setOption_4(question.getOption_4());
                questionWrappers.add(wrapper);
            }
            return new ResponseEntity<>(questionWrappers,HttpStatus.OK);
    }

    public ResponseEntity<Integer> getScore(List<Response> responses) {

        int right_answers=0;
        for(Response response : responses){
            Optional<Question> question = questionDao.findById(response.getId());
            if(question.get().getRight_answer().equals(response.getResponse()))
                right_answers++;
        }
        return new ResponseEntity<>(right_answers,HttpStatus.OK);
    }
}
