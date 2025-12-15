package proyecto.com.pe.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import proyecto.com.pe.entity.Producto;
import proyecto.com.pe.repository.ProductoRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductoService implements IProducoService{

    private final ProductoRepository productoRepository;


    @Override
    @Transactional(readOnly = true)
    public List<Producto> listarTodos() {
        return productoRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Producto> obtenerPorId(Integer id) {
        return productoRepository.findById(id);
    }

    @Override
    @Transactional
    public Producto guardar(Producto producto) {
        if (producto.getStock() == null)
            producto.setStock(0);

        return productoRepository.save(producto);
    }

    @Override
    @Transactional
    public Producto actualizar(Integer id, Producto producto) {
        Producto productoexistente = productoRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Producto no encontrado"));

        productoexistente.setNombre(producto.getNombre());
        productoexistente.setDescripcion(producto.getDescripcion());
        productoexistente.setPrecio(producto.getPrecio());
        productoexistente.setStock(producto.getStock());
        productoexistente.setCategoria(producto.getCategoria());
        productoexistente.setProveedor(producto.getProveedor());

        return productoRepository.save(productoexistente);
    }

    @Override
    @Transactional
    public void eliminar(Integer id) {
        if (!productoRepository.existsById(id)){
            throw  new RuntimeException("Producto no encontrado");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Producto> buscarPorCategoria(Integer idCategoria) {
        return productoRepository.findByCategoriaIdCategoria(idCategoria);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Producto> buscarPorProveedor(Integer idProveedor) {
        return productoRepository.findByProveedorIdProveedor(idProveedor);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Producto> buscarPorNombre(String nombre) {
        return productoRepository.findByNombreContainingIgnoreCase(nombre);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Producto> buscarPorStockBajo(Integer stockMinimo) {
        return productoRepository.findProductosConStockBajo(stockMinimo);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Producto> buscarPorStockCero() {
        return productoRepository.findByStock(0);
    }
}
