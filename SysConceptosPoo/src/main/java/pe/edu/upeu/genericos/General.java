package pe.edu.upeu.genericos;

public class General {
    public static void main(String[] args) {
        Repositorio<Producto> repo=new ProductoRepo();
        repo.guardar(new Producto("12345678", "Laptop 1", 100000.00));
        repo.guardar(new Producto("12345678", "Laptop 2", 200000.00));
        repo.guardar(new Producto("12345678", "Laptop 3", 300000.00));

        for (Producto p: repo.listarTodos()){
            System.out.println(p.nombre+" "+p.precio);
        }

     Repositorio<Cliente>  cli =new ClienteRepo();
        cli.guardar(new Cliente("David", "43631917", "Perez", "951782520"));
        cli.guardar(new Cliente("Elias", "43631918", "Perez", "951782521"));

        for (Cliente c: cli.listarTodos()){
            System.out.println(c.nombre+" "+c.telefono);
        }
    }
}
