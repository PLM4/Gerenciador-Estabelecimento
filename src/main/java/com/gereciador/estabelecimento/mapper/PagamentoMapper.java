package com.gereciador.estabelecimento.mapper;

import com.gereciador.estabelecimento.exceptions.PedidoNotFoundException;
import org.springframework.stereotype.Component;

import com.gereciador.estabelecimento.controllers.dto.request.PagamentoRequestDTO;
import com.gereciador.estabelecimento.controllers.dto.response.ClienteResponseDTO;
import com.gereciador.estabelecimento.controllers.dto.response.PagamentoResponseDTO;
import com.gereciador.estabelecimento.entities.Pagamento;
import com.gereciador.estabelecimento.repositories.ClienteRepository;
import com.gereciador.estabelecimento.repositories.PedidoRepository;

@Component
public class PagamentoMapper implements Mapper<PagamentoResponseDTO, PagamentoRequestDTO, Pagamento> {

    private final ClienteRepository clienteRepository;
    private final PedidoRepository pedidoRepository;
    private final ClienteMapper clienteMapper;
    private final PedidoMapper pedidoMapper;

    public PagamentoMapper(ClienteRepository clienteRepository, PedidoRepository pedidoRepository,
            ClienteMapper clienteMapper, PedidoMapper pedidoMapper) {
        this.clienteRepository = clienteRepository;
        this.pedidoRepository = pedidoRepository;
        this.clienteMapper = clienteMapper;
        this.pedidoMapper = pedidoMapper;
    }

    @Override
    public Pagamento toEntity(PagamentoRequestDTO dtoRequest) {
        Pagamento pagamento = new Pagamento();
        pagamento.setPedido(
                this.pedidoRepository.findById(dtoRequest.idPedido()).orElseThrow(PedidoNotFoundException::new));
        if (dtoRequest.idCliente() != null)
            pagamento.setCliente(this.clienteRepository.findById(dtoRequest.idCliente()).orElse(null));
        return pagamento;
    }

    @Override
    public PagamentoResponseDTO toDTO(Pagamento entity) {
        ClienteResponseDTO clienteDTO = null;

        if (entity.getCliente() != null) {
            clienteDTO = this.clienteMapper.toDTO(entity.getCliente());
        }

        return new PagamentoResponseDTO(
                entity.getId(),
                entity.getValor(),
                entity.getData(),
                entity.getPedido().getId(),
                clienteDTO,
                entity.getTipoPagamento(),
                entity.getStatusPagamento());
    }
}
