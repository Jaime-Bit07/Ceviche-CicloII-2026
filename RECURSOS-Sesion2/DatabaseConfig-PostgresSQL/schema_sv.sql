
CREATE SEQUENCE public.emisor_id_emisor_seq;

CREATE TABLE public.emisor (
                               id_emisor INTEGER NOT NULL DEFAULT nextval('public.emisor_id_emisor_seq'),
                               ruc VARCHAR(20) NOT NULL,
                               nombre_comercial VARCHAR(60) NOT NULL,
                               ubigeo VARCHAR(10) NOT NULL,
                               domicilio_fiscal VARCHAR(60) NOT NULL,
                               urbanizacion VARCHAR(40) NOT NULL,
                               departamento VARCHAR(30) NOT NULL,
                               provincia VARCHAR(30) NOT NULL,
                               distrito VARCHAR(30) NOT NULL,
                               CONSTRAINT emisor_pk PRIMARY KEY (id_emisor)
);


ALTER SEQUENCE public.emisor_id_emisor_seq OWNED BY public.emisor.id_emisor;

CREATE SEQUENCE public.perfil_id_perfil_seq;

CREATE TABLE public.perfil (
                               id_perfil INTEGER NOT NULL DEFAULT nextval('public.perfil_id_perfil_seq'),
                               nombre VARCHAR(20) NOT NULL,
                               codigo VARCHAR(60) NOT NULL,
                               CONSTRAINT perfil_pk PRIMARY KEY (id_perfil)
);


ALTER SEQUENCE public.perfil_id_perfil_seq OWNED BY public.perfil.id_perfil;

CREATE SEQUENCE public.proveedor_id_proveedor_seq;

CREATE TABLE public.proveedor (
                                  id_proveedor INTEGER NOT NULL DEFAULT nextval('public.proveedor_id_proveedor_seq'),
                                  dniruc VARCHAR(12) NOT NULL,
                                  nombres_raso VARCHAR(60) NOT NULL,
                                  tipo_doc VARCHAR(12) NOT NULL,
                                  celular VARCHAR(12) NOT NULL,
                                  email VARCHAR(40) NOT NULL,
                                  direccion VARCHAR(80) NOT NULL,
                                  CONSTRAINT proveedor_pk PRIMARY KEY (id_proveedor)
);


ALTER SEQUENCE public.proveedor_id_proveedor_seq OWNED BY public.proveedor.id_proveedor;

CREATE SEQUENCE public.unid_medida_id_unidad_seq;

CREATE TABLE public.unid_medida (
                                    id_unidad INTEGER NOT NULL DEFAULT nextval('public.unid_medida_id_unidad_seq'),
                                    nombre_medida VARCHAR(40) NOT NULL,
                                    CONSTRAINT unid_medida_pk PRIMARY KEY (id_unidad)
);


ALTER SEQUENCE public.unid_medida_id_unidad_seq OWNED BY public.unid_medida.id_unidad;

CREATE SEQUENCE public.usuario_id_usuario_seq;

CREATE TABLE public.usuario (
                                id_usuario INTEGER NOT NULL DEFAULT nextval('public.usuario_id_usuario_seq'),
                                usuario VARCHAR(20) NOT NULL,
                                clave VARCHAR(60) NOT NULL,
                                estado VARCHAR(10) NOT NULL,
                                id_perfil INTEGER NOT NULL,
                                CONSTRAINT usuario_pk PRIMARY KEY (id_usuario)
);


ALTER SEQUENCE public.usuario_id_usuario_seq OWNED BY public.usuario.id_usuario;

CREATE SEQUENCE public.comp_carrito_id_compcarrito_seq;

CREATE TABLE public.comp_carrito (
                                     id_compcarrito INTEGER NOT NULL DEFAULT nextval('public.comp_carrito_id_compcarrito_seq'),
                                     id_proveedor INTEGER NOT NULL,
                                     id_producto INTEGER NOT NULL,
                                     nombre_producto VARCHAR(40) NOT NULL,
                                     cantidad DOUBLE PRECISION NOT NULL,
                                     punitario DOUBLE PRECISION NOT NULL,
                                     ptotal DOUBLE PRECISION NOT NULL,
                                     estado INTEGER NOT NULL,
                                     id_usuario INTEGER NOT NULL,
                                     CONSTRAINT comp_carrito_pk PRIMARY KEY (id_compcarrito)
);


