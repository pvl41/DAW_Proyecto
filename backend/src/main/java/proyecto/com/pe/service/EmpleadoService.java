package proyecto.com.pe.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import proyecto.com.pe.entity.Empleado;
import proyecto.com.pe.repository.EmpleadoRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmpleadoService implements IEmpleadoService{
    private final EmpleadoRepository empleadoRepository;

    @Override
    public List<Empleado> listarTodos() {
        return empleadoRepository.findAll();
    }

    @Override
    public Optional<Empleado> obtenerPorId(Integer id) {
        return empleadoRepository.findById(id);
    }

    @Override
    public Empleado guardar(Empleado empleado) {
        if (empleadoRepository.existsByDni(empleado.getDni())) {
            throw new RuntimeException("El DNI ya está registrado");
        }
        // Aseguramos que activo sea true por defecto si viene nulo
        if (empleado.getActivo() == null) {
            empleado.setActivo(true);
        }
        return empleadoRepository.save(empleado);
    }

    @Override
    public Empleado actualizar(Integer id, Empleado empleado) {
        Empleado empleadoExistente = empleadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado"));

        // Validar si el DNI cambia, que no pertenezca a otro empleado
        if (!empleadoExistente.getDni().equals(empleado.getDni())
                && empleadoRepository.existsByDni(empleado.getDni())) {
            throw new RuntimeException("El DNI ya existe en otro registro");
        }

        empleadoExistente.setNombre(empleado.getNombre());
        empleadoExistente.setApellido(empleado.getApellido());
        empleadoExistente.setDni(empleado.getDni());
        empleadoExistente.setFechaIngreso(empleado.getFechaIngreso());
        empleadoExistente.setPuesto(empleado.getPuesto());
        empleadoExistente.setSalario(empleado.getSalario());
        empleadoExistente.setActivo(empleado.getActivo());

        return empleadoRepository.save(empleadoExistente);
    }

    @Override
    public void eliminar(Integer id) {
        // Opción 1: Eliminación Lógica (Recomendada dado que tienes el campo 'activo')
        Empleado empleado = empleadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado"));
        empleado.setActivo(false);
        empleadoRepository.save(empleado);


    }

    @Override
    public Optional<Empleado> buscarPorDni(String dni) {
        return empleadoRepository.findByDni(dni);
    }

    @Override
    public List<Empleado> buscarPorEstado(Boolean activo) {
        return empleadoRepository.findByActivo(activo);
    }

    @Override
    public List<Empleado> buscarPorPuesto(String puesto) {
        return empleadoRepository.findByPuesto(puesto);
    }

    @Override
    public List<Empleado> buscarPorNombreOApellido(String filtro) {
        return empleadoRepository.findByNombreContainingIgnoreCaseOrApellidoContainingIgnoreCase(filtro, filtro);
    }

    @Override
    public boolean existeDni(String dni) {
        return empleadoRepository.existsByDni(dni);
    }
}
