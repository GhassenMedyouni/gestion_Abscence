package com.example.demo.services;

import com.example.demo.Model.Etudiant;
import com.example.demo.repository.AbscenceRepository;
import com.example.demo.repository.EtudiantRepository;
import com.example.demo.repository.MatiereRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Optional;
import java.util.Properties;

@Component
@AllArgsConstructor
@Data
public class CalculAbs {

    AbscenceRepository abscenceRepository;
    EtudiantRepository etudiantRepository;
    MatiereRepository matiereRepository ;
    @Scheduled(cron = "45 11 20 * 1-6,9-12 Wed")

    void Calcul() {
        System.out.println("Calcul des abscences pour toutes les etudiants");

        Iterable<Etudiant> etudiants = etudiantRepository.findAll();
        etudiants.forEach(e -> {

            matiereRepository.findByClassesId(e.getClasse().getId()).forEach(m->{


                Optional<Double> nbabs = abscenceRepository.findNbHeureAbsByEtudiantIdAndMatiereId(e.getId(), m.getId());
                if (nbabs.isPresent() & m.getNombre_heure() * 3 / 10 < nbabs.orElse(0.0)) {
                    try {
                        sendEmail(e.getEmail(), "Elimination ",
                                "elimene en matiere " + m.getLabel() + "vous avez " + nbabs.get() + "heure d'abscences");
                    } catch (MessagingException | IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }}

            });

        });
        System.out.println("finish ..... !");
    }

    public boolean sendEmail(String mail, String subject, String mailText)
            throws AddressException, MessagingException, IOException {

        System.out.println("send main to " + mail);

        final String username = "ghassen.medyounigmail.com"; // enter your mail id
        final String password = "Mkfrlrtb13457396";// enter ur password

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("ghassen.medyouni@gmail.com")); // same email id
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mail));
            message.setSubject(subject);
            message.setText(mailText);

            Transport.send(message);
            System.out.println("Done");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

        return true;

    }
}
