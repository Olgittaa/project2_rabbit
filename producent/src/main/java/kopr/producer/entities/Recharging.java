package kopr.producer.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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

    public static Recharging randomRecharging(List<User> users) {
        List<String> locations = new ArrayList<>(List.of("nemocnica", "hlavna", "dom umenia", "galeria"));

        Collections.shuffle(locations);
        Collections.shuffle(users);
        var location = locations.get(0);
        BigDecimal amount = BigDecimal.valueOf(Math.random() * 100);
        var receiver = users.get(0);
        return new Recharging(receiver, amount, location, null);
    }

    @Override
    public String toString() {
        return "to " + receiver.getPhoneNumber() +
                " " + amount +
                " in " + location +
                " at " + date;
    }
}