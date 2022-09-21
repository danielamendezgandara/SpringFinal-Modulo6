package cl.awakelab.empresaprevencionriesgos.model.dao.MySQL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import cl.awakelab.empresaprevencionriesgos.model.beans.ProfesionalDTO;
import cl.awakelab.empresaprevencionriesgos.model.dao.IProfesionalDAO;



@Repository("MySQLProfesionalJDBC")
public class MySQLProfesionalImpl implements IProfesionalDAO{
	
	@Autowired
	JdbcTemplate template;
	
	public void setTemplate(JdbcTemplate template) {
		this.template = template;
	}


	@Override
	public ProfesionalDTO get(String rut) {
		ProfesionalDTO profesional = null;
		try {
			String sql = "select * from usuario inner join profesional on usuario.usuario_id = profesional.usuario_id where rut =?";
			profesional = template.queryForObject(sql, new ProfesionalRowMapper(),rut);
			System.out.println("Profesional get ok");
		} catch (Exception e) {
			System.out.println(e);
		}

		return profesional;
	}

	@Override
	public boolean save(ProfesionalDTO profesional) {
		boolean isOkQuery = false;

		try {
			int usuarioId = -1;
			String sql = "insert into usuario (usuario_id, nick, password1, rut, nombres, apellidos, fecha_de_nacimiento, perfil_id) values (null,?,?,?,?,?,?,?)";
			template.update(sql,profesional.getNick(),profesional.getPassword1(),profesional.getRut(),profesional.getNombres(),profesional.getApellidos(),
					 java.sql.Date.valueOf(profesional.getFechaDeNacimiento()),profesional.getPerfilId());
			sql = "select usuario_id from usuario where rut=?";
			usuarioId = template.queryForObject(sql,Integer.class,profesional.getRut());
			sql = "insert into profesional values (null,?,?,?)";
			template.update(sql,profesional.getTitulo(),java.sql.Date.valueOf(profesional.getFechaDeIngreso()),usuarioId);
			System.out.println("Insert para crear nuevo profesional ok");
			isOkQuery = true;
		} catch (Exception e) {
			System.out.println(e);
		}
		return isOkQuery;
	}

	@Override
	public boolean update(ProfesionalDTO profesional) {
		boolean isOkQuery = false;

		try {
			int usuarioId = -1;
			String sql = "update usuario set nick=?, password1=?, nombres=?, apellidos=?, fecha_de_nacimiento=? where rut=?";
			template.update(sql,profesional.getNick(),profesional.getPassword1(),profesional.getNombres(), profesional.getApellidos(),
              java.sql.Date.valueOf(profesional.getFechaDeNacimiento()),profesional.getRut());
			sql = "select usuario_id from usuario where rut=?";
			usuarioId = template.queryForObject(sql,Integer.class,profesional.getRut());
			sql = "update profesional set titulo=?, fecha_de_ingreso=? where usuario_id=?";
			template.update(sql,profesional.getTitulo(),java.sql.Date.valueOf(profesional.getFechaDeIngreso()),usuarioId);
			System.out.println("Update de profesional ok");
			isOkQuery = true;
			
		} catch (Exception e) {
			System.out.println(e);
		}
		return isOkQuery;
	}
	
	class ProfesionalRowMapper implements RowMapper<ProfesionalDTO>{

		@Override
		public ProfesionalDTO mapRow(ResultSet rs, int rownum) throws SQLException {
			
			LocalDate fechaDeNacimiento = LocalDate.parse(rs.getString("fecha_de_nacimiento"));
			LocalDate fechaDeIngreso = LocalDate.parse(rs.getString("fecha_de_ingreso"));
			ProfesionalDTO profesional = new ProfesionalDTO(rs.getString("nick"), rs.getString("password1"),
					rs.getString("rut"), rs.getString("nombres"), rs.getString("apellidos"), fechaDeNacimiento,
					rs.getInt("perfil_id"), rs.getString("titulo"), fechaDeIngreso);
			return profesional;
		}

		
		
	}

}
