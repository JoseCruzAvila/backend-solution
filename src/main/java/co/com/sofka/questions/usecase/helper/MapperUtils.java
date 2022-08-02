package co.com.sofka.questions.usecase;

import co.com.sofka.questions.collections.Answer;
import co.com.sofka.questions.collections.Question;
import co.com.sofka.questions.model.AnswerDTO;
import co.com.sofka.questions.model.Category;
import co.com.sofka.questions.model.QuestionDTO;
import co.com.sofka.questions.model.Type;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.function.Function;

@Component
public class MapperUtils {
    public Function<AnswerDTO, Answer> mapperToAnswer() {
        return updateAnswer -> {
          var answer = new Answer();
          answer.setUserId(updateAnswer.getUserId());
          answer.setAnswer(updateAnswer.getAnswer());
          answer.setQuestionId(updateAnswer.getQuestionId());
          answer.setPosition(updateAnswer.getPosition());

          return answer;
        };
    }

    public Function<Answer, AnswerDTO> mapAnswerToDTO() {
        return answer -> new AnswerDTO(answer.getQuestionId(), answer.getUserId(), answer.getAnswer());
    }

    public Function<QuestionDTO, Question> mapperToQuestion() {
        return updateQuestion -> {
            var question = new Question();
            question.setUserId(updateQuestion.getUserId());
            question.setQuestion(updateQuestion.getQuestion());
            question.setType(updateQuestion.getType().name());
            question.setCategory(updateQuestion.getCategory().name());

            return question;
        };
    }

    public Function<Question, QuestionDTO> mapQuestionToDTO() {
        return question -> new QuestionDTO(question.getUserId(), question.getQuestion(), Arrays.stream(Type.values())
                .filter(type -> type.name().equals(question.getType()))
                .findFirst().orElse(), Arrays.stream(Category.values())
                .filter(category -> category.name().equals(question.getCategory()))
                .findFirst().get());
    }
}
