package cl.awakelab.empresaprevencionriesgos.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import cl.awakelab.empresaprevencionriesgos.model.beans.UsuarioDTO;
import cl.awakelab.empresaprevencionriesgos.model.services.UsuarioService;

@Controller
@RequestMapping("/listadoDeUsuarios")
public class ListadoDeUsuarios {

	/**
	 * Maneja las solicitudes que se le hacen a la raíz del sitio
	 * 
	 * @return un objeto {@link ModelAndView} con la respuesta al cliente
	 */
	@Autowired
	private UsuarioService usuarioService;

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView mostrarListadoUsuarios(HttpServletRequest request, HttpServletResponse response, Model model) {
		HttpSession session = request.getSession();
		UsuarioDTO usuario = (UsuarioDTO) session.getAttribute("usuario");
		String page = "";

		// solo administrativo tiene acceso a la página de listadoDeUsuarios.
		if (usuario != null && usuario.getPerfilId() == 1) {

			// Se crea una instancia de la clase UsuarioServicio(lógica de negocio).
			// UsuarioService usuarioService = new UsuarioService();

			// Se crea una lista de usuarios que serán seteados para mostrar en la página
			// web.
			List<UsuarioDTO> listaUsuarios = usuarioService.obtenerListaUsuarios();
			request.setAttribute("listaUsuarios", listaUsuarios);
			model.addAttribute("usuario", usuario);
			model.addAttribute("listaUsuarios", listaUsuarios);
			page = "views/administrativo/listadoDeUsuarios";

		} else {
			page = "views/login";
		}

		return new ModelAndView(page, "", "");
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView mostrarListadoUsuariosPost(HttpServletRequest request, HttpServletResponse response,
			Model model) {
		HttpSession session = request.getSession();
		UsuarioDTO usuario = (UsuarioDTO) session.getAttribute("usuario");
		String page = "";

		// solo administrativo tiene acceso a la página de listadoDeUsuarios.
		if (usuario != null && usuario.getPerfilId() == 1) {
			model.addAttribute("usuario", usuario);
			page = "views/administrativo/listadoDeUsuarios";

		} else {
			page = "views/login";
		}
		return new ModelAndView(page, "", "");
	}

}
