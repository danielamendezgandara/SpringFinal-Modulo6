package cl.awakelab.empresaprevencionriesgos.model.dao.MySQL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import cl.awakelab.empresaprevencionriesgos.model.beans.ClienteDTO;
import cl.awakelab.empresaprevencionriesgos.model.beans.ContactoDTO;
import cl.awakelab.empresaprevencionriesgos.model.beans.UsuarioDTO;
import cl.awakelab.empresaprevencionriesgos.model.dao.IContactoDAO;



@Repository("MySQLContactoJDBC")
public class MySQLContactoImpl implements IContactoDAO{
    
	@Autowired
	JdbcTemplate template;
	
	public void setTemplate(JdbcTemplate template) {
		this.template = template;
	}
	
	
	@Override
	public boolean save(ContactoDTO contacto) {
		boolean isOkQuery = false;

		try {
			int clienteId = 0;
			String sql = "select cliente_id from cliente inner join usuario on cliente.usuario_id=usuario.usuario_id where usuario.rut=?";
		    clienteId = template.queryForObject(sql,Integer.class,contacto.getRutCliente());
			sql = "insert into contacto values (null,?,?)";
			template.update(sql,contacto.getMensaje(),clienteId);
			System.out.println("Insert en tabla contacto realizado");
			isOkQuery = true;
		} catch (Exception e) {
			System.out.println(e);
		}
		return isOkQuery;
	}

	@Override
	public List<String> getDataContacto(ContactoDTO contacto) {
		ArrayList<String> listaContacto = new ArrayList<String>();
		UsuarioDTO usuarioDTO = null;
		ClienteDTO clienteDTO = null;
		try {
		int usuarioId = 0;
		String nombres = "";
		String apellidos = "";
		String email = "";
		String organizacion = "";
		String sql = "select * from usuario where usuario.rut =?";
		usuarioDTO = template.queryForObject(sql, new ContactoUsuarioRowMapper(),contacto.getRutCliente());
		sql = "select usuario_id from usuario where usuario.rut=?";
	    usuarioId = template.queryForObject(sql,Integer.class,contacto.getRutCliente());
	    sql = "select email,organizacion from cliente where cliente.usuario_id =?";
	    clienteDTO = template.queryForObject(sql, new ContactoClienteRowMapper(),usuarioId);
		nombres = usuarioDTO.getNombres();
		apellidos = usuarioDTO.getApellidos();
		email = clienteDTO.getEmail();
		organizacion = clienteDTO.getOrganizacion();
	
		listaContacto.add(nombres);
		listaContacto.add(apellidos);
		listaContacto.add(email);
		listaContacto.add(organizacion);
		listaContacto.add(contacto.getMensaje());
		
		}catch(Exception e) {
		  System.out.println(e);
		}
		
		return listaContacto;
	}
	
	
	class ContactoUsuarioRowMapper implements RowMapper<UsuarioDTO>{

		@Override
		public UsuarioDTO mapRow(ResultSet rs, int rownum) throws SQLException {
			
			LocalDate fechaDeNacimiento = LocalDate.parse(rs.getString("fecha_de_nacimiento"));
			UsuarioDTO usuario = new UsuarioDTO(rs.getString("nick"), rs.getString("password1"), rs.getString("rut"),
					rs.getString("nombres"), rs.getString("apellidos"), fechaDeNacimiento, rs.getInt("perfil_id"));
			return usuario;
		}	
		
	}
	
	class ContactoClienteRowMapper implements RowMapper<ClienteDTO>{

		@Override
		public ClienteDTO mapRow(ResultSet rs, int rownum) throws SQLException {
			
			ClienteDTO cliente = new ClienteDTO();
			cliente.setEmail(rs.getString("email"));
			cliente.setOrganizacion(rs.getString("organizacion"));
			return cliente;
		}

		
		
	}

}
