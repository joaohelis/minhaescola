package controllers;

import play.*;
import play.mvc.*;

import java.util.*;

import annotations.AdministradorAutenticado;
import interceptors.Seguranca;
import models.*;

@With(Seguranca.class)
public class Application extends Controller {
	
	public static void naoAutorizado() {
		renderTemplate("errors/401.html");
	}
		
	// página inicial do sistema	
    public static void index() {
    	if(session.isEmpty()) {
    		Application.login();
    	}
    	long totalAlunos = Aluno.count();
    	long totalCursos = Curso.count();
    	String nomeDoTemplate = null;
    	if(session.get("tipoUsuario").equals("ALUNO"))
    		nomeDoTemplate = "Alunos/index.html";
    	else
    		nomeDoTemplate = "Application/index.html";
        renderTemplate(nomeDoTemplate, totalAlunos, totalCursos);
    }
    
    public static void login() {
    	render();
    }
    
    public static void autenticar(String email, String senha) {
    	
    	Usuario usuario = Usuario.find("email=? and senha=?", email, senha).first();
    	    	
    	if(usuario != null && usuario instanceof Aluno) {
    		
    		session.put("usuarioID", usuario.id);
    		session.put("emailUsuarioAutenticado", usuario.email);
    		session.put("tipoUsuario", "ALUNO");  
    		session.put("nomeArquivoMain", "main-aluno.html");
    		
    		Aluno aluno = (Aluno) usuario;    		    		
    		
    		if(aluno.foto.exists()) {
    			session.put("alunoTemFoto", true);
    		}    		
    		
    		Alunos.paginaInicial();
    	}else if (usuario != null && usuario instanceof Admin) {
    		session.put("emailUsuarioAutenticado", usuario.email);
    		session.put("tipoUsuario", "ADMIN");  
    		session.put("nomeArquivoMain", "main-admin.html");
    		index();    	
    	}else {
    		flash.error("Usuário ou senha inválidos!");
    		login();
    	}    	
    }
    
    public static void logout() {
    	session.clear();
    	login();
    }
    
}