package challenge.economy.management.demo.service;

import challenge.economy.management.demo.domain.InformeMensual;
import challenge.economy.management.demo.domain.InformeRequest;
import challenge.economy.management.demo.domain.InformeResponse;
import challenge.economy.management.demo.model.entity.Factura;
import challenge.economy.management.demo.model.repository.FacturaRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class FacturaServiceImpl implements IFacturaService {

    @Autowired
    private FacturaRepo facturaRepo;

    @Override
    public void save(Factura factura) {
        if (factura.getFechaPago() != null) {
            factura.setPagado(true);
        }
        facturaRepo.save(factura);
    }

    @Override
    public List<Factura> findAll() {
        return (List<Factura>) facturaRepo.findAll();
    }

    @Override
    public Factura findById(Integer id) {
        return (Factura) facturaRepo.findById(id).orElse(null);
    }

    @Override
    public void delete(Integer id) {
        facturaRepo.deleteById(id);
    }

    @Override
    public InformeResponse informePeriodo(InformeRequest solicitudInformePeriodo) {

        List<Factura> facturas = (List<Factura>) facturaRepo.findAll();
        List<Factura> facturasEnRango = seleccionarFacturasEnRango(facturas, solicitudInformePeriodo);

        Map<Object, Double> hashMeses2 = facturasEnRango.stream()
                .collect(Collectors.groupingBy(factura -> factura.getFechaPago().getMonth(),
                        Collectors.summingDouble(Factura::getDeuda)));

        //gasto total
        Double gastoTotalF = facturasEnRango.stream()
                .map(factura -> factura.getDeuda())
                .reduce(0.0, Double::sum);

        //Informe total
        List<InformeMensual> informeTotal = new ArrayList<>();
        hashMeses2.forEach((month, aDouble) -> informeTotal.add(new InformeMensual((Month) month, aDouble)));

        //Gasto maximo
        Double gastoMaximoF = informeTotal.stream()
                .map(informeMensual -> informeMensual.getGastoTotal())
                .reduce(Double.MIN_VALUE, (x, y) -> x > y ? x : y);

        //Gasto minimo
        Double gastoMinimoF = informeTotal.stream()
                .map(informeMensual -> informeMensual.getGastoTotal())
                .reduce(Double.MAX_VALUE, (x, y) -> x < y ? x : y);

        //Mes Gasto Maximo
        Month mesGastoMaximoF = getListaInformesOrdenada(informeTotal).get(getListaInformesOrdenada(informeTotal).size() - 1).getMes();

        //Mes Gasto minimo
        Month mesGastoMinimoF = getListaInformesOrdenada(informeTotal).get(0).getMes();

        InformeResponse informeResponse = new InformeResponse();

        informeResponse.setGastoMinimo(gastoMinimoF);
        informeResponse.setMesGastoMinimo(mesGastoMinimoF);
        informeResponse.setGastoMaximo(gastoMaximoF);
        informeResponse.setMesGastoMaximo(mesGastoMaximoF);
        informeResponse.setGastoTotal(gastoTotalF);
        informeResponse.setReporteMensual(informeTotal);

        return informeResponse;
    }

    //@Scheduled(cron = "*/10 * * * * *")
    @Override
    public List<Factura> listarFacturasImpagas() {
        return facturaRepo.listarFacturasImpagas();
    }


    private static List<Factura> seleccionarFacturasEnRango(List<Factura> facturas, InformeRequest solicitudInformePeriodo) {
        return facturas.stream()
                .filter(factura -> factura.getFechaPago() != null)
                .filter(factura -> factura.getFechaPago().isAfter(solicitudInformePeriodo.getFechaInicio()))
                .filter(factura -> factura.getFechaPago().isBefore(solicitudInformePeriodo.getFechaFinal()))
                .collect(Collectors.toList());
    }

    private static List<InformeMensual> getListaInformesOrdenada(List<InformeMensual> informeTotal) {
        return informeTotal.stream()
                .sorted(Comparator.comparingDouble(InformeMensual::getGastoTotal))
                .collect(Collectors.toList());

    }
}