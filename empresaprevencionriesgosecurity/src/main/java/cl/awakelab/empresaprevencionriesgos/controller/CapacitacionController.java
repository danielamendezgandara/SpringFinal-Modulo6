package cl.awakelab.empresaprevencionriesgos.controller;

import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cl.awakelab.empresaprevencionriesgos.model.beans.CapacitacionDTO;
import cl.awakelab.empresaprevencionriesgos.model.beans.ClienteDTO;
import cl.awakelab.empresaprevencionriesgos.model.beans.UsuarioDTO;
import cl.awakelab.empresaprevencionriesgos.model.services.CapacitacionService;
import cl.awakelab.empresaprevencionriesgos.model.services.ClienteService;

@Controller
@RequestMapping("/capacitacion")
public class CapacitacionController {
	@Autowired
	private CapacitacionService capacitacionService;
	@Autowired
	private ClienteService clienteService;

	@GetMapping("/crear")
	public String mostrarFormularioCrearCapacitacion(HttpServletRequest request, Model model) {
		HttpSession session = request.getSession();
		model.addAttribute("postResponse", false);
		UsuarioDTO usuario = (UsuarioDTO) session.getAttribute("usuario");
		// solo cliente (2) tiene acceso a la página de crearCapacitacion.
		if (usuario != null && usuario.getPerfilId() == 2) {
			ClienteDTO cliente = clienteService.obtenerCliente(usuario.getRut());
			model.addAttribute("usuario", usuario);
			model.addAttribute("cliente", cliente);
			model.addAttribute("isOk", false);
			return "views/cliente/crearCapacitacion";
		} else {
			return "views/login";
		}
	}

	@PostMapping("/crear") // @RequestBody String body
	public String procesarFormularioCrearCapacitacion(HttpServletRequest request,
			@RequestParam Map<String, String> allParams, Model model) {
		HttpSession session = request.getSession();
		UsuarioDTO usuario = (UsuarioDTO) session.getAttribute("usuario");
		model.addAttribute("postResponse", false);
		if (usuario != null && usuario.getPerfilId() == 2) {
			// a nivel de modelo el usuario y cliente deberia ya estar creado, pero lo vamos
			// a obviar y persistir solo la capacitacion
			// tambien vamos a obviar las validaciones que deberia hacer el backend,
			// asumimos que se envia el formato requerid
			// CapacitacionService capacitacionService = new CapacitacionService();
			// por el momento tomaremos la hora
			ClienteDTO cliente = clienteService.obtenerCliente(usuario.getRut());
			model.addAttribute("usuario", usuario);
			model.addAttribute("cliente", cliente);
			String[] params = { null, allParams.get("dia_id"), allParams.get("hora"), allParams.get("lugar"),
					allParams.get("duracion"), allParams.get("cantidadDeAsistentes"), usuario.getRut() };
			// retorna true si se creo la capacitacion y false en caso contrario
			if (capacitacionService.crearCapacitacion(params)) {
				model.addAttribute("isOk", true);
			} else {
				model.addAttribute("isOk", false);
			}
			// redirigimos a la pagina que genero la solicitud
			model.addAttribute("postResponse", true);
			return "views/cliente/crearCapacitacion";
		} else {
			return "views/login";
		}
	}

	@GetMapping("/listartodas")
	public String listarCapacitaciones(HttpServletRequest request, Model model) {
		HttpSession session = request.getSession();
		UsuarioDTO usuario = (UsuarioDTO) session.getAttribute("usuario");
		if (usuario != null && usuario.getPerfilId() == 2) {
			// CapacitacionService capacitacionService = new CapacitacionService();
			ArrayList<CapacitacionDTO> listaCapacitaciones = (ArrayList<CapacitacionDTO>) capacitacionService
					.obtenerListaCapacitacionesCliente(usuario.getRut());
			model.addAttribute("listaCapacitaciones", listaCapacitaciones);
			model.addAttribute("usuario", usuario);
			return "views/cliente/listarCapacitaciones";
		} else {
			return "views/login";
		}
	}

}
