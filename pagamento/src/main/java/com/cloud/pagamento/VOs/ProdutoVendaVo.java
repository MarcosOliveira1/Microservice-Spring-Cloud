package com.cloud.pagamento.VOs;

import com.cloud.pagamento.entity.ProdutoVenda;
import com.cloud.pagamento.entity.Venda;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoVendaVo extends RepresentationModel<ProdutoVendaVo> implements Serializable {

    private static final long serialVersionUID = 8409174115560L;

    private Long id;
    private Long idProduto;
    private Integer quantidade;

    public static ProdutoVendaVo create(ProdutoVenda produtoVenda){
        return new ModelMapper().map(produtoVenda, ProdutoVendaVo.class);
    }

}
