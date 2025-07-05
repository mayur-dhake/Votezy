package in.scalive.votezy.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class VoteRequestDTO {
	@NotNull(message="VoterId is required")
	Long voterId;
	@NotNull(message="CandidateId is required")
	Long candidateId;
}
