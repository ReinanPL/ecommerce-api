package com.compass.reinan.api_ecommerce.service.impl;

import com.compass.reinan.api_ecommerce.service.SendMailService;
import jakarta.mail.internet.MimeMessage;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class SendMailServiceImpl implements SendMailService {

    private final JavaMailSender javaMailSender;

    @Value("${EMAIL_SENDER}")
    private String sender;

    @Autowired
    public SendMailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }


    @SneakyThrows // Para suprimir a exceção de Erro ao processar o conteúdo do e-mail. Dificilmente ela será lançada, pois o conteúdo do email é controlado.
    @Async
    public void sendMail(String token, String recipientEmail) {

            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

            helper.setFrom(sender);
            helper.setTo(recipientEmail);
            helper.setSubject("Redefinição de Senha - Ecommerce Api");

            String resetPasswordLink = "http://localhost:8080/api/v1/resetPassword/reset?token=" + token;

            String mailBody = "<!DOCTYPE html>"
                    + "<html>"
                    + "<head>"
                    + "    <meta charset=\"UTF-8\">"
                    + "    <style>"
                    + "        body { font-family: Arial, sans-serif; }"
                    + "        .container { padding: 20px; background-color: #f4f4f4; border-radius: 10px; width: 80%; margin: 0 auto; }"
                    + "        .btn { display: inline-block; padding: 10px 20px; color: #fff; background-color: #007bff; text-decoration: none; border-radius: 5px; margin-top: 20px; }"
                    + "        .btn:hover { background-color: #0056b3; }"
                    + "        .footer { margin-top: 40px; font-size: 0.9em; color: #888; }"
                    + "    </style>"
                    + "</head>"
                    + "<body>"
                    + "    <div class=\"container\">"
                    + "        <h2>Olá,</h2>"
                    + "        <p>Você solicitou a redefinição de senha para sua conta na <strong>Ecommerce Api</strong>.</p>"
                    + "        <p>Para redefinir sua senha, por favor, clique no botão abaixo:</p>"
                    + "        <a href=\"" + resetPasswordLink + "\" class=\"btn\">Redefinir Senha</a>"
                    + "        <p class=\"footer\">Este link é válido por 30 minutos. Se você não solicitou esta redefinição, por favor, ignore este e-mail.</p>"
                    + "        <p class=\"footer\">Atenciosamente,<br>Ecommerce Api</p>"
                    + "    </div>"
                    + "</body>"
                    + "</html>";

            helper.setText(mailBody, true);

            javaMailSender.send(mimeMessage);
    }
}