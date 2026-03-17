package com.gereciador.estabelecimento.services;

import java.util.List;

import com.gereciador.estabelecimento.entities.ItemPedido;
import com.gereciador.estabelecimento.entities.Pedido;
import com.gereciador.estabelecimento.entities.Produto;
import com.gereciador.estabelecimento.enums.Status;
import com.gereciador.estabelecimento.enums.TipoPagamento;
import com.gereciador.estabelecimento.exceptions.PagamentoFinalizadoException;
import com.gereciador.estabelecimento.exceptions.PagamentoNotFoundException;
import com.gereciador.estabelecimento.exceptions.PedidoNotFoundException;
import com.gereciador.estabelecimento.repositories.PedidoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gereciador.estabelecimento.controllers.dto.request.PagamentoRequestDTO;
import com.gereciador.estabelecimento.controllers.dto.response.PagamentoResponseDTO;
import com.gereciador.estabelecimento.entities.Pagamento;
import com.gereciador.estabelecimento.mapper.PagamentoMapper;
import com.gereciador.estabelecimento.repositories.PagamentoRepository;

@Service
public class PagamentoService implements BaseService<PagamentoResponseDTO, PagamentoRequestDTO, Long> {

    private final PagamentoRepository pagamentoRepository;
    private final PagamentoMapper mapper;
    private final PedidoRepository pedidoRepository;
    
    public PagamentoService(PagamentoRepository pagamentoRepository, PagamentoMapper mapper, PedidoRepository pedidoRepository) {
        this.pagamentoRepository = pagamentoRepository;
        this.mapper = mapper;
        this.pedidoRepository = pedidoRepository;
    }

    @Override
    @Transactional
    public PagamentoResponseDTO save(PagamentoRequestDTO obj) {
        Pagamento pagamento = this.pagamentoRepository.save(this.mapper.toEntity(obj));
        return this.mapper.toDTO(pagamento);
    }

    @Override
    public PagamentoResponseDTO update(Long primaryKey, PagamentoRequestDTO obj) {
        Pagamento pagamentoRequest = this.mapper.toEntity(obj);
        Pagamento pagamento = this.pagamentoRepository.findById(primaryKey).orElseThrow(PagamentoNotFoundException::new);

        if(pagamentoRequest.getCliente() != null){pagamento.setCliente(pagamentoRequest.getCliente());}
        if(pagamentoRequest.getData() != null){pagamento.setData(pagamentoRequest.getData());}
        if(pagamentoRequest.getPedido() != null){pagamento.setPedido(pagamentoRequest.getPedido());}
        if(pagamentoRequest.getValor() != null){pagamento.setValor(pagamentoRequest.getValor());}
        if(pagamentoRequest.getTipoPagamento() != null){pagamento.setTipoPagamento(pagamentoRequest.getTipoPagamento());}
        if(pagamentoRequest.getStatusPagamento() != null){pagamento.setStatusPagamento(pagamentoRequest.getStatusPagamento());}

        Pagamento pagamentoSaved = this.pagamentoRepository.save(pagamento);
        return this.mapper.toDTO(pagamentoSaved);
    }

    @Override
    @Transactional
    public void delete(Long primaryKey) {
       this.pagamentoRepository.deleteById(primaryKey);
    }

    @Override
    public PagamentoResponseDTO getById(Long primaryKey) {
        Pagamento pagamento = this.pagamentoRepository.findById(primaryKey).orElseThrow(PagamentoNotFoundException::new);
        return this.mapper.toDTO(pagamento);
    }

    @Override
    public List<PagamentoResponseDTO> getAll() {
       List<Pagamento> pagamento = this.pagamentoRepository.findAll();
       return pagamento.stream().map(this.mapper::toDTO).toList();
    }

    @Transactional
    public PagamentoResponseDTO finalizarPedido(Long primary, TipoPagamento tipoPagamento) {
        Pagamento pagamento = this.pagamentoRepository.findById(primary).orElseThrow(PagamentoNotFoundException::new);

        if (pagamento.getStatusPagamento() == Status.FINALIZADO) throw new PagamentoFinalizadoException();

        Pedido pedido = this.pedidoRepository.findById(pagamento.getPedido().getId()).orElseThrow(PedidoNotFoundException::new);
        pagamento.setTipoPagamento(tipoPagamento);
        pagamento.setStatusPagamento(Status.FINALIZADO);

        pedido.setStatusPedido(Status.FINALIZADO);

        List<ItemPedido> itensPedidos = pedido.getItensPedido();

        itensPedidos.forEach(itemPedido ->  {
            Produto produto = itemPedido.getProduto();
            if (produto.getQuantidade() >= itemPedido.getQuantidade()) produto.setQuantidade(produto.getQuantidade() - itemPedido.getQuantidade());
            else produto.setQuantidade(0);
        });

        return this.mapper.toDTO(this.pagamentoRepository.save(pagamento));
    }
    
}
