package cl.awakelab.empresaprevencionriesgos.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import cl.awakelab.empresaprevencionriesgos.model.beans.ClienteDTO;
import cl.awakelab.empresaprevencionriesgos.model.beans.ContactoDTO;
import cl.awakelab.empresaprevencionriesgos.model.beans.UsuarioDTO;
import cl.awakelab.empresaprevencionriesgos.model.services.ClienteService;
import cl.awakelab.empresaprevencionriesgos.model.services.ContactoService;

@Controller
public class Contacto {

	/**
	 * Maneja las solicitudes que se le hacen a la raíz del sitio
	 *
	 * @return un objeto {@link ModelAndView} con la respuesta al cliente
	 */
	@Autowired
	private ClienteService clienteService;
	@Autowired
	private ContactoService contactoService;

	@RequestMapping(path = "/contacto", method = RequestMethod.GET)
	public ModelAndView mostrarContactoGet(HttpServletRequest request, HttpServletResponse response, Model model) {
		request.setAttribute("postResponse", false);
		HttpSession session = request.getSession();
		UsuarioDTO usuario = (UsuarioDTO) session.getAttribute("usuario");
		String page = "";

		// solo cliente tiene acceso a la página de contacto.
		if (usuario != null && usuario.getPerfilId() == 2) {
			// ClienteService clienteService = new ClienteService();
			ClienteDTO cliente = clienteService.obtenerCliente(usuario.getRut());
			request.setAttribute("cliente", cliente);
			model.addAttribute("usuario", usuario);
			model.addAttribute("cliente", cliente);
			page = "views/cliente/contacto";
		} else {
			page = "views/login";
		}
		return new ModelAndView(page, "", "");
	}

	@RequestMapping(path = "/contacto", method = RequestMethod.POST)
	public ModelAndView crearContacto(HttpServletRequest request, HttpServletResponse response, Model model) {
		request.setAttribute("postResponse", false);
		HttpSession session = request.getSession();
		UsuarioDTO usuario = (UsuarioDTO) session.getAttribute("usuario");
		String page = "";
		// solo cliente tiene acceso a la página de contacto.
		if (usuario != null && usuario.getPerfilId() == 2) {
			// ContactoService contactoService = new ContactoService();
			String mensaje = request.getParameter("mensaje");
			ContactoDTO contactoDTO = new ContactoDTO(mensaje, usuario.getRut());
			ClienteDTO cliente = clienteService.obtenerCliente(usuario.getRut());
			request.setAttribute("cliente", cliente);
			model.addAttribute("usuario", usuario);
			model.addAttribute("cliente", cliente);
			if (contactoService.addContacto(contactoDTO)) {
				request.setAttribute("isOk", true);
				model.addAttribute("isOk", true);
				contactoService.showDataContacto(contactoDTO);
			} else {
				request.setAttribute("isOk", false);
				model.addAttribute("isOk", false);
			}
			request.setAttribute("postResponse", true);
			model.addAttribute("postResponse", true);
			page = "views/cliente/contacto";
		} else {
			page = "views/login";
		}
		return new ModelAndView(page, "", "");
	}

}
