package aluno;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class AlunoException extends RuntimeException {
	public AlunoException(String msg) { super(msg); }
}
