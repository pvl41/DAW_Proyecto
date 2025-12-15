package proyecto.com.pe.controller;

import java.util.List;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import proyecto.com.pe.entity.Categoria;
import proyecto.com.pe.service.CategoriaService;

@RestController
@RequestMapping("/api/categorias")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class CategoriaController {

    private final CategoriaService categoriaService;

    // GET: /api/categorias
    @GetMapping
    public ResponseEntity<List<Categoria>> listarTodos() {
        List<Categoria> categorias = categoriaService.listarTodos();
        return categorias.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(categorias);
    }

    // GET: /api/categorias/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Categoria> obtenerPorId(@PathVariable Integer id) {
        return categoriaService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST: /api/categorias
    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody Categoria categoria) {
        try {
            Categoria nuevaCategoria = categoriaService.guardar(categoria);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevaCategoria);
        } catch (DataIntegrityViolationException e) {
            // Maneja la violación de la restricción UNIQUE (nombre)
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error 409: El nombre de la categoría ya existe.");
        } catch (RuntimeException e) {
            // Maneja otras excepciones, como campos nulos obligatorios.
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // PUT: /api/categorias/{id}
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Integer id, @RequestBody Categoria categoria) {
        try {
            Categoria categoriaActualizada = categoriaService.actualizar(id, categoria);
            return ResponseEntity.ok(categoriaActualizada);
        } catch (DataIntegrityViolationException e) {
            // Maneja la violación de la restricción UNIQUE al intentar duplicar un nombre
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error 409: El nuevo nombre de categoría ya está en uso.");
        } catch (RuntimeException e) {
            // Captura la excepción de categoría no encontrada
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // DELETE: /api/categorias/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        try {
            categoriaService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (DataIntegrityViolationException e) {
            // **IMPORTANTE:** Si la categoría tiene productos asociados (Foreign Key), la BD lanzará esto.
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: La categoría no puede eliminarse porque tiene productos asociados.");
        } catch (RuntimeException e) {
            // Maneja error si la categoría no existe
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // --- Endpoints de Búsqueda ---

    // GET: /api/categorias/buscar?nombre=ejemplo
    @GetMapping("/buscar")
    public ResponseEntity<Categoria> buscarPorNombre(@RequestParam String nombre) {
        return categoriaService.buscarPorNombre(nombre)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
