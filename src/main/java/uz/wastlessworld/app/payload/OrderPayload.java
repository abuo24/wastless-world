package uz.wastlessworld.app.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderPayload {
    private UUID userId;
    private BasketPayload basketPayload;
    private CoordinatesPayload coordinatesPayload;
}
