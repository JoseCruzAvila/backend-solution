package co.com.sofka.questions.usecase.helper;

import co.com.sofka.questions.model.QuestionDTO;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;

@FunctionalInterface
public interface GetAnswersByUseCase {
    Flux<QuestionDTO> get(String userId);
}
