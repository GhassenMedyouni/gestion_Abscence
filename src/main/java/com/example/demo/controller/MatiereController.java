package com.example.demo.controller;


import com.example.demo.Model.Matiere;
import com.example.demo.repository.MatiereRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/matieres")
public class MatiereController {

    private final MatiereRepository matiereRepository;

    @Autowired

    public MatiereController(MatiereRepository matiereRepository) {

        this.matiereRepository = matiereRepository;
    }

    @GetMapping("signup")
    public String showSignUpForm(Matiere matiere) {
        return "add-subject";
    }


    @GetMapping("list")
    public String showUpdateForm(Model model) {
        model.addAttribute("matieres", matiereRepository.findAll());
        return "SubjectIndex";
    }

    @PostMapping("/add")
    public String addmatieres(@Valid Matiere matiere, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add-subject";
        }
        matiereRepository.save(matiere);
        return "redirect:list";
    }

    @GetMapping("edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Matiere matiere = matiereRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid subject Id:" + id));
        model.addAttribute("matiere", matiere);
        return "update-subject";
    }

    @PostMapping("update/{id}")
    public String updateSubject(@PathVariable("id") long id, @Valid Matiere matiere, BindingResult result,
                                Model model) {
        if (result.hasErrors()) {
            matiere.setId(id);
            return "update-subject";
        }

        matiereRepository.save(matiere);
        model.addAttribute("matieres", matiereRepository.findAll());
        return "SubjectIndex";
    }

    @GetMapping("delete/{id}")
    public String deletesubject(@PathVariable("id") long id, Model model) {
        Matiere matiere = matiereRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid subject	 Id:" + id));
        matiereRepository.delete(matiere);
        model.addAttribute("matieres", matiereRepository.findAll());
        return "SubjectIndex";
    }

}
