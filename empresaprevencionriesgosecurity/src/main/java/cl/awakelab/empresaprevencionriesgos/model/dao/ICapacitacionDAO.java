package cl.awakelab.empresaprevencionriesgos.model.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import cl.awakelab.empresaprevencionriesgos.model.beans.CapacitacionDTO;

@Component
public interface ICapacitacionDAO {

    //Optional<T> get(long id);
    
	public List<CapacitacionDTO> getAll();
	
	public List<CapacitacionDTO> getFilterCliente(String rut);
    
	public boolean save(CapacitacionDTO capacitacion);
    
    //void update(T t, String[] params);
    
    //void delete(T t);
}
