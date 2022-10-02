package com.example.cryptogevityalpha;

public class ChatbotQnA {
    private String Question;
    private String Answer;
    public ChatbotQnA(String Q, String A) {
        Question = Q;
        Answer = A;
    }
    public ChatbotQnA(String Q) {
        Question = Q;
        Answer = null;
    }
    public void setAnswer(String A) {
        Answer = A;
    }
    public String getQuestion() {
        return Question;
    }
    public String getAnswer() {
        return Answer;
    }
}
