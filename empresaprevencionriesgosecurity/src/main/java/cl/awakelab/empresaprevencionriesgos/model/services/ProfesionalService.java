package cl.awakelab.empresaprevencionriesgos.model.services;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import cl.awakelab.empresaprevencionriesgos.model.beans.ProfesionalDTO;
import cl.awakelab.empresaprevencionriesgos.model.dao.IProfesionalDAO;
import lombok.extern.slf4j.Slf4j;


@Service("ProfesionalService")
@Slf4j
public class ProfesionalService {

	@Autowired
	@Qualifier("MySQLProfesionalJDBC")
	private IProfesionalDAO profesionalDao;// = new MySQLProfesionalDAO();

	// nick,password1,rut,nombres,apellidos,fechaDeNacimiento,perfilId,titulo,fechaDeIngreso
	private ProfesionalDTO validarProfesional(String[] params) {
		int perfilId;
		LocalDate fechaDeNacimiento;
		LocalDate fechaDeIngreso;
		try {
			log.info("Validando la creación de profesional");
			perfilId = Integer.parseInt(params[6]);
			String[] arregloFecha = params[5].split("-");
			fechaDeNacimiento = LocalDate.parse(arregloFecha[2] + '-' + arregloFecha[1] + '-' + arregloFecha[0]);
			arregloFecha = params[8].split("-");
			fechaDeIngreso = LocalDate.parse(arregloFecha[2] + '-' + arregloFecha[1] + '-' + arregloFecha[0]);
		} catch (Exception e) {
			System.out.println(e);
			log.error("Ocurrió el siguiente error : " + e);
			return null;
		}
		return new ProfesionalDTO(params[0], params[1], params[2], params[3], params[4], fechaDeNacimiento, perfilId,
				params[7], fechaDeIngreso);
	}

	public boolean crearProfesional(String[] params) {
		ProfesionalDTO obj = validarProfesional(params);
		if (obj == null) {
			log.error("Objeto profesional null");
			return false;
		}
		return profesionalDao.save(obj);
	}

	public boolean editarProfesional(String[] params) {
		ProfesionalDTO obj = validarProfesional(params);
		if (obj == null) {
			log.error("Objeto profesional null , no se puede editar");
			return false;
		}
		return profesionalDao.update(obj);
	}

	public ProfesionalDTO obtenerProfesional(String rut) {
		log.info("Obteniendo profesional rut " + rut);
		return profesionalDao.get(rut);
	}
}
