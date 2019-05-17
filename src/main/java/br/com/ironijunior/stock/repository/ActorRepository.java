package br.com.ironijunior.stock.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.ironijunior.stock.model.Actor;

public interface ActorRepository extends JpaRepository<Actor, Long> {
	
	@Query(value="SELECT e.actor "
			+ "     FROM Event e "
			+ "    GROUP BY e.actor "
			+ "    ORDER BY COUNT(e) desc, MAX(e.createdAt) DESC, e.actor.login ASC")
	List<Actor> findAllByQtEvents();
	
}