ALTER SEQUENCE public.comp_carrito_id_compcarrito_seq OWNED BY public.comp_carrito.id_compcarrito;

CREATE SEQUENCE public.vent_carrito_id_carrito_seq;

CREATE TABLE public.vent_carrito (
                                     id_carrito INTEGER NOT NULL DEFAULT nextval('public.vent_carrito_id_carrito_seq'),
                                     dniruc VARCHAR(12) NOT NULL,
                                     id_producto INTEGER NOT NULL,
                                     nombre_producto VARCHAR(40) NOT NULL,
                                     cantidad DOUBLE PRECISION NOT NULL,
                                     punitario DOUBLE PRECISION NOT NULL,
                                     ptotal DOUBLE PRECISION NOT NULL,
                                     estado INTEGER NOT NULL,
                                     id_usuario INTEGER NOT NULL,
                                     tipo_producto VARCHAR(20) NOT NULL,
                                     CONSTRAINT vent_carrito_pk PRIMARY KEY (id_carrito)
);


ALTER SEQUENCE public.vent_carrito_id_carrito_seq OWNED BY public.vent_carrito.id_carrito;

CREATE SEQUENCE public.compra_id_compra_seq;

CREATE TABLE public.compra (
                               id_compra INTEGER NOT NULL DEFAULT nextval('public.compra_id_compra_seq'),
                               precio_base DOUBLE PRECISION NOT NULL,
                               igv DOUBLE PRECISION NOT NULL,
                               precio_total DOUBLE PRECISION NOT NULL,
                               serie VARCHAR(10) NOT NULL,
                               num_doc VARCHAR(12) NOT NULL,
                               tipo_doc VARCHAR(20) NOT NULL,
                               fecha_comp DATE NOT NULL,
                               fecha_reg DATE NOT NULL,
                               id_proveedor INTEGER NOT NULL,
                               id_usuario INTEGER NOT NULL,
                               CONSTRAINT compra_pk PRIMARY KEY (id_compra)
);


ALTER SEQUENCE public.compra_id_compra_seq OWNED BY public.compra.id_compra;

CREATE TABLE public.cliente (
                                dniruc VARCHAR(12) NOT NULL,
                                nombres VARCHAR(60) NOT NULL,
                                rep_legal VARCHAR(60) NOT NULL,
                                direccion VARCHAR(60) NOT NULL,
                                tipo_documento VARCHAR(12) NOT NULL,
                                CONSTRAINT cliente_pk PRIMARY KEY (dniruc)
);
COMMENT ON COLUMN public.cliente.tipo_documento IS '--dni--ruc';


CREATE SEQUENCE public.venta_id_venta_seq;

CREATE TABLE public.venta (
                              id_venta INTEGER NOT NULL DEFAULT nextval('public.venta_id_venta_seq'),
                              precio_base DOUBLE PRECISION NOT NULL,
                              igv DOUBLE PRECISION NOT NULL,
                              precio_total DOUBLE PRECISION NOT NULL,
                              serie VARCHAR(10) NOT NULL,
                              num_doc VARCHAR(40) NOT NULL,
                              tipo_doc VARCHAR(20) NOT NULL,
                              fecha_gener DATE NOT NULL,
                              dniruc VARCHAR(12) NOT NULL,
                              id_usuario INTEGER NOT NULL,
                              CONSTRAINT venta_pk PRIMARY KEY (id_venta)
);


ALTER SEQUENCE public.venta_id_venta_seq OWNED BY public.venta.id_venta;

CREATE SEQUENCE public.marca_id_marca_seq;

CREATE TABLE public.marca (
                              id_marca INTEGER NOT NULL DEFAULT nextval('public.marca_id_marca_seq'),
                              nombre VARCHAR(20) NOT NULL,
                              CONSTRAINT marca_pk PRIMARY KEY (id_marca)
);


ALTER SEQUENCE public.marca_id_marca_seq OWNED BY public.marca.id_marca;

CREATE SEQUENCE public.categoria_id_categoria_seq;

