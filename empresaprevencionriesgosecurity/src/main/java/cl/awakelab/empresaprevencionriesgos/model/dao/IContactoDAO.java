package cl.awakelab.empresaprevencionriesgos.model.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import cl.awakelab.empresaprevencionriesgos.model.beans.ContactoDTO;

@Component
public interface IContactoDAO {

    //Optional<T> get(long id);
    
    //List<T> getAll();
    
    public boolean save(ContactoDTO contacto);
    
    //void update(T t, String[] params);
    
    //void delete(T t);
    public List<String> getDataContacto(ContactoDTO contacto);
}
