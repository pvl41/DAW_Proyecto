package proyecto.com.pe.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import proyecto.com.pe.entity.Producto;
import proyecto.com.pe.service.ProductoService;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
@CrossOrigin(origins = "*") // Permite peticiones desde Angular/React local
@RequiredArgsConstructor
public class ProductoController {
    private final ProductoService productoService;

    // GET: /api/productos
    @GetMapping
    public ResponseEntity<List<Producto>> listarTodos() {
        List<Producto> productos = productoService.listarTodos();
        return productos.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(productos);
    }

    // GET: /api/productos/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerPorId(@PathVariable Integer id) {
        return productoService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST: /api/productos
    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody Producto producto) {
        try {
            Producto nuevoProducto = productoService.guardar(producto);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoProducto);
        } catch (DataIntegrityViolationException e) {
            // Maneja la violación de la restricción UNIQUE de la BD (Nombre + Proveedor)
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error 409: Ya existe un producto con el mismo nombre asociado a este proveedor.");
        } catch (RuntimeException e) {
            // Maneja producto no encontrado, ID de categoría/proveedor inexistente, etc.
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // PUT: /api/productos/{id}
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Integer id, @RequestBody Producto producto) {
        try {
            Producto productoActualizado = productoService.actualizar(id, producto);
            return ResponseEntity.ok(productoActualizado);
        } catch (DataIntegrityViolationException e) {
            // Maneja la violación de la restricción UNIQUE al intentar duplicar (Nombre + Proveedor) con otro ID
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error 409: La actualización viola la unicidad (nombre + proveedor) de otro producto existente.");
        } catch (RuntimeException e) {
            // Maneja la excepción de producto no encontrado o errores de lógica
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // DELETE: /api/productos/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        try {
            productoService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // --- Endpoints de Búsqueda ---

    // GET: /api/productos/categoria/{idCategoria}
    @GetMapping("/categoria/{idCategoria}")
    public ResponseEntity<List<Producto>> buscarPorCategoria(@PathVariable Integer idCategoria) {
        List<Producto> productos = productoService.buscarPorCategoria(idCategoria);
        return productos.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(productos);
    }

    // GET: /api/productos/proveedor/{idProveedor}
    @GetMapping("/proveedor/{idProveedor}")
    public ResponseEntity<List<Producto>> buscarPorProveedor(@PathVariable Integer idProveedor) {
        List<Producto> productos = productoService.buscarPorProveedor(idProveedor);
        return productos.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(productos);
    }

    // GET: /api/productos/buscar?nombre=ejemplo
    @GetMapping("/buscar")
    public ResponseEntity<List<Producto>> buscarPorNombre(@RequestParam String nombre) {
        List<Producto> productos = productoService.buscarPorNombre(nombre);
        return productos.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(productos);
    }

    // GET: /api/productos/stock-bajo?minimo=10
    @GetMapping("/stock-bajo")
    public ResponseEntity<List<Producto>> buscarStockBajo(@RequestParam Integer minimo) {
        List<Producto> productos = productoService.buscarPorStockBajo(minimo);
        return productos.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(productos);
    }

    // GET: /api/productos/stock-cero
    @GetMapping("/stock-cero")
    public ResponseEntity<List<Producto>> buscarStockCero() {
        List<Producto> productos = productoService.buscarPorStockCero();
        return productos.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(productos);
    }
}
