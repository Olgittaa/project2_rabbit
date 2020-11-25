package kopr.producer;

import kopr.producer.db.UserRepository;
import kopr.producer.entities.Payment;
import kopr.producer.entities.Recharging;
import kopr.producer.entities.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootApplication
@EnableScheduling
@Slf4j
public class ProducerApplication {
    private final UserRepository userRepository;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${producer.phoneNumber}")
    private String phoneNumber;

    public ProducerApplication(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(ProducerApplication.class, args);
    }

    @Bean
    TopicExchange paymentExchange() {
        return new TopicExchange("payment");
    }

    @Bean
    DirectExchange rechargingExchange() {
        return new DirectExchange("recharging");
    }

    @Bean
    MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Scheduled(fixedDelay = 5000)
    public void payMoney() {
        if (!phoneNumber.equals("")) {
            User user = userRepository.getUserByPhoneNumber(phoneNumber).orElseThrow();
            Payment payment = Payment.randomPayment(user, userRepository.findAll());
            payment.setDate(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date()));
            user.setCredit(user.getCredit().subtract(payment.getAmount()));

            if (payment.getAmount().compareTo(user.getCredit()) < 0) {
                userRepository.save(user);

                String routingKey = "payment." + payment.getReceiver().getPhoneNumber();
                log.info("Sending payment: {}", payment.toString());
                rabbitTemplate.convertAndSend("payment", routingKey, payment);
            } else {
                log.error("Not enough money");
            }
        } else {
            recharge();
        }
    }

    public void recharge() {
        Recharging recharging = Recharging.randomRecharging(userRepository.findAll());
        recharging.setDate(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date()));
        String routingKey = "recharging." + recharging.getReceiver().getPhoneNumber();
        log.info("Sending recharging: {}", recharging.toString());
        rabbitTemplate.convertAndSend("recharging", routingKey, recharging);
    }
}