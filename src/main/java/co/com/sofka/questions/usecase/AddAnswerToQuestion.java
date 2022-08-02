package co.com.sofka.questions.usecase;

import co.com.sofka.questions.collections.Answer;
import co.com.sofka.questions.model.AnswerDTO;
import co.com.sofka.questions.repositories.AnswerRepository;
import co.com.sofka.questions.usecase.helper.ExecuteAnswerUseCase;
import co.com.sofka.questions.usecase.helper.MapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class AddAnswerToQuestion implements ExecuteAnswerUseCase {
    private final AnswerRepository repository;
    private final MapperUtils mapper;

    @Autowired
    public AddAnswerToQuestion(AnswerRepository repository, MapperUtils mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Mono<String> apply(AnswerDTO answerDTO) {
        return repository.save(mapper.mapperToAnswer().apply(answerDTO))
                .map(Answer::getId);
    }
}
