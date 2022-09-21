package cl.awakelab.empresaprevencionriesgos.model.dao;

import org.springframework.stereotype.Component;

import cl.awakelab.empresaprevencionriesgos.model.beans.ProfesionalDTO;

@Component
public interface IProfesionalDAO {

	public ProfesionalDTO get(String rut);

	//public List<ProfesionalDTO> getAll();

	public boolean save(ProfesionalDTO profesional);

	public boolean update(ProfesionalDTO profesional);

	//public void delete(ProfesionalDTO profesional);
}
