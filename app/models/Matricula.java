package models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
public class Matricula extends Model {
	
	@Required
	public Date dataMatricula;
	
	@ManyToOne
	@JoinColumn(name="aluno_id")
	public Aluno aluno;
	
	@ManyToOne
	@JoinColumn(name="curso_id")
	public Curso curso;
	
	public Matricula() {
		this.dataMatricula = new Date();
	}

	@Override
	public String toString() {
		return "Matricula [dataMatricula=" + dataMatricula + ", aluno=" + aluno + ", curso=" + curso + "]";
	}	
}
