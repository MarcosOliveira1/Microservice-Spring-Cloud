package com.cloud.pagamento.VOs;

import com.cloud.pagamento.entity.Produto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoVo extends RepresentationModel<ProdutoVo> implements Serializable {

    private static final long serialVersionUID = 89817418545560L;

    private Long id;
    private Integer estoque;

    public static ProdutoVo create(Produto produto){
        return new ModelMapper().map(produto, ProdutoVo.class);
    }

}
