package proyecto.com.pe.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import proyecto.com.pe.entity.Empleado;

public interface EmpleadoRepository extends JpaRepository<Empleado, Integer> {


    // Buscar por DNI
    Optional<Empleado> findByDni(String dni);

    // Buscar empleados activos
    List<Empleado> findByActivo(Boolean activo);

    // Buscar por puesto
    List<Empleado> findByPuesto(String puesto);

    // Buscar por nombre o apellido (búsqueda parcial)
    List<Empleado> findByNombreContainingIgnoreCaseOrApellidoContainingIgnoreCase(String nombre, String apellido);

    // Verificar si existe DNI (para validación)
    boolean existsByDni(String dni);
}