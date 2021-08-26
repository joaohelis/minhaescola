package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
public class Curso extends Model{
	
	@Required
	public String nome;
	
	@ManyToMany(mappedBy="cursos")
	public List<Categoria> categorias;	
	
	@OneToMany(mappedBy="curso")
	public List<Matricula> matriculas = new ArrayList<>();
	
	public Curso(String nome) {
		this.nome = nome;
	}		
	
	public Curso() {		
		this.categorias = new ArrayList<>();		
	}
	
	public boolean verificarAlunoMatriculado(String idAluno) {		
		Long idAlunoLong = Long.parseLong(idAluno);
		Aluno aluno = Aluno.findById(idAlunoLong);
		for(Matricula matricula: matriculas) {
			if(matricula.aluno.equals(aluno))
				return true;				
		}
		return false;		
	}

	@Override
	public String toString() {
		return "Curso [nome=" + nome + "]";
	}	
}