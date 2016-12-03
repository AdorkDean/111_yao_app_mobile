package com.rc.openapi.model;

public class HealthIndexVo {
    private String articleId;

    private String articleTitle;

	public String getArticleId() {
		return articleId;
	}

	public void setArticleId(Integer articleId) {
		this.articleId = articleId.toString();
	}

	public String getArticleTitle() {
		return articleTitle;
	}

	public void setArticleTitle(String articleTitle) {
		this.articleTitle = articleTitle;
	}

	
  
}