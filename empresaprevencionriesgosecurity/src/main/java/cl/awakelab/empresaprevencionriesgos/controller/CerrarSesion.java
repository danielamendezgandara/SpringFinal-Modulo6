package cl.awakelab.empresaprevencionriesgos.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class CerrarSesion {

	@GetMapping("/cerrarsesion")
	public String cerrarSesionGet(HttpSession session, Model model) {
		model.addAttribute("usuario", null);
		session.invalidate();
		log.info("Cerrando sesión");
		return "views/inicio";
	}

	@PostMapping("/cerrarsesion")
	public String cerrarSesionPost(HttpSession session, Model model) {
		model.addAttribute("usuario", null);
		session.invalidate();
		return "views/inicio";
	}
}
