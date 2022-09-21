package cl.awakelab.empresaprevencionriesgos.model.services;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import cl.awakelab.empresaprevencionriesgos.model.beans.AdministrativoDTO;
import cl.awakelab.empresaprevencionriesgos.model.dao.IAdministrativoDAO;
import lombok.extern.slf4j.Slf4j;

@Service("AdministrativoService")
@Slf4j
public class AdministrativoService {

	@Autowired
	@Qualifier("MySQLAdministrativoJDBC")
	private IAdministrativoDAO administrativoDao; // = new MySQLAdministrativoDAO();

	// nick,password1,rut,nombres,apellidos,fechaDeNacimiento,perfilId,area,experienciaPrevia
	private AdministrativoDTO validarAdministrativo(String[] params) {
		int perfilId;
		LocalDate fechaDeNacimiento;
		try {
			log.info("Validando creación de administrativo");
			perfilId = Integer.parseInt(params[6]);
			String[] arregloFecha = params[5].split("-");
			fechaDeNacimiento = LocalDate.parse(arregloFecha[2] + '-' + arregloFecha[1] + '-' + arregloFecha[0]);
		} catch (Exception e) {
			//System.out.println(e);
			log.error("Ocurrió el siguiente error : " + e);
			return null;
		}
		return new AdministrativoDTO(params[0], params[1], params[2], params[3], params[4], fechaDeNacimiento, perfilId,
				params[7], params[8]);
	}

	public boolean crearAdministrativo(String[] params) {
		AdministrativoDTO obj = validarAdministrativo(params);
		if (obj == null) {
			log.error("Objeto administrativo null");
			return false;
		}
		return administrativoDao.save(obj);
	}

	public boolean editarAdministrativo(String[] params) {
		AdministrativoDTO obj = validarAdministrativo(params);
		if (obj == null) {
			log.error("Objeto administrativo null");
			return false;
		}
		System.out.print("editar");
		return administrativoDao.update(obj);
	}

	public AdministrativoDTO obtenerAdministrativo(String rut) {
		log.info("Obteniendo administrativo rut " + rut);
		return administrativoDao.get(rut);
	}
}
