package pe.edu.upeu.sysventas.service.impl;

import pe.edu.upeu.sysventas.dto.ComboBoxOption;
import pe.edu.upeu.sysventas.model.Categoria;
import pe.edu.upeu.sysventas.model.UnidMedida;
import pe.edu.upeu.sysventas.repository.ICrudGenericoRepository;
import pe.edu.upeu.sysventas.repository.UnidadMedidaRepository;
import pe.edu.upeu.sysventas.service.IUnidadMedidaService;

import java.util.ArrayList;
import java.util.List;

public class UnidadMedidaServiceImp extends CrudGenericoServiceImp<UnidMedida, Long> implements IUnidadMedidaService {

    private final UnidadMedidaRepository unidadMedidaRepository;

    public UnidadMedidaServiceImp(UnidadMedidaRepository unidadMedidaRepository) {
        this.unidadMedidaRepository = unidadMedidaRepository;
    }

    @Override
    protected ICrudGenericoRepository<UnidMedida, Long> getRepo() {
        return unidadMedidaRepository;
    }

    @Override
    public List<ComboBoxOption> listarCombobox() {
        if(unidadMedidaRepository.findAll().isEmpty()) {
            unidadMedidaRepository.seedData();
        }
        List<ComboBoxOption> listar = new ArrayList<>();
        for (UnidMedida m : unidadMedidaRepository.findAll()) {
            ComboBoxOption cb = new ComboBoxOption();
            cb.setKey(String.valueOf(m.getIdUnidad()));
            cb.setValue(m.getNombreMedida());
            listar.add(cb);
        }
        return listar;
    }
}
