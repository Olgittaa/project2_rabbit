package kopr.consumer.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Recharging {
    private User receiver;
    private BigDecimal amount;
    private String location;
    private String date;

    @Override
    public String toString() {
        return "to " + receiver.getPhoneNumber() +
                " " + amount +
                " in " + location +
                " at " + date;
    }
}