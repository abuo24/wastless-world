package uz.wastlessworld.app.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CoordinatesPayload {
    private double latitude;
    private double longitude;
    private String address;
    private String comment;
}
