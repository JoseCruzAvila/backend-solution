package co.com.sofka.questions.usecase.helper;

import co.com.sofka.questions.model.AnswerDTO;
import reactor.core.publisher.Mono;

@FunctionalInterface
public interface ExecuteAnswerUseCase {
    public Mono<String> apply(AnswerDTO answerDTO);
}
