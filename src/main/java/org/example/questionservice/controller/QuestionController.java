package org.example.questionservice.controller;

import jdk.jshell.Snippet;
import org.example.questionservice.model.Question;
import org.example.questionservice.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.example.questionservice.model.Response;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

@RequestMapping("question")
public class QuestionController {
    @Autowired
    QuestionService questionService;

    @GetMapping("allquestions")
    public ResponseEntity<List<Question>> getAllQuestion(){
        return questionService.getAllQuestions();
    }

    @GetMapping("category/{category_name}")
    public ResponseEntity<List<Question>> getQuestionsByCatagory(@PathVariable("category_name") String category){
            return questionService.getAllQuestions(category);
    }

    @PostMapping("addquestion")
    public ResponseEntity<String>  addQuestion(@RequestBody Question question){
        return questionService.addQuestion(question);
    }

    @DeleteMapping("deletequestion/{question_id}")
    public ResponseEntity<String>  deleteQuestionById(@PathVariable("question_id") int id){
        return questionService.deleteQuestionById(id);
    }

    @GetMapping("/generate")
    public ResponseEntity<List<Integer>> getQuestionsForQuiz(@RequestParam String categoryName, @RequestParam Integer noOfQuestions){
        return  questionService.getQuestionsForQuiz(categoryName,noOfQuestions);
    }

    @PostMapping("getScore")
    public RequestEntity<Integer> getScore(@RequestBody List<Response> responses){
        return questionService.getScore(responses);
    }

}
