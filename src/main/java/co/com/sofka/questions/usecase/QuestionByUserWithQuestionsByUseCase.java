package co.com.sofka.questions.usecase;

import co.com.sofka.questions.model.QuestionDTO;
import co.com.sofka.questions.repositories.AnswerRepository;
import co.com.sofka.questions.repositories.QuestionRepository;
import co.com.sofka.questions.usecase.helper.GetAnswersByUseCase;
import co.com.sofka.questions.usecase.helper.MapperUtils;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;

import java.util.function.Function;

public class QuestionByUserWithAnswersByUseCase implements GetAnswersByUseCase {
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final MapperUtils mapper;

    public QuestionByUserWithAnswersByUseCase(QuestionRepository questionRepository, AnswerRepository answerRepository, MapperUtils mapper) {
        this.answerRepository = answerRepository;
        this.questionRepository = questionRepository;
        this.mapper = mapper;
    }

    @Override
    public Flux<QuestionDTO> get(String userId, Pageable pageable) {
        return questionRepository.findByUserId(userId, pageable)
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
