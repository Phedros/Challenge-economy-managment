package challenge.economy.management.demo.domain;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InformeRequest {

    private LocalDate fechaInicio;
    private LocalDate fechaFinal;

}