CREATE TABLE public.categoria (
                                  id_categoria INTEGER NOT NULL DEFAULT nextval('public.categoria_id_categoria_seq'),
                                  nombre VARCHAR(20) NOT NULL,
                                  CONSTRAINT categoria_pk PRIMARY KEY (id_categoria)
);


ALTER SEQUENCE public.categoria_id_categoria_seq OWNED BY public.categoria.id_categoria;

CREATE SEQUENCE public.producto_id_producto_seq;

CREATE TABLE public.producto (
                                 id_producto INTEGER NOT NULL DEFAULT nextval('public.producto_id_producto_seq'),
                                 nombre VARCHAR(40) NOT NULL,
                                 pu DOUBLE PRECISION NOT NULL,
                                 puold DOUBLE PRECISION NOT NULL,
                                 utilidad DOUBLE PRECISION NOT NULL,
                                 stock DOUBLE PRECISION NOT NULL,
                                 stockold DOUBLE PRECISION NOT NULL,
                                 id_categoria INTEGER NOT NULL,
                                 id_marca INTEGER NOT NULL,
                                 id_unidad INTEGER NOT NULL,
                                 tipo_producto VARCHAR(20) NOT NULL,
                                 CONSTRAINT producto_pk PRIMARY KEY (id_producto)
);


ALTER SEQUENCE public.producto_id_producto_seq OWNED BY public.producto.id_producto;

CREATE SEQUENCE public.venta_detalle_id_venta_detalle_seq;

CREATE TABLE public.venta_detalle (
                                      id_venta_detalle INTEGER NOT NULL DEFAULT nextval('public.venta_detalle_id_venta_detalle_seq'),
                                      pu DOUBLE PRECISION NOT NULL,
                                      cantidad DOUBLE PRECISION NOT NULL,
                                      descuento DOUBLE PRECISION NOT NULL,
                                      subtotal DOUBLE PRECISION NOT NULL,
                                      id_venta INTEGER NOT NULL,
                                      id_producto INTEGER NOT NULL,
                                      CONSTRAINT venta_detalle_pk PRIMARY KEY (id_venta_detalle)
);


ALTER SEQUENCE public.venta_detalle_id_venta_detalle_seq OWNED BY public.venta_detalle.id_venta_detalle;

CREATE SEQUENCE public.compra_detalle_id_compra_detalle_seq;

CREATE TABLE public.compra_detalle (
                                       id_compra_detalle INTEGER NOT NULL DEFAULT nextval('public.compra_detalle_id_compra_detalle_seq'),
                                       pu DOUBLE PRECISION NOT NULL,
                                       cantidad DOUBLE PRECISION NOT NULL,
                                       subtotal DOUBLE PRECISION NOT NULL,
                                       id_compra INTEGER NOT NULL,
                                       id_producto INTEGER NOT NULL,
                                       CONSTRAINT compra_detalle_pk PRIMARY KEY (id_compra_detalle)
);


ALTER SEQUENCE public.compra_detalle_id_compra_detalle_seq OWNED BY public.compra_detalle.id_compra_detalle;

ALTER TABLE public.usuario ADD CONSTRAINT perfil_usuario_fk
    FOREIGN KEY (id_perfil)
        REFERENCES public.perfil (id_perfil)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION
        NOT DEFERRABLE;

ALTER TABLE public.compra ADD CONSTRAINT proveedor_compra_fk
    FOREIGN KEY (id_proveedor)
        REFERENCES public.proveedor (id_proveedor)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION
        NOT DEFERRABLE;

ALTER TABLE public.producto ADD CONSTRAINT unid_medida_producto_fk
    FOREIGN KEY (id_unidad)
        REFERENCES public.unid_medida (id_unidad)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION
        NOT DEFERRABLE;

ALTER TABLE public.compra ADD CONSTRAINT usuario_compra_fk
    FOREIGN KEY (id_usuario)
        REFERENCES public.usuario (id_usuario)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION
        NOT DEFERRABLE;

ALTER TABLE public.venta ADD CONSTRAINT usuario_venta_fk
    FOREIGN KEY (id_usuario)
        REFERENCES public.usuario (id_usuario)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION
        NOT DEFERRABLE;

