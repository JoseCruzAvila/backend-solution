package co.com.sofka.questions.usecase;

import co.com.sofka.questions.model.QuestionDTO;
import co.com.sofka.questions.repositories.AnswerRepository;
import co.com.sofka.questions.services.QuestionService;
import co.com.sofka.questions.usecase.helper.GetQuestionsByUseCase;
import co.com.sofka.questions.usecase.helper.MapperUtils;
import reactor.core.publisher.Flux;

import java.util.function.Function;

public class QuestionByWithQuestionsUseCase implements GetQuestionsByUseCase {
    private final QuestionService service;
    private final AnswerRepository repository;
    private final MapperUtils mapper;

    public QuestionByWithQuestionsUseCase(QuestionService service, AnswerRepository repository, MapperUtils mapper) {
        this.service = service;
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Flux<QuestionDTO> get(String coincidence, Long page, Long size) {
        return service.findBy(coincidence)
                .map(mapper.mapQuestionToDTO())
                .map(this.getAnswersByQuestion())
                .skip((page - 1) * size)
                .take(size);
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
