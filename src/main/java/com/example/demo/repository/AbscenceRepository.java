package com.example.demo.repository;

import com.example.demo.Model.Abscence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AbscenceRepository extends JpaRepository<Abscence, Long> {

    List<Abscence> findByEtudiantIdAndMatiereId(Long idE, Long idM);

    @Query( value = "select sum(a.valeur) from Abscence a where a.etudiant.id=?1 and a.matiere.id= ?2 ")
    Optional<Double> findNbHeureAbsByEtudiantIdAndMatiereId(long id, long id2);

    @Query(value = "select   a.matiere ,a.etudiant ,sum(a.valeur) as  mm  from Abscence a "
            + "where a.matiere.id= ?1"
            + " group by a.etudiant,a.matiere "
            + "order by mm asc")
    List<?> findByMinValeurAndMatiereId(Long matiere);
}
