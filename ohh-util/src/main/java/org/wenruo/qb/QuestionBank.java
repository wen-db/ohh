package org.wenruo.qb;

public class QuestionBank {
    private String question;
    private Answer answer;

    public QuestionBank() {
    }

    public QuestionBank(String question, Answer answer) {
        this.question = question;
        this.answer = answer;
    }

    @Override
    public String toString() {
        return question;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }
}
