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

        List<InformeMensual> reporteMensual;
        Double gastoMaximo = 0.0;
        Month mesGastoMaximo = null;
        Double gastoMinimo = 99999999.0;
        Month mesGastoMinimo = null;
        Double gastoTotal = 0.0;
        InformeResponse informeResponse = new InformeResponse();
        Map<Month, Double> hashMeses = new HashMap<>();

        //Functional

        List<Factura> facturas = (List<Factura>) facturaRepo.findAll();

        List<Factura> facturasEnRango = seleccionarFacturasEnRango(facturas, solicitudInformePeriodo);

        Map<Object, Double> hashMeses2 = facturasEnRango.stream()
                .collect(Collectors.groupingBy(factura -> factura.getFechaPago().getMonth(),
                        Collectors.summingDouble(Factura::getDeuda)));
        //gasto total func
        Double gastoTotalF = facturasEnRango.stream()
                .map(factura -> factura.getDeuda())
                .reduce(0.0, Double::sum);

        //Informe total func
        List<InformeMensual> informeTotal = new ArrayList<>();
        hashMeses2.forEach((month, aDouble) -> informeTotal.add(new InformeMensual((Month) month, aDouble)));

        //Gasto maximo
        Double gastoMaximoF = informeTotal.stream()
                .map(informeMensual -> informeMensual.getGastoTotal())
                .reduce(Double.MIN_VALUE, (x, y) -> x > y ? x : y);
        //Mes Gasto Maximo
        Month mesGastoMaximoF = getListaInformesOrdenada(informeTotal).get(getListaInformesOrdenada(informeTotal).size() - 1).getMes();

        //Mes gasto minimo
        Month mesGastoMinimoF = getListaInformesOrdenada(informeTotal).get(0).getMes();

        //Gasto minimo
        Double gastoMinimoF = informeTotal.stream()
                .map(informeMensual -> informeMensual.getGastoTotal())
                .reduce(Double.MAX_VALUE, (x, y) -> x < y ? x : y);

        for (Factura factura : facturaRepo.findAll()) {

            if ((factura.getFechaPago() != null) && factura.getFechaPago().isAfter(solicitudInformePeriodo.getFechaInicio()) &&
                    factura.getFechaPago().isBefore(solicitudInformePeriodo.getFechaFinal())) {
                List<Factura> facturaPedro = new ArrayList<>();
                //HashMap de los meses
                Double valorDeFactura;
                if (hashMeses.containsKey(factura.getFechaPago().getMonth())) {
                    valorDeFactura = hashMeses.get(factura.getFechaPago().getMonth()) + factura.getDeuda();
                    hashMeses.put(factura.getFechaPago().getMonth(), valorDeFactura);
                } else hashMeses.put(factura.getFechaPago().getMonth(), factura.getDeuda());

                //Gasto total
                gastoTotal += factura.getDeuda();
            }
        }

        //Informe mensual
//        List<InformeMensual> informeTotal = new ArrayList<>(hashMeses.size());
//        hashMeses.forEach((month, aDouble) -> informeTotal.add(new InformeMensual(month, aDouble)));

        //mes gasto maximo
        Double variable = 0.0;
        for (InformeMensual inf : informeTotal) {
            if (inf.getGastoTotal() > variable) {
                variable = inf.getGastoTotal();
                mesGastoMaximo = inf.getMes();
            }
        }

        //mes gasto minimo
        Double variable2 = 999999.0;
        for (InformeMensual inf : informeTotal) {
            if (inf.getGastoTotal() < variable2) {
                variable2 = inf.getGastoTotal();
                mesGastoMinimo = inf.getMes();
            }
        }

        //gasto maximo
        gastoMaximo = hashMeses.get(mesGastoMaximo);

        //gasto minimo
        gastoMinimo = hashMeses.get(mesGastoMinimo);


        informeResponse.setGastoMinimo(gastoMinimoF);
        informeResponse.setMesGastoMinimo(mesGastoMinimoF);
        informeResponse.setGastoMaximo(gastoMaximoF);
        informeResponse.setMesGastoMaximo(mesGastoMaximoF);
        informeResponse.setGastoTotal(gastoTotalF);
        informeResponse.setReporteMensual(informeTotal);

        //event

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