package br.com.me.modelo;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Entity
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Pedido implements Serializable {
    @SequenceGenerator(name = "SECQNAMEINENTITY", sequenceName = "DB_SEC_PEDIDO", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SECQNAMEINENTITY")
    @Id
    @Getter
    @Setter
    @ApiModelProperty(hidden = true)
    @Column(name = "idPedido")
    private Long id;

    @Getter
    @Setter
    @NotNull
    private String pedido;

    @Getter
    @Setter
    @OneToMany(cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<ItemPedido> itens;

    public void addItens(List<ItemPedido> itemPedidoList){
        this.itens.clear();
        this.itens.addAll(itemPedidoList);
    }


    public BigDecimal calculaValorTotalPedido() {
        return itens.stream()
                .map(ItemPedido::calculaValorTotalItensPedido)
                .reduce(BigDecimal::add)
                .get();
    }

    public Integer calculaQuantidadeTotalItensPedido() {
        return itens.stream()
                .map(ItemPedido::getQtd)
                .reduce(0, (subtotal, element) -> subtotal + element);
    }

}