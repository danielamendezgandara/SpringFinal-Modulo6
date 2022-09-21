package cl.awakelab.empresaprevencionriesgos.model.services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import cl.awakelab.empresaprevencionriesgos.model.beans.ContactoDTO;
import cl.awakelab.empresaprevencionriesgos.model.dao.IContactoDAO;
import lombok.extern.slf4j.Slf4j;


@Service("ContactoService")
@Slf4j
public class ContactoService {

	@Autowired
	@Qualifier("MySQLContactoJDBC")
	private IContactoDAO contactoDao; // = new MySQLContactoDAO();

	public boolean addContacto(ContactoDTO contacto) {

		if (contactoDao.save(contacto)) {
			log.info("Contacto agregado exitosamente");
			return true;
		} else {
			log.error("Ocurrió un error, no se pudo registar contacto");
			return false;
		}
	}

	public void showDataContacto(ContactoDTO contacto) {
		ArrayList<String> dataContacto = (ArrayList<String>) contactoDao.getDataContacto(contacto);
        log.info("Mostrando data de contacto");
		System.out.println("Informacion sobre el mensaje de contacto enviado : ");
		System.out.println("Nombre completo : " + dataContacto.get(0) + " " + dataContacto.get(1));
		System.out.println("email : " + dataContacto.get(2));
		System.out.println("Compania/Organizacion : " + dataContacto.get(3));
		System.out.println("Mensaje : " + dataContacto.get(4));
	}
}
