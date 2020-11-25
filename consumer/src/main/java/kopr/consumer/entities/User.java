package kopr.consumer.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.math.BigDecimal;

@Document(collection = "user")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Slf4j
@Data
public class User {
    @MongoId
    private ObjectId id;
    private String phoneNumber;
    private BigDecimal credit;

    @Override
    public String toString() {
        return "User{" +
                "credit=" + credit +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}