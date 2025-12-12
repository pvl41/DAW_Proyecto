package proyecto.com.pe.repository;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import proyecto.com.pe.entity.Venta;

public interface VentaRepository extends JpaRepository<Venta, Integer> {
    List<Venta> findByClienteIdCliente(Integer idCliente);
    List<Venta> findByEmpleadoIdEmpleado(Integer idEmpleado);
    List<Venta> findByFechaBetween(LocalDateTime inicio, LocalDateTime fin);
}