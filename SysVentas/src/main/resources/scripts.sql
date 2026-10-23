MERGE INTO usuario (id_usuario, usuario, clave, estado, id_perfil) KEY (id_usuario)
    VALUES (2, 'davidmp', '123456', 'ACTIVO', 1),
            (3, 'raul', '123456', 'ACTIVO', 1),
            (4, 'pedrito', '123456', 'ACTIVO', 3);