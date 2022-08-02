package co.com.sofka.questions.usecase;

import co.com.sofka.questions.model.QuestionDTO;
import co.com.sofka.questions.repositories.AnswerRepository;
import co.com.sofka.questions.repositories.QuestionRepository;
import co.com.sofka.questions.usecase.helper.GetQuestionUseCase;
import co.com.sofka.questions.usecase.helper.MapperUtils;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Component
public class QuestionByIdWithAnswersUseCase implements GetQuestionUseCase {
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final MapperUtils mapper;

    public QuestionByIdWithAnswersUseCase(QuestionRepository questionRepository, AnswerRepository answerRepository, MapperUtils mapper) {
        this.answerRepository = answerRepository;
        this.questionRepository = questionRepository;
        this.mapper = mapper;
    }

    @Override
    public Mono<QuestionDTO> get(String id) {
        return questionRepository.findById(id)
                .map(mapper.mapQuestionToDTO())
                .map(this.getAnswersByQuestion());
    }

    private Function<QuestionDTO, QuestionDTO> getAnswersByQuestion() {
        return question -> {
            question.setAnswers(answerRepository.findAllByQuestionId(question.getId())
                    .map(mapper.mapAnswerToDTO())
                    .collectList()
                    .block());

            return question;
        };
    }
}
