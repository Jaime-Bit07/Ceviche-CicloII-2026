-- SysVentas - esquema SQLite (idempotente: se puede ejecutar en cada arranque)

CREATE TABLE IF NOT EXISTS emisor (
                                      id_emisor        INTEGER PRIMARY KEY AUTOINCREMENT,
                                      ruc              VARCHAR(20) NOT NULL,
    nombre_comercial VARCHAR(60) NOT NULL,
    ubigeo           VARCHAR(10) NOT NULL,
    domicilio_fiscal VARCHAR(60) NOT NULL,
    urbanizacion     VARCHAR(40) NOT NULL,
    departamento     VARCHAR(30) NOT NULL,
    provincia        VARCHAR(30) NOT NULL,
    distrito         VARCHAR(30) NOT NULL
    );

CREATE TABLE IF NOT EXISTS perfil (
                                      id_perfil INTEGER PRIMARY KEY AUTOINCREMENT,
                                      nombre    VARCHAR(20) NOT NULL,
    codigo    VARCHAR(60) NOT NULL
    );

CREATE TABLE IF NOT EXISTS proveedor (
                                         id_proveedor INTEGER PRIMARY KEY AUTOINCREMENT,
                                         dniruc       VARCHAR(12) NOT NULL,
    nombres_raso VARCHAR(60) NOT NULL,
    tipo_doc     VARCHAR(12) NOT NULL,
    celular      VARCHAR(12) NOT NULL,
    email        VARCHAR(40) NOT NULL,
    direccion    VARCHAR(80) NOT NULL
    );

CREATE TABLE IF NOT EXISTS unid_medida (
                                           id_unidad     INTEGER PRIMARY KEY AUTOINCREMENT,
                                           nombre_medida VARCHAR(40) NOT NULL
    );

CREATE TABLE IF NOT EXISTS usuario (
                                       id_usuario INTEGER PRIMARY KEY AUTOINCREMENT,
                                       usuario    VARCHAR(20) NOT NULL,
    clave      VARCHAR(60) NOT NULL,
    estado     VARCHAR(10) NOT NULL,
    id_perfil  INTEGER NOT NULL REFERENCES perfil (id_perfil)
    );

CREATE TABLE IF NOT EXISTS comp_carrito (
                                            id_compcarrito  INTEGER PRIMARY KEY AUTOINCREMENT,
                                            id_proveedor    INTEGER NOT NULL,
                                            id_producto     INTEGER NOT NULL,
                                            nombre_producto VARCHAR(40) NOT NULL,
    cantidad        REAL NOT NULL,
    punitario       REAL NOT NULL,
    ptotal          REAL NOT NULL,
    estado          INTEGER NOT NULL,
    id_usuario      INTEGER NOT NULL REFERENCES usuario (id_usuario)
    );

CREATE TABLE IF NOT EXISTS vent_carrito (
                                            id_carrito      INTEGER PRIMARY KEY AUTOINCREMENT,
                                            dniruc          VARCHAR(12) NOT NULL,
    id_producto     INTEGER NOT NULL,
    nombre_producto VARCHAR(40) NOT NULL,
    cantidad        REAL NOT NULL,
    punitario       REAL NOT NULL,
    ptotal          REAL NOT NULL,
    estado          INTEGER NOT NULL,
    id_usuario      INTEGER NOT NULL REFERENCES usuario (id_usuario),
    tipo_producto   VARCHAR(20) NOT NULL
    );

CREATE TABLE IF NOT EXISTS compra (
                                      id_compra    INTEGER PRIMARY KEY AUTOINCREMENT,
                                      precio_base  REAL NOT NULL,
                                      igv          REAL NOT NULL,
                                      precio_total REAL NOT NULL,
                                      serie        VARCHAR(10) NOT NULL,
    num_doc      VARCHAR(12) NOT NULL,
    tipo_doc     VARCHAR(20) NOT NULL,
    fecha_comp   DATE NOT NULL,
    fecha_reg    DATE NOT NULL,
    id_proveedor INTEGER NOT NULL REFERENCES proveedor (id_proveedor),
    id_usuario   INTEGER NOT NULL REFERENCES usuario (id_usuario)
    );

