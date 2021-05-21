package com.cloud.pagamento.services;

import com.cloud.pagamento.VOs.ProdutoVo;
import com.cloud.pagamento.entity.Produto;
import com.cloud.pagamento.exception.ResourceNotFoundException;
import com.cloud.pagamento.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    @Autowired
    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public ProdutoVo create(ProdutoVo produtoVO) {
        ProdutoVo vo = ProdutoVo.create(produtoRepository.save(Produto.create(produtoVO)));
        return vo;
    }

    public Page<ProdutoVo> findAll(Pageable pageable) {
        var page = produtoRepository.findAll(pageable);
        return page.map(this::convertToProdutoVO);
    }

    public ProdutoVo findById(Long idProduto) {
        var entity = produtoRepository.findById(idProduto)
                .orElseThrow(() -> new ResourceNotFoundException("No Records found for this ID"));
        return ProdutoVo.create(entity);
    }

    public ProdutoVo update(ProdutoVo produtoVO) {
        final Optional<Produto> optionalProduto = produtoRepository.findById(produtoVO.getId());

        if (!optionalProduto.isPresent()) {
            new ResourceNotFoundException("No Records found for this ID");
        }

        return produtoVO.create(produtoRepository.save(Produto.create(produtoVO)));
    }

    public void delete(Long idProduto) {
        var entity = produtoRepository.findById(idProduto)
                .orElseThrow(() -> new ResourceNotFoundException("No Records found for this ID"));

        produtoRepository.delete(entity);
    }

    private ProdutoVo convertToProdutoVO(Produto produto) {
        return ProdutoVo.create(produto);
    }

}
