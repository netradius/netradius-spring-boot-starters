package com.netradius.spring.tenancy.sample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ActorController {

  // Traditionally we do not allow repositories to be used directly in controllers, but to
  // keep this example simple, it was allowed.
  @Autowired
  private ActorRepository actorRepository;

  @RequestMapping(value = "/actors", method = RequestMethod.GET)
  public List<String> getActors(@RequestParam(required = false) String name) {
    List<String> list = new ArrayList<>();
    if (StringUtils.hasText(name)) {
      Actor actor = actorRepository.findByName(name);
      if (actor != null) {
        list.add(actor.getName());
      }
    } else {
      actorRepository.findAll(new Sort(Direction.ASC, "name"))
          .forEach(actor -> list.add(actor.getName()));
    }
    return list;
  }

  @RequestMapping(value = "/actors", method = RequestMethod.POST)
  public List<String> addActor(@RequestParam String name) {
    Actor actor = actorRepository.findByName(name);
    if (actor == null) {
      actor = new Actor().setName(name);
      actorRepository.save(actor);
    }
    return getActors(null);
  }

  @RequestMapping(value = "/actors", method = RequestMethod.DELETE)
  public void deleteActor(@RequestParam String name) {
    Actor actor = actorRepository.findByName(name);
    if (actor != null) {
      actorRepository.delete(actor);
    }
  }

}
