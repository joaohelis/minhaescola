package jobs;

import models.Admin;
import models.Aluno;
import models.Curso;
import models.Usuario;
import play.jobs.Job;
import play.jobs.OnApplicationStart;

@OnApplicationStart
public class Inicializador extends Job {
	
	@Override
	public void doJob() throws Exception {
		if(Usuario.count() == 0) {
			
			Admin admin = new Admin();
			admin.nome = "Admin";
			admin.email = "admin@ifrn.edu.br";
			admin.senha = "123";
			admin.save();							
		}
		
		Curso curso = null;
		Curso curso2 = null;

		if(Curso.count() == 0) {
			curso = new Curso("Informática");
			curso.save();
			
			curso2 = new Curso("Química");
			curso2.save();
		}
		
		if(Aluno.count() == 0) {
			Aluno aluno = new Aluno();
			aluno.nome = "Fulano";	
			aluno.email = "fulano@ifrn.edu.br";
			aluno.senha = "123";
			aluno.curso = curso;
			aluno.save();
			
			Aluno aluno2 = new Aluno();
			aluno2.nome = "Sicrano";
			aluno2.email = "sicrano@ifrn.edu.br";
			aluno2.senha = "123";
			aluno2.curso = curso2;
			aluno2.save();
		}
	}
}
