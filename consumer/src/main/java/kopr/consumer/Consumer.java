package kopr.consumer;

import kopr.consumer.db.SuspiciousPaymentRepository;
import kopr.consumer.db.UserRepository;
import kopr.consumer.entities.Payment;
import kopr.consumer.entities.Recharging;
import kopr.consumer.entities.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Slf4j
public class Consumer {

    private final SuspiciousPaymentRepository suspiciousPaymentRepository;
    private final UserRepository userRepository;

    public Consumer(SuspiciousPaymentRepository suspiciousPaymentRepository, UserRepository userRepository) {
        this.suspiciousPaymentRepository = suspiciousPaymentRepository;
        this.userRepository = userRepository;
    }

    @RabbitListener(queues = "${consumer.queue}")
    public void consume(Payment payment) {
        User receiver = userRepository.getUserByPhoneNumber(payment.getReceiver().getPhoneNumber()).orElseThrow();
        receiver.setCredit(receiver.getCredit().add(payment.getAmount()));
        userRepository.save(receiver);
        log.info("{}", payment.toString());
    }

    @RabbitListener(queues = "allPayments")
    public void consumeSuspiciousPayments(Payment payment) {
        if (payment.getAmount().compareTo(new BigDecimal(50)) > 0) {
            suspiciousPaymentRepository.save(payment);
            log.warn("SUSPICIOUS PAYMENT {}", payment.toString());
        }
    }

    @RabbitListener(queues = "${recharging.queue}")
    public void consumeRecharging(Recharging recharging) {
        User receiver = userRepository.getUserByPhoneNumber(recharging.getReceiver().getPhoneNumber()).get();
        receiver.setCredit(receiver.getCredit().add(recharging.getAmount()));
        userRepository.save(receiver);
        log.info("{}", recharging.toString());
    }
}