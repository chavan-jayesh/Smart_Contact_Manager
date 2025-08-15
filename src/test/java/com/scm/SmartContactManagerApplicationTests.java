package com.scm;

import org.springframework.boot.test.context.SpringBootTest;

// import java.util.List;

// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;

// import com.scm.services.EmailService;

// import io.mailtrap.config.MailtrapConfig;
// import io.mailtrap.factory.MailtrapClientFactory;
// import io.mailtrap.model.request.emails.Address;
// import io.mailtrap.model.request.emails.MailtrapMail;

// import com.resend.*;
// import com.resend.core.exception.ResendException;
// import com.resend.services.emails.model.CreateEmailOptions;
// import com.resend.services.emails.model.CreateEmailResponse;

@SpringBootTest
class SmartContactManagerApplicationTests {

	// @Autowired
	// private EmailService emailService;

	// @Test
	// void contextLoads() {
	// }

	// @Test
	// void sendEmailTest(){
	// 	emailService.sendEmail(
	// 		"suvarnachavan7875@gmail.com", 
	// 		"Verify Account : Smart Contact Manager", 
	// 		"verification link");
	// }

	// @Test
	// void testapi(){
	// 	final String TOKEN = "f7e3896310947821a12e8b47efb31c9b";
	// 	final String SENDER_EMAIL = "sender@demomailtrap.co";
	// 	final String RECIPIENT_EMAIL = "jayeshchavan7719@gmail.com";

    
        // final var config = new MailtrapConfig.Builder()
        //         .token(TOKEN)
        //         .build();

        // final var client = MailtrapClientFactory.createMailtrapClient(config);

        // final var mail = MailtrapMail.builder()
        //         .from(new Address(SENDER_EMAIL))
        //         .to(List.of(new Address(RECIPIENT_EMAIL)))
        //         .subject("Hello from Mailtrap Sending!")
        //         .text("Welcome to Mailtrap Sending!")
        //         .build();

        // System.out.println(client.sendingApi().emails().send(mail));

	// }

	// @Test
	// void resendEmailAPI(){

        // Resend resend = new Resend("re_hzbssAF8_9Vs65hWXU6KQAeXHxSPrfizn");

        // CreateEmailOptions  sendEmailRequest = CreateEmailOptions.builder()
        //         .from("scm@resend.dev")
        //         .to("jayeshchavan7719@gmail.com")
        //         .subject("Hello World")
        //         .html("<p>Congrats on sending your <strong>first email</strong>!</p>")
        //         .build();

	// 	try {
        //     CreateEmailResponse data = resend.emails().send(sendEmailRequest);
        //     System.out.println(data.getId());
        // } 
	// 	catch (ResendException e) {
        //     e.printStackTrace();
        // }
	// }


}
