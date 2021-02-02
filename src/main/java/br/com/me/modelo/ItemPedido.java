package br.com.me.modelo;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ItemPedido implements Serializable {
    @SequenceGenerator(name = "SECQNAMEINENTITY", sequenceName = "DB_SEC_ITEM_PEDIDO", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SECQNAMEINENTITY")
    @Id
    @Getter
    @Setter
    @ApiModelProperty(hidden = true)
    private Long id;

    @Getter
    @Setter
    @NotNull
    private String descricao;

    @Getter
    @Setter
    @NotNull
    private BigDecimal precoUnitario;

    @Getter
    @Setter
    @NotNull
    private Integer qtd;

    public BigDecimal calculaValorTotalItensPedido(){
        return precoUnitario.multiply(BigDecimal.valueOf(qtd));
    }

}