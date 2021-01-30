package com.example.sbs.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.sbs.dto.Article;
import com.example.sbs.dto.ResultData;
import com.example.sbs.util.Util;

@Controller
public class UsrArticleController {
	private List<Article> articles;
	private int articleLastId;

	public UsrArticleController() {
		articles = new ArrayList<>();
		articleLastId = 0;

		articles.add(new Article(++articleLastId, "2020-12-12", "2020-12-12", "제목1", "내용1"));
		articles.add(new Article(++articleLastId, "2020-12-12", "2020-12-12", "제목1", "내용1"));
	}

	@RequestMapping("/usr/article/detail")
	@ResponseBody
	public Article showDetail(int id) {
		return articles.get(id - 1);
	}

	@RequestMapping("/usr/article/list")
	@ResponseBody
	public List<Article> showList() {
		return articles;
	}

	@RequestMapping("/usr/article/doAdd")
	@ResponseBody
	public ResultData doAdd(String title, String body) {
		String regDate = Util.getNowDateStr();
		String updateDate = regDate;

		articles.add(new Article(++articleLastId, regDate, updateDate, title, body));

		return new ResultData("S-1","성공하였습니다", "id", articleLastId);
	}

	@RequestMapping("/usr/article/doDelete")
	@ResponseBody
	public ResultData doDelete(int id) {
		boolean deleteArticleRs = deleteArticle(id);

		if (deleteArticleRs == false) {

			return new ResultData("F-1", "존재하지 않는 게시물 입니다");
		}

		return new ResultData("S-1", "성공하였습니다", "id", id);

	}

	private boolean deleteArticle(int id) {

		for (Article article : articles) {
			if (article.getId() == id) {
				articles.remove(article);

				return true;
			}
		}

		return false;
	}

	@RequestMapping("/usr/article/doModify")
	@ResponseBody
	public ResultData doModify(int id, String title, String body) {
		Article selarticle = null;

		for (Article article : articles) {
			if (article.getId() == id) {
				selarticle = article;
				break;
			}
		}

		if (selarticle == null) {
			return new ResultData("F-1", String.format("%d번 게시물이 존재하지 않습니다", id));
		}

		selarticle.setTitle(title);
		selarticle.setBody(body);
		selarticle.setUpdateDate(Util.getNowDateStr());

		return new ResultData("S-1", String.format("%d번 게시물을 수정하였습니다", id), "id", id);

	}

}
