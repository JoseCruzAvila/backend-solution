package co.com.sofka.questions.usecase;

import co.com.sofka.questions.collections.Question;
import co.com.sofka.questions.model.QuestionDTO;
import co.com.sofka.questions.repositories.QuestionRepository;
import co.com.sofka.questions.usecase.helper.ExecuteQuestionUseCase;
import co.com.sofka.questions.usecase.helper.MapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class CreateQuestionUseCase implements ExecuteQuestionUseCase {
    private final QuestionRepository repository;
    private final MapperUtils mapper;

    @Autowired
    public CreateQuestionUseCase(QuestionRepository repository, MapperUtils mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Mono<String> apply(QuestionDTO questionDTO) {
        return repository.save(mapper.mapperToQuestion().apply(questionDTO))
                .map(Question::getId);
    }
}
