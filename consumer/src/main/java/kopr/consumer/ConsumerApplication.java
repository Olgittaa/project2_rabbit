package kopr.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import java.util.Objects;

@SpringBootApplication
@Slf4j
@PropertySource("classpath:/application.properties")
public class ConsumerApplication {
    @Autowired
    private Environment env;

    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class, args);
    }

    @Bean
    Queue queue() {
        return new Queue(Objects.requireNonNull(env.getProperty("consumer.queue")));
    }

    @Bean
    Queue allPaymentsQueue() {
        return new Queue("allPayments");
    }

    @Bean
    Queue rechargingQueue() {
        return new Queue(Objects.requireNonNull(env.getProperty("recharging.queue")));
    }

    @Bean
    Exchange exchange() {
        return new TopicExchange("payment");
    }

    @Bean
    Exchange recharging() {
        return new DirectExchange("recharging");
    }

    @Bean
    Binding binding() {
        return BindingBuilder.bind(queue()).to(exchange())
                .with("#." + env.getProperty("consumer.phoneNumber")).noargs();
    }

    @Bean
    Binding allPaymentsBinding() {
        return BindingBuilder.bind(allPaymentsQueue()).to(exchange())
                .with("payment.*").noargs();
    }

    @Bean
    Binding rechargingBinding() {
        return BindingBuilder.bind(rechargingQueue()).to(recharging())
                .with("recharging." + env.getProperty("consumer.phoneNumber")).noargs();
    }

    @Bean
    MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
