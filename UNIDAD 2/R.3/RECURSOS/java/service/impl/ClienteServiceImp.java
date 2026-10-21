package pe.edu.upeu.sysventas.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pe.edu.upeu.sysventas.dto.ModeloDataAutocomplet;
import pe.edu.upeu.sysventas.model.Cliente;
import pe.edu.upeu.sysventas.repository.ClienteRepository;
import pe.edu.upeu.sysventas.repository.ICrudGenericoRepository;
import pe.edu.upeu.sysventas.service.IClienteService;

import java.util.ArrayList;
import java.util.List;

public class ClienteServiceImp extends CrudGenericoServiceImp<Cliente, String>
        implements IClienteService {

    private static final Logger logger = LoggerFactory.getLogger(ClienteServiceImp.class);
    private final ClienteRepository clienteRepository;

    public ClienteServiceImp(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    protected ICrudGenericoRepository<Cliente, String> getRepo() {
        return clienteRepository;
    }

    @Override
    public List<ModeloDataAutocomplet> listAutoCompletCliente() {
        List<ModeloDataAutocomplet> listarclientes = new ArrayList<>();
        try {
            for (Cliente cliente : clienteRepository.findAll()) {
                ModeloDataAutocomplet data = new ModeloDataAutocomplet();
                data.setIdx(cliente.getDniruc());
                data.setNameDysplay(cliente.getNombres());
                data.setOtherData(cliente.getTipoDocumento().name());
                listarclientes.add(data);
            }
        } catch (Exception e) {
            logger.error("Error durante la operación", e);
        }
        return listarclientes;
    }
}
