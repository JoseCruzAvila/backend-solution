package co.com.sofka.questions.usecase;

import co.com.sofka.questions.model.QuestionDTO;
import co.com.sofka.questions.repositories.AnswerRepository;
import co.com.sofka.questions.services.QuestionService;
import co.com.sofka.questions.usecase.helper.GetAnswersByUseCase;
import co.com.sofka.questions.usecase.helper.MapperUtils;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;

import java.util.function.Function;

public class QuestionByWithAnswersUseCase implements GetAnswersByUseCase {
    private final QuestionService service;
    private final AnswerRepository repository;
    private final MapperUtils mapper;

    public QuestionByWithAnswersUseCase(QuestionService service, AnswerRepository repository, MapperUtils mapper) {
        this.service = service;
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Flux<QuestionDTO> get(String coincidence) {
        return service.findBy(coincidence)
                .map(mapper.mapQuestionToDTO())
                .map(this.getAnswersByQuestion());
    }

    private Function<QuestionDTO, QuestionDTO> getAnswersByQuestion() {
        return question -> {
            question.setAnswers(repository.findAllByQuestionId(question.getId())
                    .map(mapper.mapAnswerToDTO())
                    .collectList()
                    .block());

            return question;
        };
    }
}
