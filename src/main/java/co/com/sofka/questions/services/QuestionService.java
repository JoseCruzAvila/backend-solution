package co.com.sofka.questions.services;

import co.com.sofka.questions.collections.Question;
import org.springframework.data.domain.Example;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class QuestionService {
    private final ReactiveMongoTemplate template;

    public QuestionService(ReactiveMongoTemplate template) {
        this.template = template;
    }

    public Flux<Question> findBy(String coincidence) {
        var match = Aggregation.match(Criteria.where("category").alike(Example.of(coincidence))
                .orOperator(Criteria.where("type").alike(Example.of(coincidence)))
                .orOperator(Criteria.where("question").alike(Example.of(coincidence))));

        return template.aggregate(Aggregation.newAggregation(match), "Question", Question.class);
    }
}
