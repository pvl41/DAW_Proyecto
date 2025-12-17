package proyecto.com.pe.service;

import proyecto.com.pe.entity.Proveedor;

import java.util.List;

public interface ProveedorService {

    Proveedor guardar(Proveedor proveedor);

    Proveedor actualizar(Integer id, Proveedor proveedor);

    void eliminar(Integer id);

    Proveedor buscarPorId(Integer id);

    List<Proveedor> listarTodos();
}
