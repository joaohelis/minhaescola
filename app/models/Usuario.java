package models;

import javax.persistence.Entity;

import play.data.validation.MinSize;
import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
public class Usuario extends Model{
	
	@Required
	@MinSize(value=3, message="O nome deve ter no mínimo 3 caracteres.")
	public String nome;

	public String email;
	public String senha;
	
}
