package com.scm;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.scm.services.EmailService;

import io.mailtrap.config.MailtrapConfig;
import io.mailtrap.factory.MailtrapClientFactory;
import io.mailtrap.model.request.emails.Address;
import io.mailtrap.model.request.emails.MailtrapMail;

@SpringBootTest
class SmartContactManagerApplicationTests {

	@Autowired
	private EmailService emailService;

	@Test
	void contextLoads() {
	}

	@Test
	void sendEmailTest(){
		emailService.sendEmail(
			"jayeshchavan7719@gmail.com", 
			"Verify Account : Smart Contact Manager", 
			"verification link");
	}

	@Test
	void testapi(){
		final String TOKEN = "f7e3896310947821a12e8b47efb31c9b";
		final String SENDER_EMAIL = "sender@demomailtrap.co";
		final String RECIPIENT_EMAIL = "jayeshchavan7719@gmail.com";

    
        final var config = new MailtrapConfig.Builder()
                .token(TOKEN)
                .build();

        final var client = MailtrapClientFactory.createMailtrapClient(config);

        final var mail = MailtrapMail.builder()
                .from(new Address(SENDER_EMAIL))
                .to(List.of(new Address(RECIPIENT_EMAIL)))
                .subject("Hello from Mailtrap Sending!")
                .text("Welcome to Mailtrap Sending!")
                .build();

        System.out.println(client.sendingApi().emails().send(mail));

	}

}
