package uz.wastlessworld.app.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.wastlessworld.app.entity.helpers.Types;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MeasurementWeightPayload {
    private BigDecimal weight;
    private Types types;
}
