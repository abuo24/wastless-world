package uz.wastlessworld.app.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CategoryPayload {
    private String nameUz;
    private String nameRu;
    private List<MeasurementPayload> measurements;

    private String imageUrl;

}
