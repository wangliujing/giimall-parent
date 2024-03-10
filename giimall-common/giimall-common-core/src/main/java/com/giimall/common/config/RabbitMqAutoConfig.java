/**
 * @company 杭州吉喵云科技有限公司(www.gillmall.com)
 * @copyright Copyright (c) 2012-2022
 */
package com.giimall.common.config;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.autoconfigure.amqp.RabbitTemplateConfigurer;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;

/**
 * 类描述与备注 
 *
 * @author wangLiuJing
 * @version Id: RabbitMqConfig, v1.0.0 2022年04月13日 11:18 wangLiuJing Exp $ 
 */
@ConditionalOnClass({RabbitTemplate.class, Channel.class, EnableRabbit.class})
public class RabbitMqAutoConfig {

	@Bean
	public RabbitTemplate rabbitTemplate(RabbitTemplateConfigurer configurer, ConnectionFactory connectionFactory) {
		RabbitTemplate template = new RabbitTemplate();
		template.setMessageConverter(new Jackson2JsonMessageConverter());
		configurer.configure(template, connectionFactory);
		return template;
	}

	@Bean
	SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(SimpleRabbitListenerContainerFactoryConfigurer configurer, ConnectionFactory connectionFactory) {
		SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
		factory.setMessageConverter(new Jackson2JsonMessageConverter());
		configurer.configure(factory, connectionFactory);
		return factory;
	}
}
