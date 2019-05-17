package br.com.ironijunior.stock.service.interfaces;

import java.util.List;

import br.com.ironijunior.stock.exception.NotFoundException;
import br.com.ironijunior.stock.model.Actor;
import br.com.ironijunior.stock.model.Event;

public interface GithubApiRestService {

	void deleteAllEvents();

	void createEvent(Event event) throws NotFoundException;

	List<Event> getAllEvents();

	List<Event> getEventsFromActor(Long actorId);

	void updateActorAvatar(Actor actor);

	List<Actor> getAllActors();

	List<Actor> getActorsByMaximumStreak();
	
	
}
