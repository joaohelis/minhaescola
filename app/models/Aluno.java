package models;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import play.data.validation.InPast;
import play.data.validation.Required;
import play.db.jpa.Blob;

@Entity
public class Aluno extends Usuario {	
	
	@Required
	public String cpf;		
	
	@InPast(message="A data deve estar no passado.")
	@Required
	public Date dataNascimento;
	
	public boolean ativo;
	
	public Blob foto;	
	
	@ManyToOne
	@JoinColumn(name="curso_id")
	public Curso curso; 
	
	@OneToMany(mappedBy="aluno")
	public List<Matricula> matriculas;
	
	public Aluno() {
		this.ativo = true;
	}		
	
	public int calcularIdade() {				
		LocalDate agora = LocalDate.now();		
		LocalDate dataNascimento = this.dataNascimento
									.toInstant()
									.atZone(ZoneId.systemDefault())
									.toLocalDate();
		long diff = ChronoUnit.YEARS.between(dataNascimento, agora);
		return (int) diff;
	}

	@Override
	public String toString() {
		return "Aluno [nome=" + nome + "]";
	}
}
