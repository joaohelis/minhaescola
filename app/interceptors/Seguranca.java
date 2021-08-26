package interceptors;

import annotations.AdministradorAutenticado;
import annotations.AlunoAutenticado;
import controllers.Application;
import play.mvc.Before;
import play.mvc.Controller;

public class Seguranca extends Controller {

//	@Before(unless = { "Application.login", "Application.autenticar" })
//	public static void checarAutenticacao() {
//		if (session.get("emailUsuarioAutenticado") == null) {
//			Application.login();
//		}
//	}

	@Before
	static void checarAdministrador() {
		String tipoUsuario = session.get("tipoUsuario");
		AdministradorAutenticado adminAnnotation = getActionAnnotation(AdministradorAutenticado.class);
		if (adminAnnotation != null && !tipoUsuario.equals("ADMIN")) {
			flash.error("Acesso restrito aos administradores do sistema");
			Application.naoAutorizado();			
		}
	}
	
	@Before
	static void checarUsuarioAutenticado() {
		String tipoUsuario = session.get("tipoUsuario");
		AdministradorAutenticado adminAnnotation = getActionAnnotation(AdministradorAutenticado.class);
		AlunoAutenticado alunoAnnotation = getActionAnnotation(AlunoAutenticado.class);
		if (adminAnnotation != null || alunoAnnotation != null) {
			flash.error("O usuário deve estar logado para acessar o sistema.");
			Application.naoAutorizado();			
		}
	}

}
