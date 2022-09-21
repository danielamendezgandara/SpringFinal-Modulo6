package cl.awakelab.empresaprevencionriesgos.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
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
@RequestMapping("/eliminarUsuario")
public class EliminarUsuario {

	@Autowired
	private UsuarioService usuarioService;

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView eliminarUsuarioGet(HttpServletRequest request, HttpServletResponse response, Model model) {
		HttpSession session = request.getSession();
		UsuarioDTO usuario = (UsuarioDTO) session.getAttribute("usuario");
		String page = "";
		System.out.println("Entrando a eliminar usuario");
		// solo administrativo tiene acceso a la página..
		if (usuario != null && usuario.getPerfilId() == 1) {

			String rutUsuarioEliminar = request.getParameter("rut");
			// UsuarioService usuarioService = new UsuarioService();

			if (rutUsuarioEliminar != null) {
				boolean eliminacionUsuarioOk = usuarioService.eliminarUsuario(rutUsuarioEliminar);
				request.setAttribute("postResponse", true);
				request.setAttribute("respuestaEliminacion", eliminacionUsuarioOk);
				request.setAttribute("rut", rutUsuarioEliminar);
				model.addAttribute("usuario", usuario);
				model.addAttribute("postResponse", true);
				model.addAttribute("respuestaEliminacion", eliminacionUsuarioOk);
				model.addAttribute("rut", rutUsuarioEliminar);
			}
			// redirigo
			page = "views/administrativo/listadoDeUsuarios";
		} else {
			page = "views/login";
		}
		List<UsuarioDTO> listaUsuarios = usuarioService.obtenerListaUsuarios();
		return new ModelAndView(page, "listaUsuarios", listaUsuarios);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView eliminarUsuarioPost(HttpServletRequest request, HttpServletResponse response, Model model)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		UsuarioDTO usuario = (UsuarioDTO) session.getAttribute("usuario");
		String page = "";
		System.out.println("Entrando a eliminar usuario 2");
		// solo administrativo tiene acceso a la página..
		if (usuario != null && usuario.getPerfilId() == 1) {
			// redirigo
			model.addAttribute("usuario", usuario);
			page = "views/administrativo/listadoDeUsuarios";

		} else {
			page = "views/login";
		}
		List<UsuarioDTO> listaUsuarios = usuarioService.obtenerListaUsuarios();
		return new ModelAndView(page, "listaUsuarios", listaUsuarios);
	}
}
