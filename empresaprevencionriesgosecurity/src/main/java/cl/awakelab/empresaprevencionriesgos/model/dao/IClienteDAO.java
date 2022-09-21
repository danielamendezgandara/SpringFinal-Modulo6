package cl.awakelab.empresaprevencionriesgos.model.dao;

import org.springframework.stereotype.Component;

import cl.awakelab.empresaprevencionriesgos.model.beans.ClienteDTO;

@Component
public interface IClienteDAO {

    public ClienteDTO get(String rut);
    
    //public List<ClienteDTO> getAll();
    
    public boolean save(ClienteDTO cliente);//crear
    
    public boolean update(ClienteDTO cliente);
    
    //public void delete(String rut);
}

