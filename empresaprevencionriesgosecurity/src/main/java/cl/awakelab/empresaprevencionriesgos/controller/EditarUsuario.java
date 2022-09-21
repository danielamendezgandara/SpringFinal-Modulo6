package cl.awakelab.empresaprevencionriesgos.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import cl.awakelab.empresaprevencionriesgos.model.beans.AdministrativoDTO;
import cl.awakelab.empresaprevencionriesgos.model.beans.ClienteDTO;
import cl.awakelab.empresaprevencionriesgos.model.beans.ProfesionalDTO;
import cl.awakelab.empresaprevencionriesgos.model.beans.UsuarioDTO;
import cl.awakelab.empresaprevencionriesgos.model.services.AdministrativoService;
import cl.awakelab.empresaprevencionriesgos.model.services.ClienteService;
import cl.awakelab.empresaprevencionriesgos.model.services.ProfesionalService;

@Controller
@RequestMapping("/editarUsuario")
public class EditarUsuario {

	/**
	 * Maneja las solicitudes que se le hacen a la raíz del sitio
	 * 
	 * @return un objeto {@link ModelAndView} con la respuesta al cliente
	 */
	@Autowired
	private AdministrativoService administrativoService;
	@Autowired
	private ClienteService clienteService;
	@Autowired
	private ProfesionalService profesionalService;

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView editarUsuarioGet(HttpServletRequest request, HttpServletResponse response, Model model) {
		HttpSession session = request.getSession();
		UsuarioDTO usuario = (UsuarioDTO) session.getAttribute("usuario");
		String page = "";

		// solo administrativo tiene acceso a la página..
		if (usuario != null && usuario.getPerfilId() == 1) {
			String rutEditar = request.getParameter("rut");
			String perfilIdEditar = request.getParameter("perfil_id");

			UsuarioDTO usuarioEditar = null;
			// administrativo
			if (perfilIdEditar.equals("1")) {
				// AdministrativoService administrativoService=new AdministrativoService();
				usuarioEditar = (AdministrativoDTO) administrativoService.obtenerAdministrativo(rutEditar);
			}

			// cliente
			if (perfilIdEditar.equals("2")) {
				// ClienteService clienteService = new ClienteService();
				usuarioEditar = (ClienteDTO) clienteService.obtenerCliente(rutEditar);
			}

			// profesional
			if (perfilIdEditar.equals("3")) {
				// ProfesionalService profesionalService= new ProfesionalService();
				usuarioEditar = (ProfesionalDTO) profesionalService.obtenerProfesional(rutEditar);
			}

			request.setAttribute("usuarioEditar", usuarioEditar);
			request.setAttribute("postResponse", false);// esto para saber que no va de post
			request.setAttribute("respuestaEdicion", false);
			model.addAttribute("usuarioEditar", usuarioEditar);
			model.addAttribute("usuario", usuario);
			model.addAttribute("postResponse", false);
			model.addAttribute("respuestaEdicion", false);
			page = "views/administrativo/editarUsuario";
		} else {
			page = "views/login";
		}
		return new ModelAndView(page, "", "");
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView editarUsuarioPost(HttpServletRequest request, HttpServletResponse response, Model model) {
		System.out.println("Editar Usuario");
		// hace nada dopost
		HttpSession session = request.getSession();
		UsuarioDTO usuario = (UsuarioDTO) session.getAttribute("usuario");
		String page = "";

		// solo administrativo tiene acceso a la página..
		if (usuario != null && usuario.getPerfilId() == 1) {

			boolean respuestaEdicion = false;

			String nick = request.getParameter("nick");
			String password1 = new BCryptPasswordEncoder().encode(request.getParameter("password1"));
			String rut = request.getParameter("rut");
			String nombres = request.getParameter("nombres");
			String apellidos = request.getParameter("apellidos");
			String fechaDeNacimiento = request.getParameter("birthday");
			String perfilId = request.getParameter("perfil_id");// va ser null pq no hay radio
			System.out.println("post editar");
			UsuarioDTO usuarioEditar = null;
			// administrativo
			if (perfilId.equals("1")) {
				String area = request.getParameter("area");
				String experienciaPrevia = request.getParameter("experiencia_previa");
				// AdministrativoService administrativoService=new AdministrativoService();
				String[] params = { nick, password1, rut, nombres, apellidos, fechaDeNacimiento, perfilId, area,
						experienciaPrevia };
				respuestaEdicion = administrativoService.editarAdministrativo(params);
				usuarioEditar = administrativoService.obtenerAdministrativo(rut);
			}

			// cliente
			if (perfilId.equals("2")) {
				String telefono = request.getParameter("telefono");
				String email = request.getParameter("email");
				String afpId = request.getParameter("afp_id");
				String sistemaSaludId = request.getParameter("sistema_salud_id");
				String direccion = request.getParameter("direccion");
				String comuna = request.getParameter("comuna");
				String organizacion = request.getParameter("organizacion");
				// ClienteService clienteService = new ClienteService();
				String[] params = { nick, password1, rut, nombres, apellidos, fechaDeNacimiento, perfilId, telefono,
						email, afpId, sistemaSaludId, direccion, comuna, organizacion };
				respuestaEdicion = clienteService.editarCliente(params);
				usuarioEditar = clienteService.obtenerCliente(rut);
			}

			// profesional
			if (perfilId.equals("3")) {
				String titulo = request.getParameter("titulo");
				String fechaDeIngreso = request.getParameter("fecha_de_ingreso");// revisar esto
				// ProfesionalService profesionalService= new ProfesionalService();
				String[] params = { nick, password1, rut, nombres, apellidos, fechaDeNacimiento, perfilId, titulo,
						fechaDeIngreso };
				respuestaEdicion = profesionalService.editarProfesional(params);
				usuarioEditar = profesionalService.obtenerProfesional(rut);
			}
			System.out.println(respuestaEdicion);
			request.setAttribute("postResponse", true);
			request.setAttribute("respuestaEdicion", respuestaEdicion);
			request.setAttribute("usuarioEditar", usuarioEditar);
			request.setAttribute("rut", rut);
			model.addAttribute("postResponse", true);
			model.addAttribute("respuestaEdicion", respuestaEdicion);
			model.addAttribute("usuarioEditar", usuarioEditar);
			model.addAttribute("usuario", usuario);
			model.addAttribute("rut", rut);

			page = "views/administrativo/editarUsuario";
		} else {
			page = "views/login";
		}
		return new ModelAndView(page, "", "");
	}

}
