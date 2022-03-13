package uz.wastlessworld.app.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BasketPayload {
    private UUID categoryId;
    private List<MeasurementWeightPayload> measurements;
    private BigDecimal totalPrice;
}
