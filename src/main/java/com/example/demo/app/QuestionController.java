package com.example.demo.app;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Question;
import com.example.demo.service.QuestionService;

@Controller
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    @Qualifier("QuestionServiceImpl")
    QuestionService service;

    //トップページ
	@GetMapping
	public String index(Model model) {
		//全問題数を取得
		int countquiz = service.countQuiz();
		System.out.println("全問題数:" + countquiz);
		model.addAttribute("questionAllCount", countquiz);

		return "index";
	}

	//出題開始時
	@RequestMapping(path = "start", method = RequestMethod.POST)
	public String start(@RequestParam(name = "questionCount", required = false) Integer questionCount, RedirectAttributes redirectAttributes) {

		//出題テーブルを初期化
		service.deleteQuiz();

		//出題順リストを作成
		List<Integer> questionList = new ArrayList<Integer>();
        for(int i=1 ; i <= questionCount ; i++) {
            questionList.add(i);
        }

        //出題テーブルに登録
        service.insertQuiz(questionList);

		return "redirect:/question/quiz";
	}
	@GetMapping("/quiz")
	public String quiz(Model model, RedirectAttributes redirectAttributes) {
       //出題テーブルから解答済以外のidを取得
		List<Integer> questionIdlist = new ArrayList<Integer>();
		questionIdlist = service.idQuiz();

		//全問が終了
		if (questionIdlist.size() == 0) {
			return "redirect:/question/result";
		}

		//出題数を取得
		int countmyquiz = service.countMyquiz();
		model.addAttribute("totalquizNumber","全" + countmyquiz + "問中");

		//現在何問目か
		int nowquizNumber = countmyquiz - questionIdlist.size() + 1;
		model.addAttribute("nowquizNumber",nowquizNumber + "問目");

	    //出題
	    model.addAttribute("quiz",service.selectOne(questionIdlist.get(0)));

	    return "quiz";
	}

	//解答処理
	@RequestMapping(path = "answer", method = RequestMethod.POST)
	public String answer(@RequestParam(name = "id", required = false) Integer id,
			@RequestParam(name = "answer", required = false) Integer answer,
			Model model) {

		System.out.println("answer処理");
		//正解表示
		Question question = service.selectOne(id);
		model.addAttribute("correct", "正解：" + question.getAnswer());
		model.addAttribute("choice", "あなたの解答：" + answer);
		//解説
		model.addAttribute("description", question.getDescription());
		//問題表示
		List<Integer> questionIdlist = new ArrayList<Integer>();
		questionIdlist = service.idQuiz();
		model.addAttribute("quiz",service.selectOne(questionIdlist.get(0)));

		//正誤判定
		if (answer == question.getAnswer()) {
			model.addAttribute("judge", "正解です！");
			model.addAttribute("judgeflg", true);
			//正解にセット
			service.reviewQuiz(id);
		}
		else {
			model.addAttribute("judge", "不正解です");
			model.addAttribute("judgeflg", false);
		}

		//解答済をセット
		service.answerdQuiz(id);

		return "quiz";
	}

	//結果処理
	@GetMapping("/result")
	public String result(Model model) {
        //出題数を取得
		int countmyquiz = service.countMyquiz();
		model.addAttribute("countmyquiz", countmyquiz);

		//正解数を取得
		int countreview = service.countReview();
		model.addAttribute("countreview", countreview);

		//正解率を算出
		double answerrate = (double)countreview / countmyquiz * 100;
		model.addAttribute("answerrate", answerrate);

		if(answerrate >= 95) {
			model.addAttribute("resultmsg", "素晴らしい！！");
		}
		else if(answerrate >= 70 && answerrate < 95) {
			model.addAttribute("resultmsg", "上出来です！");
		}
		else if(answerrate >= 50 && answerrate < 70) {
			model.addAttribute("resultmsg", "なかなかです");
		}
        else if(answerrate >= 25 && answerrate < 50) {
        	model.addAttribute("resultmsg", "もう少しです");
		}
        else {
        	model.addAttribute("resultmsg", "頑張りましょう！");
        }

	   return "result";
	}

}
