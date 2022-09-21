package cl.awakelab.empresaprevencionriesgos.model.dao.MySQL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import cl.awakelab.empresaprevencionriesgos.model.beans.AdministrativoDTO;
import cl.awakelab.empresaprevencionriesgos.model.dao.IAdministrativoDAO;

@Repository("MySQLAdministrativoJDBC")
public class MySQLAdministrativoImpl implements IAdministrativoDAO{
	
	@Autowired
	JdbcTemplate template;
	
	public void setTemplate(JdbcTemplate template) {
		this.template = template;
	}

	@Override
	public AdministrativoDTO get(String rut) {
		AdministrativoDTO administrativo = null;
		try {
			String sql = "select * from usuario inner join administrativo on usuario.usuario_id = administrativo.usuario_id where rut =?";
			administrativo = template.queryForObject(sql, new AdministrativoRowMapper(),rut);
            System.out.println("Administrativo get ok");
		} catch (Exception e) {
			System.out.println(e);
		}

		return administrativo;
	}

	@Override
	public boolean save(AdministrativoDTO administrativo) {
		boolean isOkQuery = false;
		try {
			int usuarioId = -1;
			String sql = "insert into usuario (usuario_id, nick, password1, rut, nombres, apellidos, fecha_de_nacimiento, perfil_id) values (null,?,?,?,?,?,?,?)";
			template.update(sql,administrativo.getNick(),administrativo.getPassword1(),administrativo.getRut(),administrativo.getNombres(),administrativo.getApellidos(),
					 java.sql.Date.valueOf(administrativo.getFechaDeNacimiento()),administrativo.getPerfilId());
			sql = "select usuario_id from usuario where rut=?";
			usuarioId = template.queryForObject(sql,Integer.class,administrativo.getRut());
			sql = "insert into administrativo values (null,?,?,?)";
			template.update(sql,administrativo.getArea(),administrativo.getExperienciaPrevia(),usuarioId);
			System.out.println("Insert para crear nuevo administrativo ok");
			isOkQuery = true;
		} catch (Exception e) {
			System.out.println(e);
		}
		return isOkQuery;
	}

	@Override
	public boolean update(AdministrativoDTO administrativo) {
		boolean isOkQuery = false;
		try {
			int usuarioId = -1;
			String sql = "update usuario set nick=?, password1=?, nombres=?, apellidos=?, fecha_de_nacimiento=? where rut=?";
			template.update(sql,administrativo.getNick(),administrativo.getPassword1(),administrativo.getNombres(),administrativo.getApellidos(),
              java.sql.Date.valueOf(administrativo.getFechaDeNacimiento()), administrativo.getRut());
			sql = "select usuario_id from usuario where rut=?";
			usuarioId = template.queryForObject(sql,Integer.class,administrativo.getRut());
			sql = "update administrativo set area=?, experiencia_previa=? where usuario_id=?";
			template.update(sql,administrativo.getArea(),administrativo.getExperienciaPrevia(),usuarioId);
			System.out.println("Update de administrativo ok");
			isOkQuery = true;
			
		} catch (Exception e) {
			System.out.println(e);
		}
		return isOkQuery;
	}
	
	
	class AdministrativoRowMapper implements RowMapper<AdministrativoDTO>{

		@Override
		public AdministrativoDTO mapRow(ResultSet rs, int rownum) throws SQLException {
			
			LocalDate fechaDeNacimiento = LocalDate.parse(rs.getString("fecha_de_nacimiento"));
			AdministrativoDTO administrativo = new AdministrativoDTO(rs.getString("nick"), rs.getString("password1"),
					rs.getString("rut"), rs.getString("nombres"), rs.getString("apellidos"), fechaDeNacimiento,
					rs.getInt("perfil_id"), rs.getString("area"), rs.getString("experiencia_previa"));
			return administrativo;
		}

		
		
	}
	

}
