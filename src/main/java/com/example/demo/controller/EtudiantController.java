package com.example.demo.controller;

import com.example.demo.Model.Classe;
import com.example.demo.Model.Etudiant;
import com.example.demo.Model.Matiere;
import com.example.demo.repository.ClasseRepository;
import com.example.demo.repository.EtudiantRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping(value = { "/","/etudiants/"})
public class EtudiantController {

    private  final EtudiantRepository etudiantRepository;
    private final ClasseRepository classeRepository;

    public EtudiantController(EtudiantRepository etudiantRepository, ClasseRepository classeRepository) {
        this.etudiantRepository = etudiantRepository;
        this.classeRepository = classeRepository;
    }


    @GetMapping("signup")
    public String showSignUpForm(Etudiant etudiant, Model model) {

        model.addAttribute("classes", classeRepository.findAll());

        return "add-student";
    }

    @GetMapping("list")
    public String showUpdateForm(Model model) {
        model.addAttribute("classes", classeRepository.findAll());
        model.addAttribute("classe", new Classe());

        model.addAttribute("etudiants", etudiantRepository.findAll());
        return "index";
    }

    @PostMapping("/add")
    public String addEtudiants(@Valid Etudiant etudiant, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add-student";
        }

        etudiantRepository.save(etudiant);
        return "redirect:list";
    }

    @GetMapping("edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Etudiant etudiant = etudiantRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid student Id:" + id));
        model.addAttribute("classes", classeRepository.findAll());

        model.addAttribute("etudiant", etudiant);
        return "update-student";
    }

    @PostMapping("update/{id}")
    public String updateStudent(@PathVariable("id") long id, @Valid Etudiant etudiant, BindingResult result,
                                Model model) {
        if (result.hasErrors()) {
            etudiant.setId(id);
            return "update-student";
        }

        etudiantRepository.save(etudiant);
        model.addAttribute("etudiants", etudiantRepository.findAll());
        return "index";
    }


    @GetMapping("delete/{id}")
    public String deleteStudent(@PathVariable("id") long id, Model model) {
        Etudiant etudiant = etudiantRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid etudiant	 Id:" + id));
        etudiantRepository.delete(etudiant);
        model.addAttribute("etudiants", etudiantRepository.findAll());
        return "index";
    }

    @GetMapping("/matieres")
    @ResponseBody
    public List<Matiere> getListMatiereByEtudId(@RequestParam("ide") Long id) {

        System.out.println(id);
        Etudiant  etudiant=etudiantRepository.findById(id).get() ;

        return etudiant.getClasse().getMatiere() ;
    }

}
