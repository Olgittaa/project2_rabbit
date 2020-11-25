package kopr.consumer.db;

import kopr.consumer.entities.Payment;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SuspiciousPaymentRepository extends MongoRepository<Payment, ObjectId> {
}