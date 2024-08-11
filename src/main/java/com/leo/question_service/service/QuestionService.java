package com.leo.question_service.service;

import com.leo.question_service.dao.QuestionDao;
import com.leo.question_service.model.Question;
import com.leo.question_service.model.QuestionWrapper;
import com.leo.question_service.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {

    @Autowired
    QuestionDao questionDao;

    public ResponseEntity<List<Question>> getAllQuestions() {
        try{
            return new ResponseEntity<>(questionDao.findAll(), HttpStatus.OK);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<Question>> getQuestionsByCategory(String category) {
        try{
            return new ResponseEntity<>(questionDao.findByCategoryId(category),HttpStatus.OK);
        }
        catch (Exception e){

        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<Integer>> getQuestionsForForQuiz(String categoryName, String numQuestions) {
        List<Integer> questions = questionDao.findRandomQuestionsByCategory(categoryName, numQuestions);
        return new ResponseEntity<>(questions,HttpStatus.OK);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(List<Integer> questionsIds) {
        List<Question> questions = questionDao.findByIdIn(questionsIds);
        List<QuestionWrapper> questionWrappers = questions.stream().map(this::getQuestionWrapper).toList();
        return new ResponseEntity<>(questionWrappers,HttpStatus.OK);
    }

    private QuestionWrapper getQuestionWrapper(Question question) {
        return new QuestionWrapper().setId(question.getId())
                .setQuestionTitle(question.getQuestionTitle())
                .setOption1(question.getOption1())
                .setOption2(question.getOption2())
                .setOption3(question.getOption3())
                .setOption4(question.getOption4());
    }

    public ResponseEntity<Integer> getScore(List<Response> responses) {
        int right = 0;
        for (Response response : responses) {
            Question question = questionDao.findById(response.getId()).get();
            if (question.getRightAnswer().equalsIgnoreCase(response.getResponse())) {
                right++;
            }
        }
        return new ResponseEntity<>(right, HttpStatus.OK);
    }

//    public ResponseEntity<String> addQuestion(Question question) {
//        try{
//            questionDao.save(question);
//        }
//        catch (Exception e){
//
//        }
//        return new ResponseEntity<>("",HttpStatus.BAD_REQUEST);
//    }
}
