package com.cloud.pagamento.VOs;

import com.cloud.pagamento.entity.ProdutoVenda;
import com.cloud.pagamento.entity.Venda;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VendaVo  extends RepresentationModel<VendaVo> implements Serializable {

    private static final long serialVersionUID = 65917418545560L;

    private Long id;
    private Date data;
    private List<ProdutoVendaVo> produtos;
    private Double valorTotal;

    public static VendaVo create(Venda venda){
        return new ModelMapper().map(venda, VendaVo.class);
    }
}
