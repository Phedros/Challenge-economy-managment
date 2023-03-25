package challenge.economy.management.demo.service;

import challenge.economy.management.demo.domain.InformeMensual;
import challenge.economy.management.demo.domain.InformeRequest;
import challenge.economy.management.demo.domain.InformeResponse;
import challenge.economy.management.demo.model.entity.Factura;
import challenge.economy.management.demo.model.repository.FacturaRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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



        for (Factura factura : facturaRepo.findAll()){

            if ((factura.getFechaPago() != null) && factura.getFechaPago().isAfter(solicitudInformePeriodo.getFechaInicio()) &&
                    factura.getFechaPago().isBefore(solicitudInformePeriodo.getFechaFinal())){

                //HashMap de los meses
                Double valorDeFactura;
                if (hashMeses.containsKey(factura.getFechaPago().getMonth())){
                    valorDeFactura = hashMeses.get(factura.getFechaPago().getMonth()) + factura.getDeuda();
                    hashMeses.put(factura.getFechaPago().getMonth(), valorDeFactura);
                } else hashMeses.put(factura.getFechaPago().getMonth(), factura.getDeuda());

                //Gasto Maximo
/*
                if (factura.getDeuda() > gastoMaximo){
                    gastoMaximo = factura.getDeuda();
                    mesGastoMaximo = factura.getFechaPago().getMonth();
                }

                //Gasto Minimo
                if (factura.getDeuda() < gastoMinimo){
                    gastoMinimo = factura.getDeuda();
                    mesGastoMinimo = factura.getFechaPago().getMonth();
                }
*/
                //Gasto total
                gastoTotal += factura.getDeuda();



            }
        }





        //Mes gasto maximo
        List<InformeMensual> informe = new ArrayList<>(hashMeses.size());
        hashMeses.forEach((month, aDouble) -> informe.add(new InformeMensual(month, aDouble)));

        //mes gasto maximo
        Double variable = 0.0;
        for (InformeMensual inf : informe){
            if (inf.getGastoTotal()>variable){
                variable = inf.getGastoTotal();
                mesGastoMaximo = inf.getMes();
            }
        }

        //mes gasto minimo
        Double variable2 = 999999.0;
        for (InformeMensual inf : informe){
            if (inf.getGastoTotal()<variable2){
                variable2 = inf.getGastoTotal();
                mesGastoMinimo = inf.getMes();
            }
        }

        //gasto maximo
        gastoMaximo = hashMeses.get(mesGastoMaximo);

        //gasto minimo
        gastoMinimo = hashMeses.get(mesGastoMinimo);


        informeResponse.setGastoMinimo(gastoMinimo);
        informeResponse.setMesGastoMinimo(mesGastoMinimo);
        informeResponse.setGastoMaximo(gastoMaximo);
        informeResponse.setMesGastoMaximo(mesGastoMaximo);
        informeResponse.setGastoTotal(gastoTotal);
        informeResponse.setReporteMensual(informe);

        //event

        return informeResponse;
    }

    //@Scheduled(cron = "*/10 * * * * *")
    @Override
    public List<Factura> listarFacturasImpagas() {
        return facturaRepo.listarFacturasImpagas();
    }




}
