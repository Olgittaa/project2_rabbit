package kopr.producer.db;

import kopr.producer.entities.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, ObjectId> {
    Optional<User> getUserByPhoneNumber(String phoneNumber);
}