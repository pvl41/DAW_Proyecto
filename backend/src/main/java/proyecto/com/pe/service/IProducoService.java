package proyecto.com.pe.service;


import proyecto.com.pe.entity.Producto;

import java.util.List;
import java.util.Optional;

public interface IProducoService {

    // CRUD Básico
    List<Producto> listarTodos();
    Optional<Producto> obtenerPorId(Integer id);
    Producto guardar(Producto producto);
    Producto actualizar(Integer id, Producto producto);
    void eliminar(Integer id);

    // Búsquedas Personalizadas
    List<Producto> buscarPorCategoria(Integer idCategoria);
    List<Producto> buscarPorProveedor(Integer idProveedor);
    List<Producto> buscarPorNombre(String nombre);
    List<Producto> buscarPorStockBajo(Integer stockMinimo);
    List<Producto> buscarPorStockCero();
}
