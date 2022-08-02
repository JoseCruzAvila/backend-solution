package co.com.sofka.questions.usecase.helper;

import co.com.sofka.questions.model.QuestionDTO;
import reactor.core.publisher.Mono;

public interface GetAnswerUseCase {
    public Mono<QuestionDTO> get(String id);
}
