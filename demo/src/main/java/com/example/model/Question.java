package com.example.model;

import java.util.Comparator;

public class Question implements java.io.Serializable , Comparable<Question> , Cloneable, Comparator<Question> {
    public String questionText;
    public String[] options;
    public String answer;
    public Subject subject;

    public Question(String questionText, String[] options, String answer, Subject subject) {
        this.questionText = questionText;
        this.options = options;
        this.answer = answer;
        this.subject = subject;
    }

    public Question(){}

    public Question clone(){
        Question cloned = new Question();
        cloned.questionText = new String(this.questionText);
        cloned.options = this.options.clone();
        cloned.answer = new String(this.answer);
        cloned.subject = this.subject;
        return cloned;
    }

    public boolean checkAnswer(String userAnswer) {
        return this.answer.equalsIgnoreCase(userAnswer);
    }

    public int hashCode() {
        return questionText.hashCode() ^ answer.hashCode() ^ subject.hashCode();
    }

    public boolean equals(Object obj) {
        if (this.hashCode() == obj.hashCode())
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Question other = (Question) obj;
        return questionText.equals(other.questionText) && answer.equals(other.answer) && subject == other.subject;
    }

    @Override
    public int compare(Question o1, Question o2) {
        if (o1 == null || o2 == null)
            throw new IllegalArgumentException("Cannot compare null questions");

        int compareSubject = o1.subject.compareTo(o2.subject);
        int compareQuestionText = o1.questionText.compareTo(o2.questionText);
        int compareAnswer = o1.answer.compareTo(o2.answer);
        return compareSubject != 0 ? compareSubject : (compareQuestionText != 0 ? compareQuestionText : compareAnswer);
    }

    @Override
    public int compareTo(Question o) {
        if (o == null){
            throw new IllegalArgumentException("Cannot compare to null question");
        }
        return compare(this, o);
    }



}
