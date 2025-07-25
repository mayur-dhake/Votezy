package in.scalive.votezy.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor 
public class ErrorResponse {

	private int statusCode;
	private String messageString;
}
