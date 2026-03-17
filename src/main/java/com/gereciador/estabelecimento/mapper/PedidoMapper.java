package com.gereciador.estabelecimento.mapper;

import com.gereciador.estabelecimento.controllers.dto.request.PedidoRequestDTO;
import com.gereciador.estabelecimento.controllers.dto.response.PedidoResponseDTO;
import com.gereciador.estabelecimento.entities.ItemPedido;
import com.gereciador.estabelecimento.entities.Pedido;
import com.gereciador.estabelecimento.exceptions.PedidoNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PedidoMapper implements Mapper<PedidoResponseDTO, PedidoRequestDTO, Pedido> {

    private final ItemPedidoMapper itemPedidoMapper;

    public PedidoMapper(ItemPedidoMapper itemPedidoMapper) {
        this.itemPedidoMapper = itemPedidoMapper;
    }

    @Override
    public Pedido toEntity(PedidoRequestDTO dtoRequest) throws PedidoNotFoundException {
        List<ItemPedido> itensPedido = this.itemPedidoMapper.toEntity(dtoRequest.itensPedido());

        Pedido pedido = new Pedido();
        itensPedido.forEach(itemPedido -> itemPedido.setPedido(pedido));
        pedido.setItensPedido(itensPedido);
        return pedido;
    }

    @Override
    public PedidoResponseDTO toDTO(Pedido entity) {
        return new PedidoResponseDTO(
                entity.getId(),
                this.itemPedidoMapper.toDTO(entity.getItensPedido()),
                entity.getData(),
                entity.getStatusPedido());
    }
}