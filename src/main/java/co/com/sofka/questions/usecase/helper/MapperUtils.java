package co.com.sofka.questions.usecase.helper;

import co.com.sofka.questions.collections.Answer;
import co.com.sofka.questions.collections.Question;
import co.com.sofka.questions.model.AnswerDTO;
import co.com.sofka.questions.model.enums.Category;
import co.com.sofka.questions.model.QuestionDTO;
import co.com.sofka.questions.model.enums.Type;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.function.Function;

@Component
public class MapperUtils {
    public Function<AnswerDTO, Answer> mapperToAnswer(String id) {
        return updateAnswer -> {
          var answer = new Answer();
          answer.setId(id);
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

    public Function<QuestionDTO, Question> mapperToQuestion(String id) {
        return updateQuestion -> {
            var question = new Question();
            question.setId(id);
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
                .findFirst().orElse(Type.OPEN), Arrays.stream(Category.values())
                .filter(category -> category.name().equals(question.getCategory()))
                .findFirst().orElse(Category.SCIENCES));
    }
}
