package br.com.me.repositorio;

import br.com.me.modelo.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    Optional<Pedido> findByPedido(String pedido);
}
