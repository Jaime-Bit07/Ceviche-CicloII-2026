package pe.edu.upeu.sysventas.service.impl;

import pe.edu.upeu.sysventas.model.Usuario;
import pe.edu.upeu.sysventas.repository.ICrudGenericoRepository;
import pe.edu.upeu.sysventas.repository.UsuarioRepository;
import pe.edu.upeu.sysventas.service.IUsuarioService;

import java.util.Optional;

public class UsuarioServiceImp extends CrudGenericoServiceImp<Usuario,Long> implements IUsuarioService {
    private final UsuarioRepository repo;
    public UsuarioServiceImp(UsuarioRepository repo) {
        this.repo = repo;
    }
    @Override
    protected ICrudGenericoRepository<Usuario, Long> getRepo() {
        return repo;
    }

    @Override
    public Optional<Usuario> loginUsuario(String usuario, String clave) {
        return repo.findByUsuarioAndClave(usuario, clave);
    }


}
