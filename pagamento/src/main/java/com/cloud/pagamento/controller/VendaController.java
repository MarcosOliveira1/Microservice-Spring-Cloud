package com.cloud.pagamento.controller;

import com.cloud.pagamento.VOs.VendaVo;
import com.cloud.pagamento.services.VendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/venda")
public class VendaController {

    private final VendaService vendaService;
    private final PagedResourcesAssembler<VendaVo> assembler;

    @Autowired
    public VendaController(VendaService vendaService, PagedResourcesAssembler<VendaVo> assembler) {
        this.vendaService = vendaService;
        this.assembler = assembler;
    }

    @GetMapping(value = "/{id}", produces = {"application/json", "application/xml", "application/x-yaml"})
    public VendaVo findById(@PathVariable("id") Long id) {
        VendaVo vendaVo = vendaService.findById(id);
        vendaVo.add(linkTo(methodOn(VendaController.class).findById(id)).withSelfRel());
        return vendaVo;
    }

    @GetMapping(produces = {"application/json", "application/xml", "application/x-yaml"})
    public ResponseEntity findALl(@RequestParam(value = "page", defaultValue = "0") int page,
                                  @RequestParam(value = "limit", defaultValue = "12") int limit,
                                  @RequestParam(value = "direction", defaultValue = "asc") String direction) {
        var sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection,"data"));

        Page<VendaVo> vendas = vendaService.findAll(pageable);

        vendas.stream().forEach(p -> p.add(linkTo(methodOn(VendaController.class).findById(p.getId())).withSelfRel()));

        PagedModel<EntityModel<VendaVo>> pagedModel = assembler.toModel(vendas);
        return new ResponseEntity<>(pagedModel, HttpStatus.OK);
    }

    @PostMapping(produces = {"application/json", "application/xml", "application/x-yaml"},
            consumes = {"application/json", "application/xml", "application/x-yaml"})
    public VendaVo create(@RequestBody VendaVo vendaVo) {
        VendaVo vo = vendaService.create(vendaVo);
        vo.add(linkTo(methodOn(VendaController.class).findById(vo.getId())).withSelfRel());
        return vo;
    }

    @PutMapping(produces = {"application/json", "application/xml", "application/x-yaml"},
            consumes = {"application/json", "application/xml", "application/x-yaml"})
    public VendaVo update(@RequestBody VendaVo vendaVo) {
        VendaVo vo = vendaService.update(vendaVo);
        vo.add(linkTo(methodOn(VendaController.class).findById(vo.getId())).withSelfRel());
        return vo;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        vendaService.delete(id);
        return ResponseEntity.ok().build();
    }

}
