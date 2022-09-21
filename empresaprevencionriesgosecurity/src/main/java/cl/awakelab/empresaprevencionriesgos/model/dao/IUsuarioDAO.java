package cl.awakelab.empresaprevencionriesgos.model.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import cl.awakelab.empresaprevencionriesgos.model.beans.UsuarioDTO;


@Component
public interface IUsuarioDAO {

	public UsuarioDTO get(String nick, String password1);
    
    public List<UsuarioDTO> getAll();
    
    //void save(T t);
    
    //void update(T t, String[] params);
    
    boolean delete(String rut);
}

