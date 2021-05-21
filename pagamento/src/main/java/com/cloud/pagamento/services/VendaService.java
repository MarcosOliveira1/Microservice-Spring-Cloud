package com.cloud.pagamento.services;

import com.cloud.pagamento.VOs.VendaVo;
import com.cloud.pagamento.entity.Produto;
import com.cloud.pagamento.entity.ProdutoVenda;
import com.cloud.pagamento.entity.Venda;
import com.cloud.pagamento.exception.ResourceNotFoundException;
import com.cloud.pagamento.repository.ProdutoRepository;
import com.cloud.pagamento.repository.ProdutoVendaRepository;
import com.cloud.pagamento.repository.VendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class VendaService {

    private final VendaRepository vendaRepository;
    private final ProdutoVendaRepository produtoVendaRepository;

    @Autowired
    public VendaService(VendaRepository vendaRepository, ProdutoVendaRepository produtoVendaRepository) {
        this.vendaRepository = vendaRepository;
        this.produtoVendaRepository = produtoVendaRepository;
    }

    public VendaVo create(VendaVo vendaVo) {
        VendaVo vo = VendaVo.create(vendaRepository.save(Venda.create(vendaVo)));
        List<ProdutoVenda> produtosSalvos = new ArrayList<>();
        vendaVo.getProdutos().forEach(p -> {
            ProdutoVenda pv = ProdutoVenda.create(p);
            pv.setVenda(Venda.create(vo));
            produtosSalvos.add(produtoVendaRepository.save(pv));
        });
//        vo.setProdutos(Produto.create(produtosSalvos));
        return vo;
    }

    public Page<VendaVo> findAll(Pageable pageable) {
        var page = vendaRepository.findAll(pageable);
        return page.map(this::convertToVendaVo);
    }

    public VendaVo findById(Long idProduto) {
        var entity = vendaRepository.findById(idProduto)
                .orElseThrow(() -> new ResourceNotFoundException("No Records found for this ID"));
        return VendaVo.create(entity);
    }

    public VendaVo update(VendaVo vendaVo) {
        final Optional<Venda> optionalProduto = vendaRepository.findById(vendaVo.getId());

        if (!optionalProduto.isPresent()) {
            new ResourceNotFoundException("No Records found for this ID");
        }

        return vendaVo.create(vendaRepository.save(Venda.create(vendaVo)));
    }

    public void delete(Long idVenda) {
        var entity = vendaRepository.findById(idVenda)
                .orElseThrow(() -> new ResourceNotFoundException("No Records found for this ID"));

        vendaRepository.delete(entity);
    }

    private VendaVo convertToVendaVo(Venda venda) {
        return VendaVo.create(venda);
    }
}
