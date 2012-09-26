package edu.buffalo.cse.ubicomp.crowdonline.collector;

public class CorrectAnswer {
	@Override
	public String toString() {
		return "CorrectAnswer [questionId=" + questionId + ", no=" + no
				+ ", choice=" + choice + "]";
	}

	protected int questionId;
	protected int no;
	protected char choice;
	
	public CorrectAnswer(int questionId, int no, char choice) {
		this.questionId = questionId;
		this.choice = choice;
		this.no = no;
	}

	public int getQuestionId() {
		return questionId;
	}

	public int getNo() {
		return no;
	}

	public char getChoice() {
		return choice;
	}
}
