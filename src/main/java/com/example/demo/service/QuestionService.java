package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Question;

public interface QuestionService {

    public Question selectOne(int id);

    public List<Question> selectMany();

    public int countQuiz();

    public int insertQuiz(List<Integer> id);

    public int deleteQuiz();

    public int countMyquiz();

    public List<Integer> idQuiz();

    public int reviewQuiz(int id);

    public int answerdQuiz(int id);

    public int countReview();

}