CREATE TABLE IF NOT EXISTS cliente (
                                       dniruc         VARCHAR(12) PRIMARY KEY,
    nombres        VARCHAR(60) NOT NULL,
    rep_legal      VARCHAR(60) NOT NULL,
    direccion      VARCHAR(60) NOT NULL,
    tipo_documento VARCHAR(12) NOT NULL
    );

CREATE TABLE IF NOT EXISTS venta (
                                     id_venta     INTEGER PRIMARY KEY AUTOINCREMENT,
                                     precio_base  REAL NOT NULL,
                                     igv          REAL NOT NULL,
                                     precio_total REAL NOT NULL,
                                     serie        VARCHAR(10) NOT NULL,
    num_doc      VARCHAR(40) NOT NULL,
    tipo_doc     VARCHAR(20) NOT NULL,
    fecha_gener  DATE NOT NULL,
    dniruc       VARCHAR(12) NOT NULL REFERENCES cliente (dniruc),
    id_usuario   INTEGER NOT NULL REFERENCES usuario (id_usuario)
    );

CREATE TABLE IF NOT EXISTS marca (
                                     id_marca INTEGER PRIMARY KEY AUTOINCREMENT,
                                     nombre   VARCHAR(20) NOT NULL
    );

CREATE TABLE IF NOT EXISTS categoria (
                                         id_categoria INTEGER PRIMARY KEY AUTOINCREMENT,
                                         nombre       VARCHAR(20) NOT NULL
    );

CREATE TABLE IF NOT EXISTS producto (
                                        id_producto   INTEGER PRIMARY KEY AUTOINCREMENT,
                                        nombre        VARCHAR(40) NOT NULL,
    pu            REAL NOT NULL,
    puold         REAL NOT NULL,
    utilidad      REAL NOT NULL,
    stock         REAL NOT NULL,
    stockold      REAL NOT NULL,
    id_categoria  INTEGER NOT NULL REFERENCES categoria (id_categoria),
    id_marca      INTEGER NOT NULL REFERENCES marca (id_marca),
    id_unidad     INTEGER NOT NULL REFERENCES unid_medida (id_unidad),
    tipo_producto VARCHAR(20) NOT NULL
    );

CREATE TABLE IF NOT EXISTS venta_detalle (
                                             id_venta_detalle INTEGER PRIMARY KEY AUTOINCREMENT,
                                             pu               REAL NOT NULL,
                                             cantidad         REAL NOT NULL,
                                             descuento        REAL NOT NULL,
                                             subtotal         REAL NOT NULL,
                                             id_venta         INTEGER NOT NULL REFERENCES venta (id_venta),
    id_producto      INTEGER NOT NULL REFERENCES producto (id_producto)
    );

CREATE TABLE IF NOT EXISTS compra_detalle (
                                              id_compra_detalle INTEGER PRIMARY KEY AUTOINCREMENT,
                                              pu                REAL NOT NULL,
                                              cantidad          REAL NOT NULL,
                                              subtotal          REAL NOT NULL,
                                              id_compra         INTEGER NOT NULL REFERENCES compra (id_compra),
    id_producto       INTEGER NOT NULL REFERENCES producto (id_producto)
    );

-- Datos iniciales
INSERT OR IGNORE INTO perfil (id_perfil, nombre, codigo) VALUES
    (1, 'Root', 'ROOT'),
    (2, 'Administrador', 'ADM'),
    (3, 'Reporte', 'REP');

INSERT OR IGNORE INTO usuario (id_usuario, usuario, clave, estado, id_perfil)
VALUES (1, 'admin', 'admin123', 'ACTIVO', 1);

INSERT OR IGNORE INTO categoria (id_categoria, nombre) VALUES (1, 'Artefactos');

INSERT OR IGNORE INTO marca (id_marca, nombre) VALUES (1, 'LG');

INSERT OR IGNORE INTO unid_medida (id_unidad, nombre_medida) VALUES (1, 'Unidad');
