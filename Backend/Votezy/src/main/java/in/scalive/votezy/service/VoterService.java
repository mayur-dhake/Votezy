package in.scalive.votezy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.scalive.votezy.entity.Candidate;
import in.scalive.votezy.entity.Vote;
import in.scalive.votezy.entity.Voter;
import in.scalive.votezy.exception.DuplicateResourceException;
import in.scalive.votezy.exception.ResourceNotFoundException;
import in.scalive.votezy.repository.CandidateRepository;
import in.scalive.votezy.repository.VoterRepository;
import jakarta.transaction.Transactional;

@Service
public class VoterService {

	private VoterRepository voterRepository;
	private CandidateRepository candidateRepository;
	
	@Autowired
	public VoterService(VoterRepository voterRepository, CandidateRepository candidateRepository) {
		this.voterRepository = voterRepository;
		this.candidateRepository = candidateRepository;
	}
	
	public Voter registerVoter(Voter voter) {		
		if(voterRepository.existsByEmail(voter.getEmail())) {
			throw new DuplicateResourceException("Voter with email id"+voter.getEmail()+"already exists");
		}
		
		return voterRepository.save(voter);
	}
	
	public List<Voter> getAllVoters(){		
		return voterRepository.findAll();
	}
	
	public Voter getVoterById(Long id){	
		Voter voter=voterRepository.findById(id).orElse(null);
		if(voter==null) {
			throw new ResourceNotFoundException("Voter with id:"+id+" not found");
		}
		
		return voter;
	}
	
	public Voter updateVoter(Long id, Voter updatedVoter) {
		Voter voter=voterRepository.findById(id).orElse(null);
		if(voter==null) {
			throw new ResourceNotFoundException("Voter with id"+voter.getId()+" not found");
		}
		
//		if(updatedVoter.getName()!=null) {
			voter.setName(updatedVoter.getName());
//		}
		
//		if(updatedVoter.getEmail()!=null) {
			voter.setEmail(updatedVoter.getEmail());
//		}
	
		return voterRepository.save(voter);
	}

	@Transactional
	public void deleteVoter(Long id) {
		Voter voter=voterRepository.findById(id).orElse(null);
		if(voter==null) {
			throw new ResourceNotFoundException("Can not delete voter with id :"+id+"as voter does not exits");
		}
		
		Vote vote=voter.getVote();
		if(vote!=null) {
			Candidate candidate=vote.getCandidate();
			candidate.setVoteCount(candidate.getVoteCount()-1);
			candidateRepository.save(candidate);
		}
		
		voterRepository.delete(voter);
		
	}
	
	
}
