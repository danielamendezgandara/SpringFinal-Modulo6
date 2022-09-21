package cl.awakelab.empresaprevencionriesgos.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/login")
public class Login {

	@GetMapping("/login")
	@RequestMapping(method = RequestMethod.GET)
	public String fomrularioLogin(Model model, @RequestParam(required = false) String error) {
		if (error != null) {
			model.addAttribute("postResponse", true);
			log.error("Usuario no registrado en la base de datos");
		}

		return "views/login";
	}

}
