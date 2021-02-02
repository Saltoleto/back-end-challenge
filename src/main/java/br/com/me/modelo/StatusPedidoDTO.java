package br.com.me.modelo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jdk.nashorn.internal.objects.annotations.Property;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StatusPedidoDTO {
    @Getter
    @Setter
    @NotNull
    private String status;
    @Getter
    @Setter
    @NotNull
    private Integer itensAprovados;
    @Getter
    @Setter
    @NotNull
    private BigDecimal valorAprovado;
    @Getter
    @Setter
    @NotNull
    private String pedido;
    @Setter
    @Getter
    @JsonIgnore
    private List<StatusType> statusTypes = new ArrayList<>();

    public void addStatus(StatusType statusType){
        this.statusTypes.add(statusType);
    }

}