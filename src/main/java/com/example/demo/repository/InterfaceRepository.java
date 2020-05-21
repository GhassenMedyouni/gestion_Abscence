package com.example.demo.repository;

import com.example.demo.Model.Interface;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InterfaceRepository extends JpaRepository<Interface,Long> {
}
