package br.com.ironijunior.stock.service;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.com.ironijunior.stock.exception.BadRequestException;
import br.com.ironijunior.stock.exception.NotFoundException;
import br.com.ironijunior.stock.model.Actor;
import br.com.ironijunior.stock.model.Event;
import br.com.ironijunior.stock.repository.ActorRepository;
import br.com.ironijunior.stock.repository.EventRepository;
import br.com.ironijunior.stock.repository.RepoRepository;
import br.com.ironijunior.stock.service.interfaces.GithubApiRestService;
import br.com.ironijunior.stock.util.MapUtil;

@Service
public class GithubApiRestServiceImpl implements GithubApiRestService {

	@Autowired
	private EventRepository eventRepo;
	@Autowired
	private ActorRepository actorRepo;
	@Autowired
	private RepoRepository repoRepo;
	
	@Override
	public void deleteAllEvents() {
		eventRepo.deleteAll();
	}

	@Override
	public void createEvent(Event event) throws NotFoundException {
		if(eventRepo.exists(event.getId())) {
			throw new NotFoundException();
		}
		
		actorRepo.save(event.getActor());
		repoRepo.save(event.getRepo());
		eventRepo.save(event);
	}

	@Override
	public List<Event> getAllEvents() {
		return eventRepo.findAll(new Sort(Sort.Direction.ASC, "id"));
	}
	
	@Override
	public List<Event> getEventsFromActor(Long actorId) {
		if(!actorRepo.exists(actorId)) {
			throw new NotFoundException();
		}
		
		return eventRepo.findAllByActorIdOrderByIdAsc(actorId);
	}
	
	@Override
	public void updateActorAvatar(Actor actor)  {
		Actor original = actorRepo.findOne(actor.getId());
		if(original == null) {
			throw new NotFoundException();
		}
		
		if(!original.getLogin().equals(actor.getLogin())) {
			throw new BadRequestException();
		}
		
		actorRepo.save(actor);
	}

	@Override
	public List<Actor> getAllActors() {
		return actorRepo.findAllByQtEvents();
	}

	@Override
	public List<Actor> getActorsByMaximumStreak() {
		List<Event> events = eventRepo.findAllByStreak();
		Map<Actor, Integer> maxStreakActor = getMaxStreakByActor(events);
		
		List<Actor> actors = Arrays.asList(maxStreakActor.keySet()
				.toArray(new Actor[maxStreakActor.keySet().size()]));
		
		return actors;
	}

	private Map<Actor, Integer> getMaxStreakByActor(List<Event> events) {
		Map<Actor, List<Event>> mapActorEvents = getEventsByActor(events);
		
		Map<Actor, Integer> actorMaxStreak = new LinkedHashMap<>();
		for(Entry<Actor, List<Event>> entry: mapActorEvents.entrySet()) {
			Actor key = entry.getKey();
			actorMaxStreak.put(key, 0);
			Integer actorCurrentStreak = 0;
			
			Event currentEvent;
			Event previousEvent = null;
			for(Event e: entry.getValue()) {
				currentEvent = e;
				
				if(isConsecutive(previousEvent, currentEvent)) {
					Integer streakActor = actorCurrentStreak;
					streakActor = streakActor + 1;
					actorCurrentStreak = streakActor;
				} else {
					if(actorCurrentStreak > actorMaxStreak.get(key)) {
						actorMaxStreak.put(key, actorCurrentStreak);
					}
					actorCurrentStreak = 1;
				}
				
				previousEvent = currentEvent;
			}
			if(actorCurrentStreak > actorMaxStreak.get(key)) {
				actorMaxStreak.put(key, actorCurrentStreak);
			}
		}
		return MapUtil.sortReverseByValue(actorMaxStreak);
	}

	private Map<Actor, List<Event>> getEventsByActor(List<Event> events) {
		Map<Actor, List<Event>> mapActorEvents = new LinkedHashMap<>();
		for(Event e: events) {
			List<Event> evs;
			if(mapActorEvents.containsKey(e.getActor())) {
				evs = mapActorEvents.get(e.getActor());
				evs.add(e);
			} else {
				evs = new LinkedList<>();
				evs.add(e);
				mapActorEvents.put(e.getActor(), evs);
			}
		}
		return mapActorEvents;
	}

	private boolean isConsecutive(Event previous, Event current) {
		if(previous == null) {
			return true;
		}
		
		return previous.getCreatedAt().toLocalDateTime().toLocalDate().minusDays(1)
				.equals(current.getCreatedAt().toLocalDateTime().toLocalDate());
	}
}
