package challenge.economy.management.demo.service;

import challenge.economy.management.demo.domain.InformeRequest;
import challenge.economy.management.demo.domain.InformeResponse;
import challenge.economy.management.demo.model.entity.Factura;

import java.util.List;

public interface IFacturaService {

    public void save(Factura factura);
    public List<Factura> findAll();
    public Factura findById(Integer id);
    public void delete(Integer id);
    public InformeResponse informePeriodo(InformeRequest solicitudInformePeriodo);
    public List<Factura> listarFacturasImpagas();
//    public List<Factura> listarFacturasPorEmpresa(String empresa);
    public List<Factura> listarPorEmpresa(String empresa);
//    public List<Factura> listarFacturasPorPropietario(String propietario);  listarPorPropietario
    public List<Factura> listarPorPropietario(String propietario);
    public List<Factura> listarFacturasVencidas();


}
