package com.example.demo.repository;

import com.example.demo.Model.Classe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClasseRepository extends JpaRepository<Classe, Long> {

    Classe findByLabel(String label);
}
