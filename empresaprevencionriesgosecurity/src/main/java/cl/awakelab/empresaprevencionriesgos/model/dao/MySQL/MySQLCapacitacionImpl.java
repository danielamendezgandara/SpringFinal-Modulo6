package cl.awakelab.empresaprevencionriesgos.model.dao.MySQL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import cl.awakelab.empresaprevencionriesgos.model.beans.CapacitacionDTO;
import cl.awakelab.empresaprevencionriesgos.model.dao.ICapacitacionDAO;


@Repository("MySQLCapacitacionJDBC")
public class MySQLCapacitacionImpl implements ICapacitacionDAO{
	
	@Autowired
	JdbcTemplate template;
	
	public void setTemplate(JdbcTemplate template) {
		this.template = template;
	}

	@Override
	public List<CapacitacionDTO> getAll() {
		List<CapacitacionDTO> lista = new ArrayList<CapacitacionDTO>();
		
		try {
			String sql = "select identificador, capacitacion.dia_id, hora, lugar,duracion,cantidad_de_asistentes, rut from usuario "
					+ "inner join cliente on usuario.usuario_id=cliente.usuario_id "
					+ "inner join capacitacion on cliente.cliente_id=capacitacion.cliente_id "
					+ "inner join dia on capacitacion.dia_id=dia.dia_id ";
			lista = template.query(sql,new CapacitacionRowMapper());
		    System.out.println("Lista de capacitaciones obtenida");
		} catch (Exception e) {
			System.out.println(e);
		}
		
		return lista;
	}

	@Override
	public List<CapacitacionDTO> getFilterCliente(String rut) {
		List<CapacitacionDTO> lista = new ArrayList<CapacitacionDTO>();
		try {
			String sql = "select identificador, capacitacion.dia_id, hora, lugar,duracion,cantidad_de_asistentes, rut from usuario "
					+ "inner join cliente on usuario.usuario_id=cliente.usuario_id "
					+ "inner join capacitacion on cliente.cliente_id=capacitacion.cliente_id "
					+ "inner join dia on capacitacion.dia_id=dia.dia_id where rut=? ";
			lista = template.query(sql,new CapacitacionRowMapper(),rut);
		    System.out.println("Lista de capacitaciones obtenida");
		} catch (Exception e) {
			System.out.println(e);
		}

		return lista;
	}

	@Override
	public boolean save(CapacitacionDTO capacitacion) {
		boolean isOkQuery = false;

		try {
			int clienteId = 0;
			String rut = capacitacion.getRutCliente();
			String sql = "select cliente_id from cliente inner join usuario on cliente.usuario_id=usuario.usuario_id where usuario.rut=?";
			clienteId  = template.queryForObject(sql,Integer.class,rut);
			sql = "insert into capacitacion values (null,?,?,?,?,?,?,?)";
			template.update(sql,(int) capacitacion.getIdentificador(),capacitacion.getDiaId(),capacitacion.getHora(),
					capacitacion.getLugar(),capacitacion.getDuracion(),capacitacion.getCantidadDeAsistentes(),
					clienteId);
			System.out.println("Insert en tabla capacitacion realizado");
			System.out.println(capacitacion);
			isOkQuery = true;
			
		} catch (Exception e) {
			System.out.println(e);
		}
		return isOkQuery;
	}

	
	
	class CapacitacionRowMapper implements RowMapper<CapacitacionDTO>{

		@Override
		public CapacitacionDTO mapRow(ResultSet rs, int rownum) throws SQLException {
			
			CapacitacionDTO capacitacion = new CapacitacionDTO(rs.getInt("identificador"), rs.getInt("dia_id"), rs.getString("hora"),
					rs.getString("lugar"), rs.getInt("duracion"), rs.getInt("cantidad_de_asistentes"),
					rs.getString("rut"));
			return capacitacion;
		}
		
	}
	

}
