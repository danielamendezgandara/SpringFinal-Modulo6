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

import cl.awakelab.empresaprevencionriesgos.model.beans.UsuarioDTO;
import cl.awakelab.empresaprevencionriesgos.model.dao.IUsuarioDAO;


@Repository("MySQLUsuarioJDBC")
public class MySQLUsuarioImpl implements IUsuarioDAO{
	
	@Autowired
	JdbcTemplate template;
	
	public void setTemplate(JdbcTemplate template) {
		this.template = template;
	}

	@Override
	public UsuarioDTO get(String nick, String password1) {
		UsuarioDTO usuarioDTO = null;
		try {
			String sql = "select * from usuario where nick =?";
			usuarioDTO = template.queryForObject(sql, new UsuarioRowMapper(),nick);
			System.out.println("Login Exitoso");
		} catch (Exception e) {
			System.out.println(e);
		}

		return usuarioDTO;
	}

	@Override
	public List<UsuarioDTO> getAll() {
		// TODO Auto-generated method stub
		List<UsuarioDTO> lista = new ArrayList<UsuarioDTO>();
		try {
			String sql = "select * from usuario";
			lista = template.query(sql,new UsuarioRowMapper());
			System.out.println("Lista usuarios obtenida");
		} catch (Exception e) {
			System.out.println(e);
		}
		return lista;
	}

	@Override
	public boolean delete(String rut) {
		boolean isOkQuery = false;
		try {
			String sql = "delete from usuario where rut=?";
			template.update(sql,rut);
			System.out.println("Eliminacion usuario ok");
			isOkQuery = true;
		} catch (Exception e) {
			System.out.println(e);
		}

		return isOkQuery;
	}
	
	
	class UsuarioRowMapper implements RowMapper<UsuarioDTO>{

		@Override
		public UsuarioDTO mapRow(ResultSet rs, int rownum) throws SQLException {
			
			LocalDate fechaDeNacimiento = LocalDate.parse(rs.getString("fecha_de_nacimiento"));
			UsuarioDTO usuario = new UsuarioDTO(rs.getString("nick"), rs.getString("password1"), rs.getString("rut"),
					rs.getString("nombres"), rs.getString("apellidos"), fechaDeNacimiento, rs.getInt("perfil_id"));
			return usuario;
		}

		
		
	}

}
