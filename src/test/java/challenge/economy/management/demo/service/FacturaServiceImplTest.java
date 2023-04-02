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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
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
        listaDeFacturasMock.add(Factura.builder()
                .id(52)
                .deuda(978.34)
                .direccion("Pagano 2628 PB C")
                .empresa("Cablevision temprano")
                .fechaEmision(LocalDate.of(2022, 2, 2))
                .fechaPago(LocalDate.of(2022, 2, 5))
                .vencimiento(LocalDate.of(2022, 3, 28))
                .formaDePago(FormaDePago.NS)
                .pagado(true)
                .propietario("Pedro")
                .build());
        listaDeFacturasMock.add(Factura.builder()
                .id(52)
                .deuda(978.34)
                .direccion("Pagano 2628 PB C")
                .empresa("Cablevision tarde")
                .fechaEmision(LocalDate.of(2024, 2, 2))
                .fechaPago(LocalDate.of(2024, 2, 5))
                .vencimiento(LocalDate.of(2024, 3, 28))
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


@DisplayName("Guardar factura")
    @Test
    void save() {

        Factura factura = Factura.builder()
                .fechaPago(LocalDate.of(2023, 04, 8))
                .id(1)
                .pagado(true)
                .propietario("Pedro")
                .direccion("Pagano 2628")
                .deuda(2000.0)
                .empresa("Eternauta")
                .fechaEmision(LocalDate.of(2023, 04, 01))
                .vencimiento(LocalDate.of(2023, 05, 01))
                .formaDePago(FormaDePago.NS)
                .build();

    when(facturaRepo.findById(any())).thenReturn(Optional.ofNullable(factura));

    final Optional<Factura> result = facturaService.save(factura);

    assertEquals(factura, result.get(), "debe traer la misma factura");
    }

    @Test
    void findAll() {
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

        assertEquals(listaDeFacturasMock, facturaService.findAll());
    }

    @Test
    void findById() {

        Factura factura = Factura.builder()
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
                .build();
        when(facturaRepo.findById(any())).thenReturn(Optional.ofNullable(factura));

        assertEquals(factura, facturaService.findById(52));

    }

    @Test
    void delete() {
        Factura factura = Factura.builder()
                .fechaPago(LocalDate.of(2023, 04, 8))
                .id(1)
                .pagado(true)
                .propietario("Pedro")
                .direccion("Pagano 2628")
                .deuda(2000.0)
                .empresa("Eternauta")
                .fechaEmision(LocalDate.of(2023, 04, 01))
                .vencimiento(LocalDate.of(2023, 05, 01))
                .formaDePago(FormaDePago.NS)
                .build();

        when(facturaRepo.findById(any())).thenReturn(Optional.ofNullable(factura));

        final Optional<Factura> result = facturaService.delete(1);

        assertEquals(factura, result.get());
    }

    @Test
    void listarFacturasImpagas() {

        List<Factura> listaDeFacturasMock = new ArrayList<>();
        listaDeFacturasMock.add(Factura.builder()
                .id(52)
                .deuda(978.34)
                .direccion("Pagano 2628 PB C")
                .empresa("Cablevision temprano")
                .fechaEmision(LocalDate.of(2022, 2, 2))
                .vencimiento(LocalDate.of(2022, 3, 28))
                .formaDePago(FormaDePago.NS)
                .pagado(false)
                .propietario("Pedro")
                .build());
        when(facturaRepo.listarFacturasImpagas()).thenReturn(listaDeFacturasMock);

        assertEquals(listaDeFacturasMock, facturaService.listarFacturasImpagas());
    }

    @Test
    void listarPorEmpresa() {

        List<Factura> listaDeFacturasMock = new ArrayList<>();
        listaDeFacturasMock.add(Factura.builder()
                .id(52)
                .deuda(2978.34)
                .direccion("Pagano 2628 PB C")
                .empresa("AySA")
                .fechaEmision(LocalDate.of(2023, 2, 2))
                .fechaPago(LocalDate.of(2023, 2, 5))
                .vencimiento(LocalDate.of(2023, 3, 28))
                .formaDePago(FormaDePago.NS)
                .pagado(true)
                .propietario("Pedro")
                .build());
        listaDeFacturasMock.add(Factura.builder()
                .id(52)
                .deuda(978.34)
                .direccion("Pagano 2628 PB C")
                .empresa("Cablevision")
                .fechaEmision(LocalDate.of(2022, 2, 2))
                .fechaPago(LocalDate.of(2022, 2, 5))
                .vencimiento(LocalDate.of(2022, 3, 28))
                .formaDePago(FormaDePago.NS)
                .pagado(true)
                .propietario("Pedro")
                .build());
        listaDeFacturasMock.add(Factura.builder()
                .id(52)
                .deuda(9788.34)
                .direccion("Pagano 2628 PB C")
                .empresa("AySA")
                .fechaEmision(LocalDate.of(2024, 2, 2))
                .fechaPago(LocalDate.of(2024, 2, 5))
                .vencimiento(LocalDate.of(2024, 3, 28))
                .formaDePago(FormaDePago.NS)
                .pagado(true)
                .propietario("Pedro")
                .build());
        when(facturaRepo.findAll()).thenReturn(listaDeFacturasMock);

        listaDeFacturasMock.remove(1);

        assertEquals(listaDeFacturasMock, facturaService.listarPorEmpresa("AySA"));

    }

    @Test
    void listarPorPropietario() {

        List<Factura> listaDeFacturasMock = new ArrayList<>();
        listaDeFacturasMock.add(Factura.builder()
                .id(52)
                .deuda(2978.34)
                .direccion("Pagano 2628 PB C")
                .empresa("AySA")
                .fechaEmision(LocalDate.of(2023, 2, 2))
                .fechaPago(LocalDate.of(2023, 2, 5))
                .vencimiento(LocalDate.of(2023, 3, 28))
                .formaDePago(FormaDePago.NS)
                .pagado(true)
                .propietario("Pedro")
                .build());
        listaDeFacturasMock.add(Factura.builder()
                .id(52)
                .deuda(978.34)
                .direccion("Pagano 2628 PB C")
                .empresa("Cablevision")
                .fechaEmision(LocalDate.of(2022, 2, 2))
                .fechaPago(LocalDate.of(2022, 2, 5))
                .vencimiento(LocalDate.of(2022, 3, 28))
                .formaDePago(FormaDePago.NS)
                .pagado(true)
                .propietario("Kathy")
                .build());
        listaDeFacturasMock.add(Factura.builder()
                .id(52)
                .deuda(9788.34)
                .direccion("Pagano 2628 PB C")
                .empresa("AySA")
                .fechaEmision(LocalDate.of(2024, 2, 2))
                .fechaPago(LocalDate.of(2024, 2, 5))
                .vencimiento(LocalDate.of(2024, 3, 28))
                .formaDePago(FormaDePago.NS)
                .pagado(true)
                .propietario("Pedro")
                .build());
        when(facturaRepo.findAll()).thenReturn(listaDeFacturasMock);

        listaDeFacturasMock.remove(1);

        assertEquals(listaDeFacturasMock, facturaService.listarPorPropietario("Pedro"));

    }

    @Test
    void listarFacturasVencidas() {

        List<Factura> listaDeFacturasMock = new ArrayList<>();
        listaDeFacturasMock.add(Factura.builder()
                .id(52)
                .deuda(2978.34)
                .direccion("Pagano 2628 PB C")
                .empresa("AySA")
                .fechaEmision(LocalDate.of(2023, 2, 2))
                .vencimiento(LocalDate.of(2023, 4, 28))
                .formaDePago(FormaDePago.NS)
                .pagado(false)
                .propietario("Pedro")
                .build());
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
                .propietario("Kathy")
                .build());
        listaDeFacturasMock.add(Factura.builder()
                .id(52)
                .deuda(9788.34)
                .direccion("Pagano 2628 PB C")
                .empresa("AySA")
                .fechaEmision(LocalDate.of(2024, 2, 2))
                .vencimiento(LocalDate.of(2024, 3, 28))
                .formaDePago(FormaDePago.NS)
                .pagado(false)
                .propietario("Pedro")
                .build());
        when(facturaRepo.findAll()).thenReturn(listaDeFacturasMock);
        //when(LocalDate.now()).thenReturn(LocalDate.of(2023, 4, 8));

        listaDeFacturasMock.remove(1);

        assertEquals(listaDeFacturasMock, facturaService.listarFacturasVencidas());

    }
}