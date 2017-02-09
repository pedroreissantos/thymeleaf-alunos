package aluno;

import aluno.AlunoException;

public class Aluno {
	public static final int CODE_SIZE = 5;
	private String name;
	private String code;

	public Aluno() { name = ""; code = ""; }

	public Aluno(String name, String code) {
		name(name);
		code(code);
	}

	public String getName() {
		return name;
	}

	public String getCode() {
		return code;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setCode(String code) {
		this.code = code;
	}

	private void name(String name) {
		if (name.length() == 0)
			throw new AlunoException("No name given");
		for (Aluno a: AlunoController.alunos())
			if (a.getName().equals(name))
				throw new AlunoException("Duplicate name: "+name);
		this.name = name;
	}

	private void code(String code) {
		if (code.length() != Aluno.CODE_SIZE)
			throw new AlunoException("Code size error: "+code.length());
		for (Aluno a: AlunoController.alunos())
			if (a.getCode().equals(code))
				throw new AlunoException("Duplicate code: "+code);
		this.code = code;
	}
}
