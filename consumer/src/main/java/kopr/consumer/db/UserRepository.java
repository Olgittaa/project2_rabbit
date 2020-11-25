package kopr.consumer.db;

import kopr.consumer.entities.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, ObjectId> {
    Optional<User> getUserByPhoneNumber(String phoneNumber);
}