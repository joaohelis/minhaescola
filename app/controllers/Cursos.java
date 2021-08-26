package controllers;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import annotations.AdministradorAutenticado;
import interceptors.Seguranca;
import models.Aluno;
import models.Curso;
import models.Matricula;
import play.cache.Cache;
import play.data.validation.Valid;
import play.mvc.Controller;
import play.mvc.With;

@With(Seguranca.class)
public class Cursos extends Controller {

	@AdministradorAutenticado
	public static void form() {
		Curso curso = (Curso) Cache.get("curso");
		Cache.clear();
		List<Aluno> alunos = Aluno.findAll();
		render(curso, alunos);
	}

	@AdministradorAutenticado
	public static void editar(Long id) {
		Curso curso = Curso.findById(id);
		List<Aluno> alunosNaoMatriculados = Aluno.find("curso = null").fetch();
		renderTemplate("Cursos/form.html", curso, alunosNaoMatriculados);
	}

	@AdministradorAutenticado
	public static void remover(Long id) {
		Curso curso = Curso.findById(id);
		curso.delete();
		listar();
	}

	public static void detalhes(Long id) {
		Curso curso = Curso.findById(id);
		render(curso);
	}

	public static void listar() {
		List<Curso> cursos = Curso.findAll();
		render(cursos);
	}

	@AdministradorAutenticado
	public static void salvar(@Valid Curso curso) {

		if (validation.hasErrors()) {
			Cache.add("curso", curso);
			validation.keep();
			form();
		}
		flash.success("Curso salvo com sucesso.");
		curso.save();
		listar();
	}

	@AdministradorAutenticado
	public static void adicionarAluno(Long idCurso, Long idAluno) {
		Curso curso = Curso.findById(idCurso);
		Aluno aluno = Aluno.findById(idAluno);
		aluno.curso = curso;
		aluno.save();
		editar(idCurso);
	}

	@AdministradorAutenticado
	public static void desvincularAluno(Long idCurso, Long idAluno) {
		Aluno aluno = Aluno.findById(idAluno);
		aluno.curso = null;
		aluno.save();
		editar(idCurso);
	}

	public static void matricularAluno(Long idCurso) {

		Long idAluno = Long.parseLong(session.get("usuarioID"));

		Aluno aluno = Aluno.findById(idAluno);
		Curso curso = Curso.findById(idCurso);
		
		System.out.println("matricular aluno: " + aluno);

		Matricula novaMatricula = new Matricula();
		novaMatricula.aluno = aluno;
		novaMatricula.curso = curso;
		novaMatricula.save();

		listar();
	}

	public static void desmatricularAluno(Long idCurso) {

		Long idAluno = Long.parseLong(session.get("usuarioID"));
		Aluno aluno = Aluno.findById(idAluno);
		Curso curso = Curso.findById(idCurso);
		
		List<Matricula> matriculas = Matricula.find("curso = ? and aluno = ?", curso, aluno).fetch();

		for(Matricula matricula: matriculas) {
			matricula.delete();
		}
		
		listar();
	}
}
