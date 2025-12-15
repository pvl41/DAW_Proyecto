package proyecto.com.pe.service;

import proyecto.com.pe.entity.Empleado;

import java.util.List;
import java.util.Optional;

public interface IEmpleadoService {


    List<Empleado> listarTodos();

    Optional<Empleado> obtenerPorId(Integer id);

    Empleado guardar(Empleado empleado);

    Empleado actualizar(Integer id, Empleado empleado);

    void eliminar(Integer id); // Puede ser físico o lógico


    // Métodos personalizados
    Optional<Empleado> buscarPorDni(String dni);

    List<Empleado> buscarPorEstado(Boolean activo);

    List<Empleado> buscarPorPuesto(String puesto);

    List<Empleado> buscarPorNombreOApellido(String filtro);

    boolean existeDni(String dni);
}
