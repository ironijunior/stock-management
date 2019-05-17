package br.com.ironijunior.stock.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.ironijunior.stock.model.Repo;

public interface RepoRepository extends JpaRepository<Repo, Long> {
}
