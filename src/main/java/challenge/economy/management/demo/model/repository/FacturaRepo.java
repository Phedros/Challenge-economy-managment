package challenge.economy.management.demo.model.repository;

import challenge.economy.management.demo.model.entity.Factura;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FacturaRepo extends CrudRepository<Factura, Integer> {
    @Query(value = "SELECT * FROM factura where pagado = 0", nativeQuery = true)
    List<Factura> listarFacturasImpagas();
}
