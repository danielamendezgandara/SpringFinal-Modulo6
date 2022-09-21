package cl.awakelab.empresaprevencionriesgos.model.dao.MySQL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import cl.awakelab.empresaprevencionriesgos.model.beans.ClienteDTO;
import cl.awakelab.empresaprevencionriesgos.model.dao.IClienteDAO;


@Repository("MySQLClienteJDBC")
public class MySQLClienteImpl  implements IClienteDAO{
	
	@Autowired
	JdbcTemplate template;
	
	public void setTemplate(JdbcTemplate template) {
		this.template = template;
	}


	@Override
	public ClienteDTO get(String rut) {
		ClienteDTO cliente = null;
		try {
			String sql = "select * from cliente inner join usuario on cliente.usuario_id=usuario.usuario_id where rut =?";
			cliente = template.queryForObject(sql, new ClienteRowMapper(),rut);
            System.out.println("Cliente get existe");
		} catch (Exception e) {
			System.out.println(e);
		}

		return cliente;
	}

	@Override
	public boolean save(ClienteDTO cliente) {
		boolean isOkQuery = false;

		try {
			int usuarioId = -1;
			String sql = "insert into usuario (usuario_id, nick, password1, rut, nombres, apellidos, fecha_de_nacimiento, perfil_id) values (null,?,?,?,?,?,?,?)";
			template.update(sql,cliente.getNick(),cliente.getPassword1(),cliente.getRut(),cliente.getNombres(),cliente.getApellidos(),
					java.sql.Date.valueOf(cliente.getFechaDeNacimiento()),cliente.getPerfilId());
			sql = "select usuario_id from usuario where rut=?";
			usuarioId = template.queryForObject(sql,Integer.class,cliente.getRut());
			sql = "insert into cliente values (null,?,?,?,?,?,?,?,?)";
			template.update(sql,cliente.getTelefono(),cliente.getEmail(),cliente.getAfpId(),cliente.getSistemaSaludId(),cliente.getDireccion(),cliente.getComuna(),
					cliente.getOrganizacion(),usuarioId);
			System.out.println("Insert para crear nuevo cliente ok");
			isOkQuery = true;
		} catch (Exception e) {
			System.out.println(e);
		}
		return isOkQuery;
	}

	@Override
	public boolean update(ClienteDTO cliente) {
		boolean isOkQuery = false;

		try {
			int usuarioId = -1;
			String sql = "update usuario set nick=?, password1=?, nombres=?, apellidos=?, fecha_de_nacimiento=? where rut=?";
			template.update(sql,cliente.getNick(),cliente.getPassword1(),cliente.getNombres(),cliente.getApellidos(),
					java.sql.Date.valueOf(cliente.getFechaDeNacimiento()),cliente.getRut());
			sql = "select usuario_id from usuario where rut=?";
			usuarioId = template.queryForObject(sql,Integer.class,cliente.getRut());
			sql = "update cliente set telefono=?, email=?, afp_id=?, sistema_salud_id=?, direccion=?, comuna=?, organizacion=? where usuario_id=?";
			template.update(sql,cliente.getTelefono(),cliente.getEmail(),cliente.getAfpId(),cliente.getSistemaSaludId(),cliente.getDireccion(),cliente.getComuna(),
					cliente.getOrganizacion(),usuarioId);
			System.out.println("Insert para crear nuevo administrativo ok");
			isOkQuery = true;
		} catch (Exception e) {
			System.out.println(e);
		}
		return isOkQuery;
	}
	
	class ClienteRowMapper implements RowMapper<ClienteDTO>{

		@Override
		public ClienteDTO mapRow(ResultSet rs, int rownum) throws SQLException {
			
			LocalDate fechaDeNacimiento = LocalDate.parse(rs.getString("fecha_de_nacimiento"));
			ClienteDTO cliente = new ClienteDTO(rs.getString("nick"), rs.getString("password1"), rs.getString("rut"),
					rs.getString("nombres"), rs.getString("apellidos"), fechaDeNacimiento, rs.getInt("perfil_id"),
					rs.getString("telefono"), rs.getString("email"), rs.getInt("afp_id"),
					rs.getInt("sistema_salud_id"), rs.getString("direccion"), rs.getString("comuna"),
					rs.getString("organizacion"));
			return cliente;
		}

		
		
	}

}
