package com.rc.openapi.model;

public class IssueVo {
	private String  topicId;
	private String topicTitle;
	private String topicScore;
	public String getTopicId() {
		return topicId;
	}
	public void setTopicId(Integer topicId) {
		this.topicId = topicId.toString();
	}
	public String getTopicTitle() {
		return topicTitle;
	}
	public void setTopicTitle(String topicTitle) {
		this.topicTitle = topicTitle;
	}
	public String getTopicScore() {
		return topicScore;
	}
	public void setTopicScore(Integer topicScore) {
		this.topicScore = topicScore.toString();
	}
	
}
