package sch.personal.backendmovie.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import sch.personal.backendmovie.document.UserDocument;

public interface UserRepository extends MongoRepository<UserDocument, Integer> {
    UserDocument findByUid(Integer uid);
    void deleteByUid(Integer uid);
}
