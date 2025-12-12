package proyecto.com.pe.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import proyecto.com.pe.entity.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {

    // Buscar por nombre
    Optional<Categoria> findByNombre(String nombre);

    // Verificar si existe el nombre
    boolean existsByNombre(String nombre);
}