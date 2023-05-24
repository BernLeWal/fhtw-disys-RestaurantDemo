package at.fhtw.disys.orderapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Pan {
    private String id;
    private int diameter;
    private boolean teflon;
}
