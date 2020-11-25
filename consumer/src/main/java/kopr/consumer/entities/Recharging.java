package kopr.consumer.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Slf4j
@Data
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