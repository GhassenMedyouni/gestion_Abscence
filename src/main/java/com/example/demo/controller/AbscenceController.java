package com.example.demo.controller;


import com.example.demo.Model.Abscence;
import com.example.demo.Model.Matiere;
import com.example.demo.repository.AbscenceRepository;
import com.example.demo.repository.EtudiantRepository;
import com.example.demo.repository.MatiereRepository;
import com.example.demo.services.CalculAbs;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

@Controller
@RequestMapping("/abscences")
@AllArgsConstructor
public class AbscenceController {

    AbscenceRepository abscenceRepository;
    MatiereRepository matiereRepository;
    EtudiantRepository etudiantRepository;
    CalculAbs calulServ;


    @GetMapping("add")
    public String showSignUpForm(Abscence abscence, Model model) {
        model.addAttribute("matieres", new ArrayList<Matiere>());
        model.addAttribute("etudiants", etudiantRepository.findAll());
        model.addAttribute("abslist", Arrays.asList("1.5", "3"));
        return "add-abscence";
    }

    @GetMapping("list")
    public String showUpdateForm(Abscence absence, Model model) {
        model.addAttribute("matieres", matiereRepository.findAll());
        model.addAttribute("etudiants", etudiantRepository.findAll());
        model.addAttribute("abscences", abscenceRepository.findAll());
        return "AbscenceIndex";

    }

    @PostMapping("/add")
    public String addabscence(@Valid Abscence abscence, BindingResult result, Model model) {

        System.out.println(abscence.getMatiere().getId());
        if (result.hasErrors()) {
            return "redirect:add";
        }
        abscenceRepository.save(abscence);
        return "redirect:list";
    }

    @GetMapping("edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Abscence abscence = abscenceRepository.findById(id)

                .orElseThrow(() -> new IllegalArgumentException("Invalid class Id:" + id));
        model.addAttribute("abscence", abscence);
        model.addAttribute("matieres", matiereRepository.findAll());
        model.addAttribute("etudiants", etudiantRepository.findAll());
        model.addAttribute("abslist", Arrays.asList("1.5", "3"));

        return "update-abscence";
    }

    @PostMapping("update/{id}")
    public String updateAbscence(@PathVariable("id") long id, @Valid Abscence abscence, BindingResult result,
                                Model model) {
        if (result.hasErrors()) {

            return "update-abscence";
        }
        Abscence abs = abscenceRepository.findById(id).get();
        abscence.setId(id);
        abscence.setEtudiant(abs.getEtudiant());
        abscence.setMatiere(abs.getMatiere());
        abscenceRepository.save(abscence);
        model.addAttribute("abscences", abscenceRepository.findAll());
        return "AbscenceIndex";
    }

    @GetMapping("delete/{id}")
    public String deleteabscence(@PathVariable("id") long id, Model model) {
        Optional<Abscence> abscence = abscenceRepository.findById(id);
        if (abscence.isPresent())
        {
            abscenceRepository.delete(abscence.get());
            model.addAttribute("abscences", abscenceRepository.findAll());
            return "AbscenceIndex";
        }
        else return "redirect:/erreur" ;

    }

    @GetMapping("/envoi")
    void sendMail() {
        try {
            calulServ.sendEmail("ihebhasni6@gmail.com", "MAilTest ", "Mail Mail");
        } catch (MessagingException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
