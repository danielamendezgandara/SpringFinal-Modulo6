package cl.awakelab.empresaprevencionriesgos.model.services;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import cl.awakelab.empresaprevencionriesgos.model.beans.ClienteDTO;
import cl.awakelab.empresaprevencionriesgos.model.dao.IClienteDAO;
import lombok.extern.slf4j.Slf4j;


@Service("ClienteService")
@Slf4j
public class ClienteService {

	@Autowired
	@Qualifier("MySQLClienteJDBC")
	private IClienteDAO clienteDao; // = new MySQLClienteDAO();

	// nick,password1,rut,nombres,apellidos,fechaDeNacimiento,perfilId,telefono,email,afpId,sistemaSaludId,direccion,comuna,organizacion
	private ClienteDTO validarCliente(String[] params) {
		LocalDate fechaDeNacimiento;
		int perfilId, afpId, sistemaSaludId;
		try {
			log.info("Validando cliente creado");
			String[] arregloFecha = params[5].split("-");
			fechaDeNacimiento = LocalDate.parse(arregloFecha[2] + '-' + arregloFecha[1] + '-' + arregloFecha[0]);
			perfilId = Integer.parseInt(params[6]);
			afpId = Integer.parseInt(params[9]);
			sistemaSaludId = Integer.parseInt(params[10]);
		} catch (Exception e) {
			System.out.println(e);
			log.error("Ocurrió el siguiente error : " + e);
			return null;
		}
		return new ClienteDTO(params[0], params[1], params[2], params[3], params[4], fechaDeNacimiento, perfilId,
				params[7], params[8], afpId, sistemaSaludId, params[11], params[12], params[13]);
	}

	public boolean crearCliente(String[] params) {
		ClienteDTO obj = validarCliente(params);
		if (obj == null) {
			log.error("Objeto cliente nulo, no se puedo crear cliente");
			return false;
		}
		return clienteDao.save(obj);
	}

	public boolean editarCliente(String[] params) {
		ClienteDTO obj = validarCliente(params);
		if (obj == null) {
			log.error("Objeto cliente nulo, no se puedo editar cliente");
			return false;
		}
		return clienteDao.update(obj);
	}

	public ClienteDTO obtenerCliente(String rut) {
		log.info("Se obtiene cliente rut "+ rut + " para crear capacitación ");
		return clienteDao.get(rut);
	}
}
