package co.com.sofka.questions.usecase.helper;

import co.com.sofka.questions.model.QuestionDTO;
import reactor.core.publisher.Mono;

@FunctionalInterface
public interface ExecuteQuestionUseCase {
    public Mono<String> apply(QuestionDTO questionDTO);
}
