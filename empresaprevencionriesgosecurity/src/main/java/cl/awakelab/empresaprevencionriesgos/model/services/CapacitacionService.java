package cl.awakelab.empresaprevencionriesgos.model.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import cl.awakelab.empresaprevencionriesgos.model.beans.CapacitacionDTO;
import cl.awakelab.empresaprevencionriesgos.model.dao.ICapacitacionDAO;
import lombok.extern.slf4j.Slf4j;



@Service("CapacitacionService")
@Slf4j
public class CapacitacionService {

	@Autowired
	@Qualifier("MySQLCapacitacionJDBC")
	private ICapacitacionDAO capacitacionDao; // = new MySQLCapacitacionDAO();

	// identificador,diaId,hora,lugar,duracion,cantidadDeAsistentes,rutCliente
	public boolean crearCapacitacion(String[] params) {
		// identificador lo asigna el sistema, por ahora solo lo haremos random existe
		// una peque�a posibilidad de que se repita pero vamos a obviar esto.
		int identificador = (int) (Math.random() * 1500000000);
		int diaId, duracion, cantidadDeAsistentes;
		try {
			log.info("Creando capacitación...");
			diaId = Integer.parseInt(params[1]);
			duracion = Integer.parseInt(params[4]);
			cantidadDeAsistentes = Integer.parseInt(params[5]);
			System.out.println("Datos Capacitación : ");
			System.out.println("Día : " + diaId);
			System.out.println("Duración : " + duracion);
			System.out.println("Cantidad de asistentes : " + cantidadDeAsistentes);
		} catch (Exception e) {
			//System.out.println(e);
			log.error("Ocurrió el siguiente error : " + e);
			return false;
		}
		return capacitacionDao.save(new CapacitacionDTO(identificador, diaId, params[2], params[3], duracion,
				cantidadDeAsistentes, params[6]));
	}

	public List<CapacitacionDTO> obtenerListaCapacitaciones() {
		log.info("Mostrando lista de capacitaciones...");
		return capacitacionDao.getAll();
	}

	public List<CapacitacionDTO> obtenerListaCapacitacionesCliente(String rutCliente) {
		log.info("Mostrando lista de capacitaciones según cliente");
		return capacitacionDao.getFilterCliente(rutCliente);
	}
}

