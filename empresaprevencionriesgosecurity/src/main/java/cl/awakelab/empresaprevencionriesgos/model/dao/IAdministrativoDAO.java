package cl.awakelab.empresaprevencionriesgos.model.dao;

import org.springframework.stereotype.Component;

import cl.awakelab.empresaprevencionriesgos.model.beans.AdministrativoDTO;

@Component
public interface IAdministrativoDAO {

	public AdministrativoDTO get(String rut);
    
    //public List<AdministrativoDTO> getAll();
    
    public boolean save(AdministrativoDTO administrativo);
    
    public boolean update(AdministrativoDTO administrativo);
    
   // public void delete(AdministrativoDTO adminsitrativo);
}
