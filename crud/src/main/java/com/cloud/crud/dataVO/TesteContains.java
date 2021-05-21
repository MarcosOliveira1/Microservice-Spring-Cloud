package com.cloud.crud.dataVO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TesteContains {

    @Data
    @AllArgsConstructor
    @EqualsAndHashCode
    public static class ItemDeTeste implements Serializable {
        private static final long serialVersionUID = 590917418545560L;
        private Long id;
        private String Nome;
    }

    public static void main(String[] args) {
        List<ItemDeTeste> itens = new ArrayList<>();
        itens.add(new ItemDeTeste(1L, "Marcos"));
        itens.add(new ItemDeTeste(2L, "Marcos"));
        itens.add(new ItemDeTeste(3L, "Marcos"));
        itens.add(new ItemDeTeste(4L, "Marcos"));
        itens.add(new ItemDeTeste(5L, "Marcos"));
        ItemDeTeste itemDeTeste = new ItemDeTeste(6L,"Marcos");

        System.out.println(itens.indexOf(itemDeTeste)>0);
    }

}
