package co.com.sofka.questions.usecase.helper;

import co.com.sofka.questions.model.QuestionDTO;
import reactor.core.publisher.Flux;

@FunctionalInterface
public interface GetQuestionsByUseCase {
    Flux<QuestionDTO> get(String userId, Long page, Long size);
}
