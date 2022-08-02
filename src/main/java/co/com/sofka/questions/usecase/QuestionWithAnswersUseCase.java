package co.com.sofka.questions.usecase;

import co.com.sofka.questions.model.QuestionDTO;
import co.com.sofka.questions.repositories.AnswerRepository;
import co.com.sofka.questions.repositories.QuestionRepository;
import co.com.sofka.questions.usecase.helper.GetQuestionsByUseCase;
import co.com.sofka.questions.usecase.helper.MapperUtils;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.function.Function;

@Component
public class QuestionWithAnswersUseCase implements GetQuestionsByUseCase {
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final MapperUtils mapper;

    public QuestionWithAnswersUseCase(QuestionRepository questionRepository, AnswerRepository answerRepository, MapperUtils mapper) {
        this.answerRepository = answerRepository;
        this.questionRepository = questionRepository;
        this.mapper = mapper;
    }

    @Override
    public Flux<QuestionDTO> get(String id, Long page, Long size) {
        return questionRepository.findAll()
                .map(mapper.mapQuestionToDTO())
                .map(this.getAnswersByQuestion())
                .skip((page - 1) * size)
                .take(size);
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
