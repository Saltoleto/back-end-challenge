package br.com.me.modelo;

import lombok.*;

import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor
public class StatusResposta {
    @Getter
    @Setter
    private String pedido;
    @Getter
    @Setter
    private List<StatusType> status = new ArrayList<>();

    public StatusResposta(String pedido, StatusType statusType) {
        this.pedido = pedido;
        this.status.add(statusType);
    }

    public StatusResposta(String pedido, List<StatusType> statusType) {
        this.pedido = pedido;
        this.status.addAll(statusType);
    }
}