ALTER TABLE public.vent_carrito ADD CONSTRAINT usuario_carrito_fk
    FOREIGN KEY (id_usuario)
        REFERENCES public.usuario (id_usuario)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION
        NOT DEFERRABLE;

ALTER TABLE public.comp_carrito ADD CONSTRAINT usuario_comp_carrito_fk
    FOREIGN KEY (id_usuario)
        REFERENCES public.usuario (id_usuario)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION
        NOT DEFERRABLE;

ALTER TABLE public.compra_detalle ADD CONSTRAINT compra_compra_detalle_fk
    FOREIGN KEY (id_compra)
        REFERENCES public.compra (id_compra)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION
        NOT DEFERRABLE;

ALTER TABLE public.venta ADD CONSTRAINT cliente_venta_fk
    FOREIGN KEY (dniruc)
        REFERENCES public.cliente (dniruc)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION
        NOT DEFERRABLE;

ALTER TABLE public.venta_detalle ADD CONSTRAINT venta_venta_detalle_fk
    FOREIGN KEY (id_venta)
        REFERENCES public.venta (id_venta)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION
        NOT DEFERRABLE;

ALTER TABLE public.producto ADD CONSTRAINT marca_producto_fk
    FOREIGN KEY (id_marca)
        REFERENCES public.marca (id_marca)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION
        NOT DEFERRABLE;

ALTER TABLE public.producto ADD CONSTRAINT categoria_producto_fk
    FOREIGN KEY (id_categoria)
        REFERENCES public.categoria (id_categoria)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION
        NOT DEFERRABLE;

ALTER TABLE public.compra_detalle ADD CONSTRAINT producto_compra_detalle_fk
    FOREIGN KEY (id_producto)
        REFERENCES public.producto (id_producto)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION
        NOT DEFERRABLE;

ALTER TABLE public.venta_detalle ADD CONSTRAINT producto_venta_detalle_fk
    FOREIGN KEY (id_producto)
        REFERENCES public.producto (id_producto)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION
        NOT DEFERRABLE;


INSERT INTO perfil (id_perfil, nombre, codigo)
VALUES
    (1, 'Root', 'ROOT'),
    (2, 'Administrador', 'ADM'),
    (3, 'Reporte', 'REP')
    ON CONFLICT (id_perfil) DO NOTHING;

INSERT INTO usuario (id_usuario, usuario, clave, estado, id_perfil)
VALUES (1, 'admin', 'admin123', 'ACTIVO', 1)
    ON CONFLICT (id_usuario) DO NOTHING;

INSERT INTO categoria (id_categoria, nombre)
VALUES (1, 'Televisor')
    ON CONFLICT (id_categoria) DO NOTHING;

INSERT INTO marca (id_marca, nombre)
VALUES (1, 'LG')
    ON CONFLICT (id_marca) DO NOTHING;

INSERT INTO unid_medida (id_unidad, nombre_medida)
VALUES (1, 'Unidad')
    ON CONFLICT (id_unidad) DO NOTHING;

-- Los INSERT anteriores usan IDs fijos y no avanzan las secuencias.
-- Se sincroniza cada secuencia con el MAX(id) de su tabla para que el
-- siguiente INSERT sin ID no choque con una clave existente.
SELECT setval('public.perfil_id_perfil_seq', COALESCE(MAX(id_perfil), 1), MAX(id_perfil) IS NOT NULL) FROM public.perfil;
SELECT setval('public.usuario_id_usuario_seq', COALESCE(MAX(id_usuario), 1), MAX(id_usuario) IS NOT NULL) FROM public.usuario;
SELECT setval('public.categoria_id_categoria_seq', COALESCE(MAX(id_categoria), 1), MAX(id_categoria) IS NOT NULL) FROM public.categoria;
SELECT setval('public.marca_id_marca_seq', COALESCE(MAX(id_marca), 1), MAX(id_marca) IS NOT NULL) FROM public.marca;
SELECT setval('public.unid_medida_id_unidad_seq', COALESCE(MAX(id_unidad), 1), MAX(id_unidad) IS NOT NULL) FROM public.unid_medida;
