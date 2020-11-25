package kopr.producer.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Document(collection = "payment")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class Payment {

    @MongoId
    private ObjectId id;
    private User payer;
    private User receiver;
    private BigDecimal amount;
    private String goal;
    private String date;

    public Payment(User payer, User receiver, BigDecimal amount, String goal, String date) {
        this.payer = payer;
        this.receiver = receiver;
        this.amount = amount;
        this.goal = goal;
        this.date = date;
    }

    public static Payment randomPayment(User payer, List<User> users) {
        List<String> goals = new ArrayList<>(List.of("coffee", "gift", "mc", "shopping"));
        Collections.shuffle(goals);
        Collections.shuffle(users);
        var goal = goals.get(0);
        BigDecimal amount = BigDecimal.valueOf(Math.random() * 100);
        var receiver = users.get(0);
        if (payer.getPhoneNumber().equals(receiver.getPhoneNumber())) {
            receiver = users.get(1);
        }
        return new Payment(payer, receiver, amount, goal, null);
    }

    @Override
    public String toString() {
        return "from " + payer.getPhoneNumber() +
                " to " + receiver.getPhoneNumber() +
                " " + amount +
                " for " + goal +
                " at " + date;
    }
}