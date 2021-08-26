package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import play.db.jpa.Model;

@Entity
public class Categoria extends Model{
	
	public String nome;
	
	@ManyToMany
	@JoinTable(name="categoria_curso")
	public List<Curso> cursos;

}
