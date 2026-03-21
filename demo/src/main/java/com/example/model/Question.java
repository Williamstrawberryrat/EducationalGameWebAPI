package com.example.model;

public class Question implements java.io.Serializable , Comparable<Question> , Cloneable{
    public String questionText;
    public String[] options;
    private String answer;
    public Subject subject;

    public Question(String questionText, String[] options, String answer, Subject subject) {
        this.questionText = questionText;
        this.options = options;
        this.answer = answer;
        this.subject = subject;
    }

    public Question(){}

    public Question clone() throws CloneNotSupportedException {
        if (canClone())
            throw new CloneNotSupportedException("Cloning not supported for this class");

        Question cloned = new Question();
        cloned.questionText = new String(this.questionText);
        cloned.options = this.options.clone();
        cloned.answer = new String(this.answer);
        cloned.subject = this.subject;
        return cloned;
    }

    @Override
    public int hashCode() {
        return questionText.hashCode() ^ answer.hashCode() ^ subject.hashCode();
    }

    public boolean checkAnswer(String userAnswer) {
        if (userAnswer == null || userAnswer.isEmpty()) {
            throw new IllegalArgumentException("User answer is of an invalid format.");
        }
        return answer.equals(userAnswer);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass())
            return false;

        Question other = (Question) obj;
        return questionText.equals(other.questionText) && answer.equals(other.answer) && subject == other.subject;
    }

    @Override
    public int compareTo(Question o) {
        if (o == null){
            throw new IllegalArgumentException("Cannot compare to null question");
        }
        return compare(this, o);
    }

    private int compare(Question o1, Question o2) {
        if (o1 == null || o2 == null)
            throw new IllegalArgumentException("Cannot compare null questions");

        int compareSubject = o1.subject.compareTo(o2.subject);
        int compareQuestionText = o1.questionText.compareTo(o2.questionText);
        int compareAnswer = o1.answer.compareTo(o2.answer);
        return compareSubject != 0 ? compareSubject : (compareQuestionText != 0 ? compareQuestionText : compareAnswer);
    }

    private boolean canClone(){
        Class<?>[] interfaces = this.getClass().getInterfaces();
        for (Class<?> iface : interfaces) {
            if (iface.equals(Cloneable.class)) {
                return true;
            }
        }
        return false;
    }


}
