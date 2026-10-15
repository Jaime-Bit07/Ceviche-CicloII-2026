package pe.edu.upeu.sysventas.config;

import pe.edu.upeu.sysventas.cine.controller.CandyController;
import pe.edu.upeu.sysventas.cine.repository.CandyRepository;
import pe.edu.upeu.sysventas.cine.repository.ICandyRepository;
import pe.edu.upeu.sysventas.cine.service.ICandyService;
import pe.edu.upeu.sysventas.cine.service.impl.CandyServiceImpl;

import java.util.HashMap;
import java.util.Map;

public final class AppCandyContext {

    private static final AppCandyContext INSTANCE = new AppCandyContext();
    private final Map<Class<?>, Object> beans = new HashMap<>();

    private AppCandyContext() {
        ICandyRepository repository = new CandyRepository();
        ICandyService service = new CandyServiceImpl(repository);
        register(ICandyRepository.class, repository);
        register(ICandyService.class, service);
        register(CandyController.class, new CandyController(service));
    }

    public static AppCandyContext getInstance() {
        return INSTANCE;
    }

    private void register(Class<?> type, Object bean) {
        beans.put(type, bean);
    }

    public <T> T getBean(Class<T> type) {
        Object bean = beans.get(type);
        if (bean == null) {
            bean = beans.values().stream()
                    .filter(type::isInstance)
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException(
                            "No se registró el componente: " + type.getName()
                    ));
        }
        return type.cast(bean);
    }
}
