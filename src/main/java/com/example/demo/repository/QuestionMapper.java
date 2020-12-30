package com.example.demo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.entity.Question;

@Mapper
public interface QuestionMapper {

    // カウント用メソッド
    public int count();

    // １件検索用メソッド
    public Question selectOne(int id);

    // 全件検索用メソッド
    public List<Question> selectMany();

    // 問題数カウント用メソッド
    public int countQuiz();

    // 登録用メソッド
    public int insertQuiz(List<Integer> id);

    // 初期化用メソッド
    public int deleteQuiz();

    //　出題数取得メソッド
    public int countMyquiz();

    // 出題ID取得メソッド
    public List<Integer> idQuiz();

    //　正解・不正解結果メソッド
    public int reviewQuiz(int id);

    //　解答済結果メソッド
    public int answerdQuiz(int id);

    //　正解数取得メソッド
    public int  countReview();
}