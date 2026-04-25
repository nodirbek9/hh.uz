package uz.java.dto.tag;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TagResponse {

    Long id;

    String name;

    String code;

}
