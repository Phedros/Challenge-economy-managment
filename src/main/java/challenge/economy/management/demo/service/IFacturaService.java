package challenge.economy.management.demo.service;

import challenge.economy.management.demo.domain.InformeRequest;
import challenge.economy.management.demo.domain.InformeResponse;
import challenge.economy.management.demo.model.entity.Factura;

import java.util.List;
import java.util.Optional;

public interface IFacturaService {

    public Optional<Factura> save(Factura factura);
    public List<Factura> findAll();
    public Factura findById(Integer id);
    public Optional<Factura> delete(Integer id);
    public InformeResponse informePeriodo(InformeRequest solicitudInformePeriodo);
    public List<Factura> listarFacturasImpagas();
    public List<Factura> listarPorEmpresa(String empresa);
    public List<Factura> listarPorPropietario(String propietario);
    public List<Factura> listarFacturasVencidas();

}


//    public List<Factura> listarFacturasPorEmpresa(String empresa);

//    public List<Factura> listarFacturasPorPropietario(String propietario);  listarPorPropietario

