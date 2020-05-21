package com.example.demo.repository;

import com.example.demo.Model.Matiere;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatiereRepository extends JpaRepository<Matiere, Long> {

    List<Matiere> findByClassesId(long id);
}
