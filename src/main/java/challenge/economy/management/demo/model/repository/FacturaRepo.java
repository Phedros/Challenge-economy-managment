package challenge.economy.management.demo.model.repository;

import challenge.economy.management.demo.model.entity.Factura;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FacturaRepo extends CrudRepository<Factura, Integer> {
    @Query(value = "SELECT * FROM factura where pagado = 0", nativeQuery = true)
    List<Factura> listarFacturasImpagas();

//    @Query(value = "SELECT * FROM factura u where u.empresa= :empresa", nativeQuery = true)
//    List<Factura> listarFacturasPorEmpresa(String empresa);

//    @Query(value = "SELECT * FROM factura u where u.propietario= :propietario", nativeQuery = true)
//    List<Factura> listarFacturasPorPropietario(String propietario);
}
