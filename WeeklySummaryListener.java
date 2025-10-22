package com.oreo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class WeeklySummaryListener {

    private final WebClient webClient;
    private final JavaMailSender mailSender;

    @Value("${GITHUB_MODELS_URL}")
    private String githubUrl;

    @Value("${MODEL_ID}")
    private String modelId;

    @Value("${GITHUB_TOKEN}")
    private String githubToken;

    @Value("${MAIL_USERNAME}")
    private String fromEmail;

    public WeeklySummaryListener(JavaMailSender mailSender) {
        this.mailSender = mailSender;
        this.webClient = WebClient.builder().build();
    }

    @Async
    @EventListener
    public void handleWeeklySummary(WeeklySummaryEvent event) {
        System.out.println("[Listener] Recibido resumen semanal, iniciando proceso LLM + Email...");

        try {
            String prompt = String.format("""
                {
                  "model": "%s",
                  "messages": [
                    {"role": "system", "content": "Eres un analista que escribe resúmenes breves y claros para correos corporativos."},
                    {"role": "user", "content": "Con estos datos: totalUnits=1250, totalRevenue=4800.50, topSku=OREO_DOUBLE, topBranch=Miraflores. Devuelve un resumen ≤120 palabras en español para enviar por email."}
                  ],
                  "max_tokens": 200
                }
            """, modelId);

            String summaryText = webClient.post()
                    .uri(githubUrl)
                    .header("Authorization", "Bearer " + githubToken)
                    .header("Accept", "application/json")
                    .bodyValue(prompt)
                    .retrieve()
                    .bodyToMono(String.class)
                    .onErrorResume(e -> {
                        System.err.println("❌ Error al conectar con GitHub Models: " + e.getMessage());
                        return Mono.just("{\"choices\":[{\"message\":{\"content\":\"Error generando resumen.\"}}]}");
                    })
                    .block();

            System.out.println("✅ Respuesta LLM recibida:\n" + summaryText);

            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo("gerente@oreo.com"); // Puedes parametrizarlo desde el evento
            message.setSubject("🍪 Reporte Semanal Oreo");
            message.setText("Resumen generado:\n\n" + summaryText);

            mailSender.send(message);

            System.out.println("📧 Email enviado correctamente a gerente@oreo.com");

        } catch (Exception e) {
            System.err.println("🚨 Error en listener asíncrono: " + e.getMessage());
            e.printStackTrace();
        }
    }
}