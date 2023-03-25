package challenge.economy.management.demo.domain;

import challenge.economy.management.demo.domain.InformeMensual;
import lombok.*;

import java.time.Month;
import java.util.HashMap;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class InformeResponse {

    private List<InformeMensual> reporteMensual;
    private Double gastoMaximo;
    private Month mesGastoMaximo;
    private Double gastoMinimo;
    private Month mesGastoMinimo;
    private Double gastoTotal;

}
