package proyecto.com.pe.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import proyecto.com.pe.entity.Empleado;
import proyecto.com.pe.service.EmpleadoService;

import java.util.List;

@RestController
@RequestMapping("/api/empleados")
@CrossOrigin(origins = "*") // Permite peticiones desde Angular/React local
@RequiredArgsConstructor
public class EmpleadoController {
    private final EmpleadoService empleadoService;

    // Listar todos
    @GetMapping
    public ResponseEntity<List<Empleado>> listarTodos() {
        return ResponseEntity.ok(empleadoService.listarTodos());
    }

    // Obtener por ID
    @GetMapping("/{id}")
    public ResponseEntity<Empleado> obtenerPorId(@PathVariable Integer id) {
        return empleadoService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Crear empleado
    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody Empleado empleado) {
        try {
            Empleado nuevoEmpleado = empleadoService.guardar(empleado);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoEmpleado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Actualizar empleado
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Integer id, @RequestBody Empleado empleado) {
        try {
            Empleado empleadoActualizado = empleadoService.actualizar(id, empleado);
            return ResponseEntity.ok(empleadoActualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Eliminar (Lógico)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        try {
            empleadoService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // --- Endpoints de Búsqueda ---

    @GetMapping("/buscar/dni/{dni}")
    public ResponseEntity<Empleado> buscarPorDni(@PathVariable String dni) {
        return empleadoService.buscarPorDni(dni)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/buscar/activos")
    public ResponseEntity<List<Empleado>> buscarActivos() {
        return ResponseEntity.ok(empleadoService.buscarPorEstado(true));
    }

    @GetMapping("/buscar/puesto/{puesto}")
    public ResponseEntity<List<Empleado>> buscarPorPuesto(@PathVariable String puesto) {
        List<Empleado> empleados = empleadoService.buscarPorPuesto(puesto);
        return empleados.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(empleados);
    }

    @GetMapping("/buscar/general")
    public ResponseEntity<List<Empleado>> buscarPorNombreOApellido(@RequestParam String filtro) {
        // Uso: /api/empleados/buscar/general?filtro=Juan
        List<Empleado> empleados = empleadoService.buscarPorNombreOApellido(filtro);
        return empleados.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(empleados);
    }
}
