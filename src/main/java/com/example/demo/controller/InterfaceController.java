package com.example.demo.controller;

import com.example.demo.Model.Etudiant;
import com.example.demo.Model.Matiere;
import com.example.demo.repository.AbscenceRepository;
import com.example.demo.repository.EtudiantRepository;
import com.example.demo.repository.MatiereRepository;
import com.example.demo.services.ClasseService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("/showabs")
@AllArgsConstructor
public class InterfaceController {

    AbscenceRepository abscenceRepository;
    MatiereRepository matiereRepository;
    EtudiantRepository etudiantRepository;
    private ClasseService classeSerivce;

    private class Msg {
        private Long matiere, etudiant;

        public Long getMatiere() {
            return matiere;
        }

        public void setMatiere(Long matiere) {
            this.matiere = matiere;
        }

        public Long getEtudiant() {
            return etudiant;
        }

        public void setEtudiant(Long etudiant) {
            this.etudiant = etudiant;
        }
    }


    @GetMapping
    public String showSelectForm(Matiere matiere, Etudiant etudiant, Model model) {

        model.addAttribute("matieres", matiereRepository.findAll());
        model.addAttribute("etudiants", etudiantRepository.findAll());
        model.addAttribute("absences", abscenceRepository.findAll());
        model.addAttribute("msg", new Msg());
        return "InterfaceIndex";
    }
    @PostMapping
    public String process(@ModelAttribute("msg") Msg msg) {
        return "redirect:/showabs/" + msg.getMatiere() + "/" + msg.getEtudiant();
    }


    @GetMapping("/{matiere}/{etudiant}")
    public String showDetailAbs(@PathVariable Long matiere, @PathVariable Long etudiant, Model model,
                                HttpServletRequest request) {

        model.addAttribute("matiere", matiereRepository.findById(matiere).get());
        Optional<Double> totalabs = abscenceRepository.findNbHeureAbsByEtudiantIdAndMatiereId(etudiant, matiere);
        if (totalabs.isPresent())

            model.addAttribute("totalabs", totalabs.get());
        else
            model.addAttribute("totalabs", 0);

        List<?> listSearch = abscenceRepository.findByMinValeurAndMatiereId(matiere);
        Object[] minstd = null;
        Object[] maxstd = null;
        if (listSearch != null && !listSearch.isEmpty()) {
            minstd = (Object[]) listSearch.get(0);
            System.out.println(Arrays.toString(minstd));
            model.addAttribute("minabs", minstd[minstd.length - 1]);
            maxstd = ((Object[]) listSearch.toArray()[listSearch.toArray().length - 1]);

            model.addAttribute("maxabs", maxstd[maxstd.length - 1]);
        }

        model.addAttribute("etudiant", etudiantRepository.findById(etudiant).get());
        System.out.println(abscenceRepository.findNbHeureAbsByEtudiantIdAndMatiereId(etudiant, matiere));
        model.addAttribute("abscences", abscenceRepository.findByEtudiantIdAndMatiereId(etudiant, matiere));

        return "absdetails";
    }
    @GetMapping("/{classe}")
    public String showDetailAbsMatiere(@PathVariable("classe") Long classe, Model model) {

        model.addAttribute("classeabs", classeSerivce.getClasseAbs(classe));
        return "classeabs";
    }
}
