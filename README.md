

  Thymeleaf interface exemple for a student list: aluno(code+name)


  access by http://localhost:8088 in your browser

  command line access through curl. Don't forget to quote ? * &
  	Add member with:
  		curl -X POST http://localhost:8088/alunos?name=x&code=12345
  	Remove member with:
  		curl -X POST http://localhost:8088/alunos/remove?code=12345
  	Find members by regular expression with:
  		curl -X POST http://localhost:8088/alunos/find?name=.*Santos.*
  	Delete all (!) with:
  		curl http://localhost:8088/alunos/delete
  	Save to file with:
  		curl http://localhost:8088/alunos/quit
  	Discard changes with:
  		curl http://localhost:8088/alunos/abandon

(c)reis.santos@tecnico.ulisboa.pt, 2017
