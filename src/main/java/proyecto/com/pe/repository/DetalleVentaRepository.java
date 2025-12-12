package proyecto.com.pe.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import proyecto.com.pe.entity.DetalleVenta;

public interface DetalleVentaRepository extends JpaRepository<DetalleVenta, Integer> {
    List<DetalleVenta> findByVentaIdVenta(Integer idVenta);
}