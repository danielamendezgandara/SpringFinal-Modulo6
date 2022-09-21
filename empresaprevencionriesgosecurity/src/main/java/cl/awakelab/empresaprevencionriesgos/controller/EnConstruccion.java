package cl.awakelab.empresaprevencionriesgos.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import cl.awakelab.empresaprevencionriesgos.model.beans.UsuarioDTO;

@Controller
@RequestMapping("/enConstruccion")
public class EnConstruccion {
	/**
	 * Maneja las solicitudes que se le hacen a la raíz del sitio
	 * 
	 * @return un objeto {@link ModelAndView} con la respuesta al cliente
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView enConstruccionGet(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			Model model) {
		UsuarioDTO usuario = (UsuarioDTO) session.getAttribute("usuario");
		model.addAttribute("usuario", usuario);
		return new ModelAndView("views/enConstruccion", "", "");
	}

	@RequestMapping(method = RequestMethod.POST)
	public String enConstruccionPost(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			Model model) {
		UsuarioDTO usuario = (UsuarioDTO) session.getAttribute("usuario");
		model.addAttribute("usuario", usuario);
		return "views/enConstruccion";
	}

}
