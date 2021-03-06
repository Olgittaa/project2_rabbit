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

@Document(collection = "suspisious_payment")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Slf4j
@Data
public class Payment {

    @MongoId
    private ObjectId id;
    private User payer;
    private User receiver;
    private BigDecimal amount;
    private String goal;
    private String date;

    @Override
    public String toString() {
        return "from " + payer.getPhoneNumber() +
                " to " + receiver.getPhoneNumber() +
                " " + amount +
                " for " + goal +
                " at " + date;
    }
}