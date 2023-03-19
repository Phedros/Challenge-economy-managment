package challenge.economy.management.demo.model.entity;

import challenge.economy.management.demo.domain.FormaDePago;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="factura")
public class Factura {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(name="empresa", nullable = false)
    private String empresa;

    @Column(name = "propietario", nullable = false)
    private String propietario;

    @Column(name = "direccion", nullable = false)
    private String direccion;

    @Column(name = "deuda", nullable = false)
    private Double deuda;

    @Column(name = "vencimiento", nullable = false)
    private LocalDate vencimiento;

    @Column(name = "fecha_pago")
    private LocalDate fechaPago;

    @Column(name = "fecha_emision", nullable = false)
    private LocalDate fechaEmision;

    @Column(name = "forma_de_pago")
    private FormaDePago formaDePago;

    private Boolean pagado;



}
