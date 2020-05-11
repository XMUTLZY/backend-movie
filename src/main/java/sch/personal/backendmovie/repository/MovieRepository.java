package sch.personal.backendmovie.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import sch.personal.backendmovie.document.MovieDocument;

public interface MovieRepository extends MongoRepository<MovieDocument, Integer> {
    void deleteByMid(Integer mid);
    MovieDocument findByMid(Integer mid);
}
