package proyecto.com.pe.service;

import proyecto.com.pe.entity.Categoria;

import java.util.List;
import java.util.Optional;

public interface ICategoriaService {

    // CRUD Básico
    List<Categoria> listarTodos();
    Optional<Categoria> obtenerPorId(Integer id);
    Categoria guardar(Categoria categoria);
    Categoria actualizar(Integer id, Categoria categoria);
    void eliminar(Integer id);

    // Búsquedas Personalizadas
    Optional<Categoria> buscarPorNombre(String nombre);
    boolean existeNombre(String nombre);
}
