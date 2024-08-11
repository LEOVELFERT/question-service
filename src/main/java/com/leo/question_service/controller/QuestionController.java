package com.leo.question_service.controller;

import com.leo.question_service.model.Question;
import com.leo.question_service.model.QuestionWrapper;
import com.leo.question_service.model.Response;
import com.leo.question_service.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/question")
@RestController
public class QuestionController {
    @Autowired
    private QuestionService questionService;

    @GetMapping("/allQuestions")
    public ResponseEntity<List<Question>> getAllQuestions(){
        return questionService.getAllQuestions();
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<Question>> getQuestionsByCategory(@PathVariable String categoryId){
        return questionService.getQuestionsByCategory(categoryId);
    }

//    @PostMapping("/add")
//    public ResponseEntity<String> addQuestion(@RequestBody Question question){
//        return questionService.addQuestion(question);
//    }

    //generate
    //get the question by question id
    //we need get score

    @GetMapping("/generate")
    public ResponseEntity<List<Integer>> getQuestionsForQuiz(@RequestParam String categoryName
            ,@RequestParam String numQuestions){
        return questionService.getQuestionsForForQuiz(categoryName,numQuestions);
    }

    @PostMapping("/getQuestions")
    public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(@RequestBody List<Integer> questionsIds){
        return questionService.getQuestionsFromId(questionsIds);
    }


    //SCORE
@PostMapping("/score")
    public ResponseEntity<Integer> getScore(@RequestBody List<Response> responses){
        return questionService.getScore(responses);
    }
}
