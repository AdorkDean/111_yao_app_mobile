package com.rc.openapi.model;

public class HealthTestVo {
    private String testId;

    private String testTitle;

	public String getTestId() {
		return testId;
	}

	public void setTestId(Integer testId) {
		this.testId = testId.toString();
	}

	public String getTestTitle() {
		return testTitle;
	}

	public void setTestTitle(String testTitle) {
		this.testTitle = testTitle;
	}
  
}