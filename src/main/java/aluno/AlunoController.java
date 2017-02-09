package aluno;

import java.util.*;
import java.io.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/alunos")
public class AlunoController {
	private static Logger logger = LoggerFactory.getLogger(AlunoController.class);
	private static final String FILENAME = "alunos.txt";
	private static boolean loaded = false;
	private static List<Aluno> alunos = new ArrayList<>();
	private List<Aluno> find = new ArrayList<>();

	@GetMapping()
	public String alunoForm(Model model) {
		logger.info("alunoForm");
		if (!loaded) load();
	 	model.addAttribute("aluno", new Aluno());
		// model.addAttribute("alunos", alunos);
		model.addAttribute("size", alunos.size());
		model.addAttribute("length", find.size());
		model.addAttribute("regex", find);
		return "aluno";
	}

	@PostMapping()
	public String alunoSubmit(@ModelAttribute Aluno aluno) {
		logger.info("alunoSubmit name:{}, code:{}", aluno.getName(), aluno.getCode());
		// alunos.add(aluno); // does nor ensure restrictions
		alunos.add(new Aluno(aluno.getName(), aluno.getCode())); // ensure restrictions
		return "redirect:/alunos";
	}

	@PostMapping("/remove")
	public String remove(@ModelAttribute Aluno aluno) {
		logger.info("remove code:{}", aluno.getCode());
		for (Aluno a: alunos)
			if (a.getCode().equals(aluno.getCode())) {
				alunos.remove(a);
				return "redirect:/alunos";
			}
		return "dup";
	}

	@PostMapping("/find")
	public String find(@ModelAttribute Aluno aluno) {
		logger.info("regex name:{}", aluno.getName());
		find.clear();
		for (Aluno a: alunos)
			if (a.getName().matches(aluno.getName()))
				find.add(a);
		return "redirect:/alunos";
	}

	@RequestMapping("/delete")
	public String delete() {
		logger.info("delete");
		alunos.clear();
		return "redirect:/alunos";
	}

	@RequestMapping("/quit")
	public String save() {
		logger.info("save");
		try {
			PrintWriter out = new PrintWriter(FILENAME, "UTF-8");
			for (Aluno aluno: alunos) {
				out.println(aluno.getName());
				out.println(aluno.getCode());
			}
			out.close();
			loaded = false;
			alunos.clear();
		} catch (IOException e) {
			throw new AlunoException("Output file: "+e);
		}
		return "redirect:/";
	}

	@RequestMapping("/abandon")
	public String abandon() {
		logger.info("abandon");
		loaded = false;
		alunos.clear();
		return "redirect:/";
	}

	public static Collection<Aluno> alunos() {
		return Collections.unmodifiableCollection(alunos);
	}

	public void load() {
		try {
			loaded = true;
			BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(FILENAME), "UTF-8"));
			String name, code;
			while ((name = in.readLine()) != null &&
				(code = in.readLine()) != null)
				alunos.add(new Aluno(name, code));
			in.close();
		} catch (FileNotFoundException e) {
			return; // ignore inexisting input file
		} catch (IOException e) {
			throw new AlunoException("Input file: "+e);
		}
	}

}
