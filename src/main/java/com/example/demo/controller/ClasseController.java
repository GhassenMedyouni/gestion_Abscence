package com.example.demo.controller;


import com.example.demo.Model.Classe;
import com.example.demo.Model.Etudiant;
import com.example.demo.Model.Matiere;
import com.example.demo.repository.ClasseRepository;
import com.example.demo.repository.MatiereRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/classes")
public class ClasseController {

    private final ClasseRepository classeRepository;
    private  final MatiereRepository matiereRepository;

    @Autowired
    public ClasseController(ClasseRepository classeRepository, MatiereRepository matiereRepository) {
        this.classeRepository = classeRepository;
        this.matiereRepository = matiereRepository;
    }

    @GetMapping("signup")
    public String showSignUpForm(Classe classe) {
        return "add-class";
    }

    @GetMapping("list")
    public String showUpdateForm(Model model) {
        model.addAttribute("classes", classeRepository.findAll());
        return "ClassIndex";
    }

    @PostMapping("/add")
    public String addclass(@Valid Classe classe, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add-class";
        }
        classeRepository.save(classe);
        return "redirect:list";
    }

    @GetMapping("edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Classe classe = classeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid class Id:" + id));
        model.addAttribute("classe", classe);
        return "update-class";
    }

    @GetMapping("listmatiere/{id}")
    public String showListMat(@PathVariable("id") long id, Model model) {
        Classe classe = classeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid class Id:" + id));
        model.addAttribute("classe", classe);
        model.addAttribute("matieres", classeRepository.findById(id).get().getMatiere());
        return "matiereclass";
    }

    @PostMapping("update/{id}")
    public String updateClass(@PathVariable("id") long id, @Valid Classe classe, BindingResult result, Model model) {
        if (result.hasErrors()) {
            classe.setId(id);
            return "update-class";
        }

        classeRepository.save(classe);
        model.addAttribute("classes", classeRepository.findAll());
        return "ClassIndex";
    }

    @GetMapping("delete/{id}")
    public String deletesubject(@PathVariable("id") long id, Model model) {
        Classe classe = classeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid subject	 Id:" + id));
        classeRepository.delete(classe);
        model.addAttribute("classes", classeRepository.findAll());
        return "ClassIndex";
    }

    @GetMapping("add/{id}")
    public String addMatForm(@PathVariable("id") long id, Model model) {
        Classe classe = classeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid class	 Id:" + id));
        model.addAttribute("classe", classe);
        List<Matiere> listm = new ArrayList<Matiere>();
        matiereRepository.findAll().forEach(e -> {
            if (!classe.getMatiere().contains(e))
                listm.add(e);
        });

        listm.forEach(System.out::println);
        model.addAttribute("matieres", listm);
        model.addAttribute("msg", new Msg());
        return "addSubToClass";

    }

    @PostMapping("addMat")
    public String addMatiereToClasse(@ModelAttribute("msg") Msg msg, BindingResult result, Model model) {

        Optional<Classe> clss = classeRepository.findById(msg.classe);
        System.out.println("class" + msg.classe);
        if (clss.isPresent()) {

            // clss.get().getMatiere().add(matiereRepository.findById(msg.matiere).get()) ;
            System.out.println(clss.get().getMatiere().isEmpty());

            Matiere matiere = matiereRepository.findById(msg.matiere).get();
            matiere.getClasses().add(clss.get());
            matiereRepository.save(matiere);
            classeRepository.save(clss.get());

        }
        return "redirect:listmatiere/"+clss.get().getId();
    }

    @GetMapping("/etudiant")
    @ResponseBody
    public List<Etudiant> getListEtudByClassId(@RequestParam("ide") Long id) {

        System.out.println(id);
        return classeRepository.findById(id).get().getEtudiant() ;

    }

    @Data
    private class Msg {
        private Long matiere, classe;
    }

}
