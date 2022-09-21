package cl.awakelab.empresaprevencionriesgos.controller;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import cl.awakelab.empresaprevencionriesgos.model.beans.UsuarioDTO;
import cl.awakelab.empresaprevencionriesgos.model.services.UsuarioService;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class Inicio {

	@Autowired
	private UsuarioService usuarioService;

	@GetMapping("/inicio")
	public String mostrarInicio(HttpSession session, Model model, HttpServletRequest request,
			HttpServletResponse response) {

		Principal principal = request.getUserPrincipal();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		String nick = principal.getName();
		String credential = (String) auth.getCredentials();
		UsuarioDTO usuario = usuarioService.get(nick, credential);

		if (usuario != null) {
			model.addAttribute("usuario", usuario);
			session.setAttribute("usuario", usuario);
			log.info("Ingreso exitoso");
		}

		return "views/inicio";
	}

	@GetMapping("/")
	public String mostrarInicioPost(HttpSession session, Model model) {
		log.info("Bienvenidos/as a Empresa de Prevención de Riesgos con Spring boot");
		return "views/inicio";
	}

	@GetMapping("/403")
	public String accessDenied() {
		return "views/403";
	}
}
