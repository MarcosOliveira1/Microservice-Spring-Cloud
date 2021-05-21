package com.cloud.crud.service;

import com.cloud.crud.dataVO.ProdutoVO;
import com.cloud.crud.entity.Produto;
import com.cloud.crud.exception.ResourceNotFoundException;
import com.cloud.crud.message.ProdutoSendMessage;
import com.cloud.crud.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final ProdutoSendMessage produtoSendMessage;

    @Autowired
    public ProdutoService(ProdutoRepository produtoRepository, ProdutoSendMessage produtoSendMessage) {
        this.produtoRepository = produtoRepository;
        this.produtoSendMessage = produtoSendMessage;
    }

    public ProdutoVO create(ProdutoVO produtoVO) {
        ProdutoVO vo = ProdutoVO.create(produtoRepository.save(Produto.create(produtoVO)));
        produtoSendMessage.sendMessage(vo);
        return vo;
    }

    public Page<ProdutoVO> findAll(Pageable pageable) {
        var page = produtoRepository.findAll(pageable);
        return page.map(this::convertToProdutoVO);
    }

    public ProdutoVO findById(Long idProduto) {
        var entity = produtoRepository.findById(idProduto)
                .orElseThrow(() -> new ResourceNotFoundException("No Records found for this ID"));
        return ProdutoVO.create(entity);
    }

    public ProdutoVO update(ProdutoVO produtoVO) {
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

    private ProdutoVO convertToProdutoVO(Produto produto) {
        return ProdutoVO.create(produto);
    }

}
