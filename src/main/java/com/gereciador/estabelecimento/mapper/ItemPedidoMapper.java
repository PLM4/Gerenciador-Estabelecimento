package com.gereciador.estabelecimento.mapper;

import com.gereciador.estabelecimento.controllers.dto.request.ItemPedidoRequestDTO;
import com.gereciador.estabelecimento.controllers.dto.response.ItemPedidoResponseDTO;
import com.gereciador.estabelecimento.entities.ItemPedido;
import com.gereciador.estabelecimento.entities.Produto;
import com.gereciador.estabelecimento.exceptions.PedidoNotFoundException;
import com.gereciador.estabelecimento.repositories.ProdutoRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ItemPedidoMapper implements Mapper<List<ItemPedidoResponseDTO>, List<ItemPedidoRequestDTO>, List<ItemPedido>> {

    private final ProdutoRepository produtoRepository;

    public ItemPedidoMapper(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    @Override
    public List<ItemPedido> toEntity(List<ItemPedidoRequestDTO> dtoRequest) throws PedidoNotFoundException {
        List<Long> idsProdutos = dtoRequest.stream()
                .map(ItemPedidoRequestDTO::produtoId)
                .toList();

        List<Produto> produtos = this.produtoRepository.findAllById(idsProdutos);

        if (produtos.size() != idsProdutos.size()) {
            throw new PedidoNotFoundException("Um ou mais produtos informados não existem");
        }

        dtoRequest.forEach(dto -> {
            if (dto.quantidade() == null || dto.quantidade() <= 0) {
                throw new IllegalArgumentException(
                        "Quantidade inválida para o produto ID " + dto.produtoId()
                );
            }
        });

        var dtoMap = dtoRequest.stream()
                .collect(Collectors.toMap(
                        ItemPedidoRequestDTO::produtoId,
                        dto -> dto
                ));

        return produtos.stream()
                .map(produto -> {
                    ItemPedidoRequestDTO dto = dtoMap.get(produto.getId());
                    ItemPedido item = new ItemPedido();
                    item.setProduto(produto);
                    item.setQuantidade(dto.quantidade());
                    return item;
                })
                .toList();
    }

    @Override
    public List<ItemPedidoResponseDTO> toDTO(List<ItemPedido> entities) {
        return entities.stream()
                .map(itemPedido -> new ItemPedidoResponseDTO(
                        itemPedido.getId(),
                        itemPedido.getProduto().getNome(),
                        itemPedido.getQuantidade()))
                .toList();
    }
}