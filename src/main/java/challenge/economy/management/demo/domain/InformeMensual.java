package challenge.economy.management.demo.domain;

import lombok.*;

import java.time.Month;
import java.util.HashMap;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class InformeMensual {

    private Month mes;
    private Double gastoTotal;
}
