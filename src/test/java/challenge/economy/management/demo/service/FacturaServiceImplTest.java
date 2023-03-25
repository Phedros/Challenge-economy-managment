package challenge.economy.management.demo.service;

import challenge.economy.management.demo.domain.FormaDePago;
import challenge.economy.management.demo.domain.InformeMensual;
import challenge.economy.management.demo.domain.InformeRequest;
import challenge.economy.management.demo.domain.InformeResponse;
import challenge.economy.management.demo.model.entity.Factura;
import challenge.economy.management.demo.model.repository.FacturaRepo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class FacturaServiceImplTest {

    @Mock
    private FacturaRepo facturaRepo;

    @InjectMocks
    private FacturaServiceImpl facturaService;

    @DisplayName("Informe de un periodo")
    @Test
    void informePeriodo() {

        //Creo el Response con datos que deverian devolver
        List<InformeMensual> listaInformeMensual = new ArrayList<>();
        listaInformeMensual.add(InformeMensual.builder().gastoTotal(978.34).mes(Month.FEBRUARY).build());

        InformeResponse informeResponse = InformeResponse.builder()
                .reporteMensual(listaInformeMensual)
                .gastoMaximo(978.34)
                .gastoMinimo(978.34)
                .gastoTotal(978.34)
                .mesGastoMaximo(Month.FEBRUARY)
                .mesGastoMinimo(Month.FEBRUARY).build();

        //lista de facturas que mockeo (1 sola factura)
        List<Factura> listaDeFacturasMock = new ArrayList<>();
        listaDeFacturasMock.add(Factura.builder()
                .id(52)
                .deuda(978.34)
                .direccion("Pagano 2628 PB C")
                .empresa("Cablevision")
                .fechaEmision(LocalDate.of(2023, 2, 2))
                .fechaPago(LocalDate.of(2023, 2, 5))
                .vencimiento(LocalDate.of(2023, 3, 28))
                .formaDePago(FormaDePago.NS)
                .pagado(true)
                .propietario("Pedro")
                .build());
        when(facturaRepo.findAll()).thenReturn(listaDeFacturasMock);

        //Request con periodo de tiempo
        InformeRequest informeRequest = new InformeRequest(LocalDate.of(2023, 2, 1),
                LocalDate.of(2023, 4, 1));

        assertEquals(informeResponse, facturaService.informePeriodo(informeRequest), "La lista debe ser la misma");
    }
}