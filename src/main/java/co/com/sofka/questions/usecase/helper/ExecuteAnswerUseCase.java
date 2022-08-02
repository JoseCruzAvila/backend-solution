package co.com.sofka.questions.usecase.helper;

import reactor.core.publisher.Mono;

@FunctionalInterface
public interface ExecuteUseCase {
    public Mono<String> apply();
}
