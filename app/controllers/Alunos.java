package controllers;

import java.util.List;

import annotations.AdministradorAutenticado;
import interceptors.Seguranca;
import models.Aluno;
import models.Curso;
import play.cache.Cache;
import play.data.validation.Valid;
import play.mvc.Controller;
import play.mvc.With;

@With(Seguranca.class)
public class Alunos extends Controller{
	
	public static void paginaInicial() {
		renderTemplate("Alunos/index.html");
	}
		
	@AdministradorAutenticado
	public static void form() {
		
		Aluno aluno = (Aluno) Cache.get("alu");
		Cache.clear();
		
		List<Curso> cursos = Curso.findAll();			
		render(cursos, aluno);
	}
	
	public static void listar() {
		List<Aluno> alunos = Aluno.findAll();
		render(alunos);
	}
	
	public static void editar(Long id) {
		Aluno aluno = Aluno.findById(id);
		List<Curso> cursos = Curso.findAll();
		renderTemplate("Alunos/form.html", aluno, cursos);
	}
	
	public static void remover(Long id) {
		Aluno aluno = Aluno.findById(id);
		aluno.delete();
		listar();
	}
	
	public static void salvar(@Valid Aluno aluno) {
		
		if(validation.hasErrors()) {
			Cache.add("alu", aluno);
			validation.keep();
			form();
		}		
		aluno.save();
		listar();
	}
	
	public static void renderFotoAluno(Long idAluno) {
		Aluno aluno = Aluno.findById(idAluno);
		response.setContentTypeIfNotSet(aluno.foto.type());
		renderBinary(aluno.foto.get());		
	}
}