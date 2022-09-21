package cl.awakelab.empresaprevencionriesgos.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import cl.awakelab.empresaprevencionriesgos.model.beans.UsuarioDTO;
import cl.awakelab.empresaprevencionriesgos.model.services.AdministrativoService;
import cl.awakelab.empresaprevencionriesgos.model.services.ClienteService;
import cl.awakelab.empresaprevencionriesgos.model.services.ProfesionalService;

@RestController
@RequestMapping("/crearUsuario")
public class CrearUsuario {

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
	public ModelAndView mostrarCrearUsuario(HttpSession session,Model model) {
		//HttpSession session = request.getSession(false);
		System.out.println(session.getAttribute("usuario"));
		UsuarioDTO usuario = (UsuarioDTO) session.getAttribute("usuario");	
		//session = request.getSession();
		//UsuarioDTO usuario = (UsuarioDTO) session.getAttribute("usuario");
		String page = "";
		if (usuario != null && usuario.getPerfilId() == 1) {
			System.out.println("existo");
			model.addAttribute("usuario", usuario);
			model.addAttribute("postResponse", false);
			model.addAttribute("respuestaCreacion", false);
			session.setAttribute("usuario", usuario);
			session.setAttribute("postResponse", false);
			session.setAttribute("respuestaCreacion", false);
			page = "views/administrativo/crearUsuario";
		} else {
			System.out.println("estoy vacío");
			page = "views/login";
		}
		return new ModelAndView(page, "", "");
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView agregarUsuario(HttpServletRequest request, HttpServletResponse response,HttpSession session,Model model) {
		UsuarioDTO usuario = (UsuarioDTO) session.getAttribute("usuario");
		String page = "";
		// solo administrativo tiene acceso a la página de crearUsuario.
		if (usuario != null && usuario.getPerfilId() == 1) {

			boolean respuestaCreacion = false;

			String nick = request.getParameter("nick");
			String password1 = new BCryptPasswordEncoder().encode(request.getParameter("password1"));
			String rut = request.getParameter("rut");
			String nombres = request.getParameter("nombres");
			String apellidos = request.getParameter("apellidos");
			String fechaDeNacimiento = request.getParameter("birthday");
			String perfilId = request.getParameter("perfil_id");

			// administrativo
			if (perfilId.equals("1")) {
				String area = request.getParameter("area");
				String experienciaPrevia = request.getParameter("experiencia_previa");
				// AdministrativoService administrativoService=new AdministrativoService();
				String[] params = { nick, password1, rut, nombres, apellidos, fechaDeNacimiento, perfilId, area,
						experienciaPrevia };
				respuestaCreacion = administrativoService.crearAdministrativo(params);
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
				respuestaCreacion = clienteService.crearCliente(params);
			}

			// profesional
			if (perfilId.equals("3")) {
				String titulo = request.getParameter("titulo");
				String fechaDeIngreso = request.getParameter("fecha_de_ingreso");// revisar esto
				// ProfesionalService profesionalService= new ProfesionalService();
				String[] params = { nick, password1, rut, nombres, apellidos, fechaDeNacimiento, perfilId, titulo,
						fechaDeIngreso };
				respuestaCreacion = profesionalService.crearProfesional(params);
			}

			request.setAttribute("postResponse", true);
			request.setAttribute("respuestaCreacion", respuestaCreacion);
			request.setAttribute("rut", rut);
			model.addAttribute("usuario", usuario);
			model.addAttribute("postResponse", true);
			model.addAttribute("respuestaCreacion", respuestaCreacion);
			model.addAttribute("rut", rut);
			page = "views/administrativo/crearUsuario";
		} else {
			request.setAttribute("postResponse", true);
			page = "views/login";
		}
		return new ModelAndView(page, "", "");
	}

}

