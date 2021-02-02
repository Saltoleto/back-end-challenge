package br.com.me.controle;


import br.com.me.modelo.Pedido;
import br.com.me.repositorio.PedidoRepository;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/pedido")
@Validated
public class PedidoControle {

    PedidoRepository pedidoRepository;

    public PedidoControle(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    @ApiOperation(value = "Endpoint para listar todos os pedidos cadastrados")
    @GetMapping
    public ResponseEntity<List<Pedido>> listaTodos() {
        return ResponseEntity.ok(pedidoRepository.findAll());
    }

    @ApiOperation(value = "Endpoint para buscar um pedido por id")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Pedido Atualizado com sucesso"),
            @ApiResponse(code = 404, message = "Not found: Pedido nao encontrado")})
    @GetMapping(path = "/{id}")
    public ResponseEntity<?> buscaPorId(@PathVariable(value = "id") Long idPedido) {
        Optional<Pedido> pedido = pedidoRepository.findById(idPedido);
        return pedido.isPresent() ? ResponseEntity.ok(pedido) : ResponseEntity.notFound().build();
    }

    @ApiOperation(value = "Endpoint para cadastrar pedidos")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Pedido Cadastrado com sucesso"),
            @ApiResponse(code = 400, message = "Bad Request: Requisição possui campos inválidos")})
    @PostMapping
    public ResponseEntity<?> salva(@Valid @RequestBody Pedido pedido) {
        return new ResponseEntity<>(pedidoRepository.save(pedido), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Endpoint para editar pedidos")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Pedido Atualizado com sucesso"),
            @ApiResponse(code = 204, message = "Sem conteudo: Pedido nao encontrado")})
    @PutMapping("/{id}")
    public ResponseEntity<?> atualiza(@PathVariable(value = "id") Long idPedido, @Valid @RequestBody Pedido
            pedidoAtualizar) {
        Optional<Pedido> pedido = pedidoRepository.findById(idPedido);
        if (!pedido.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        pedido.get().setPedido(pedidoAtualizar.getPedido());
        pedido.get().addItens(pedidoAtualizar.getItens());
        return ResponseEntity.ok(pedidoRepository.save(pedido.get()));
    }

    @ApiOperation(value = "Endpoint para deletar pedidos")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Pedido deletado com sucesso"),
            @ApiResponse(code = 404, message = "Não encontrado: Pedido nao encontrado")})
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleta(@PathVariable Long id) {
        Optional<Pedido> pedido = pedidoRepository.findById(id);
        if (!pedido.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        pedidoRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }


}