import java.util.List;

public class Question {
    String questionTest;
    String correctAnswer;
    List<String> options;
    Subject subject;

    public boolean isCorrect(String answer) {
        return this.correctAnswer.equals(answer);
    }

    public boolean isOption(String option) {
        return this.options.contains(option);
    }

    public List<String> generateCopy(){
        return List.copyOf(this.options);
    }

}
