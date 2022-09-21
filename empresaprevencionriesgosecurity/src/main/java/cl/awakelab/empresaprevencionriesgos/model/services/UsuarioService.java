package cl.awakelab.empresaprevencionriesgos.model.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import cl.awakelab.empresaprevencionriesgos.model.beans.UsuarioDTO;
import cl.awakelab.empresaprevencionriesgos.model.dao.IUsuarioDAO;
import lombok.extern.slf4j.Slf4j;



@Service("UsuarioService")
@Slf4j
public class UsuarioService {

	@Autowired
	@Qualifier("MySQLUsuarioJDBC")
	private IUsuarioDAO usuarioDao; // = new MySQLUsuarioDAO();

	public UsuarioDTO get(String nick, String password) {
		log.info("Obteniendo usuario nick " + nick + " y password " + password);
		return usuarioDao.get(nick, password);
	}

	public List<UsuarioDTO> obtenerListaUsuarios() {
		log.info("Obteniendo lista de usuarios...");
		return usuarioDao.getAll();
	}

	public boolean eliminarUsuario(String rut) {
		log.info("Eliminando usuario rut " + rut);
		return usuarioDao.delete(rut);
	}

}

