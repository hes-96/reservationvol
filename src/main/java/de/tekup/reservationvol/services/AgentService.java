package de.tekup.reservationvol.services;

import de.tekup.reservationvol.entities.Agent;
import de.tekup.reservationvol.repositories.AgentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AgentService {
    @Autowired
    AgentRepository agentRepository;

    //get all Agents
    public List<Agent> getAgents() {
        return agentRepository.findAll();
    }

    //get employee by id
    public ResponseEntity<Agent> getAgent(long id) {

        Optional<Agent> optionalAgent = agentRepository.findById(id);
        if (optionalAgent.isPresent()) {
            return ResponseEntity.ok(optionalAgent.get());
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //update employee profile
    public ResponseEntity<Agent> editProfile(long id, Agent agent) {
        if (agent == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Optional<Agent> optionalAgent = agentRepository.findById(id);
        if (optionalAgent.isPresent()) {
            agent.setId(id);
            agent.setPassword(optionalAgent.get().getPassword());
            agentRepository.save(agent);
            return ResponseEntity.ok(optionalAgent.get());
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //delete agent
    public void deleteAgent(long id) {
        agentRepository.deleteById(id);
    }
}

