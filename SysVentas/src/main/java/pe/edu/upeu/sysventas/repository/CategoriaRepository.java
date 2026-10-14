package pe.edu.upeu.sysventas.repository;

import pe.edu.upeu.sysventas.model.Categoria;
import pe.edu.upeu.sysventas.model.Marca;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoriaRepository extends AbstractJpaRepository<Categoria, Long> {


    @Override
    protected String getTableName() { return "categoria";}

    @Override
    protected String getPkColumn() {return "id_categoria";}

    @Override
    protected Categoria insert(Connection connection, Categoria entity) throws SQLException {
       long id=executeInsertGetKey(connection,
               " insert into  categoria(nombre) values(?)", entity.getNombre());
       entity.setIdCategoria(id);
       return entity;
    }

    @Override
    protected Categoria updateRow(Connection connection, Categoria entity) throws SQLException {
        executeUpdate(connection,
                "update categoria set nombre=? where id_categoria=?",
                entity.getNombre(), entity.getIdCategoria()
                );

        return entity;
    }

    @Override
    protected Categoria mapRow(ResultSet rs) throws SQLException {

        return Categoria.builder()
                .idCategoria(rs.getLong("id_categoria"))
                .nombre(rs.getString("nombre"))

                .build();
    }
}
