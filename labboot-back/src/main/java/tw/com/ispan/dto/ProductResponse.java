package tw.com.ispan.dto;

import java.util.List;

public record ProductResponse(
    Boolean success,
    String message,
    Long count,
    List<?> list) {
    
}
