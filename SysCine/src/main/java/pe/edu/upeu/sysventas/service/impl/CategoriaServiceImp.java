package pe.edu.upeu.sysventas.service.impl;

import pe.edu.upeu.sysventas.dto.ComboBoxOption;
import pe.edu.upeu.sysventas.model.Categoria;
import pe.edu.upeu.sysventas.model.Marca;
import pe.edu.upeu.sysventas.repository.CategoriaRepository;
import pe.edu.upeu.sysventas.repository.ICrudGenericoRepository;
import pe.edu.upeu.sysventas.service.ICategoriaService;

import java.util.ArrayList;
import java.util.List;

public class CategoriaServiceImp extends CrudGenericoServiceImp<Categoria, Long>
        implements ICategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaServiceImp(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    @Override
    protected ICrudGenericoRepository<Categoria, Long> getRepo() {
        return categoriaRepository;
    }


    @Override
    public List<ComboBoxOption> listarCombobox() {
        if(categoriaRepository.findAll().isEmpty()) {
            categoriaRepository.seedData();
        }
        List<ComboBoxOption> listar = new ArrayList<>();
        for (Categoria m : categoriaRepository.findAll()) {
            ComboBoxOption cb = new ComboBoxOption();
            cb.setKey(String.valueOf(m.getIdCategoria()));
            cb.setValue(m.getNombre());
            listar.add(cb);
        }
        return listar;
    }
}
