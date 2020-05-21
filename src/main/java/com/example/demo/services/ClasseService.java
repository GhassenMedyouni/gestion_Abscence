package com.example.demo.services;


import com.example.demo.Model.Classe;
import com.example.demo.Model.Etudiant;
import com.example.demo.Model.Matiere;
import com.example.demo.repository.ClasseRepository;
import com.example.demo.repository.EtudiantRepository;
import com.example.demo.repository.MatiereRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ClasseService {

    private ClasseRepository classeRepository;
    private EtudiantRepository etudiantRepository ;
    private MatiereRepository matiereRepository ;

    public void save(Classe classe) {
        classeRepository.save(classe);
    }

    public ClasseDTO getClasseAbs(Long classe) {
        ClasseDTO classeDTO=new ClasseDTO();
        classeDTO.etudiants=etudiantRepository.findByClasseId(classe);
        classeDTO.matieres=matiereRepository.findByClassesId(classe);
        // TODO Auto-generated method stub
        return classeDTO;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    class ClasseDTO {

        List<Etudiant> etudiants ;
        List<Matiere> matieres ;

        public List<Etudiant> getEtudiants() {
            return etudiants;
        }

        public void setEtudiants(List<Etudiant> etudiants) {
            this.etudiants = etudiants;
        }

        public List<Matiere> getMatieres() {
            return matieres;
        }

        public void setMatieres(List<Matiere> matieres) {
            this.matieres = matieres;
        }
    }

}
