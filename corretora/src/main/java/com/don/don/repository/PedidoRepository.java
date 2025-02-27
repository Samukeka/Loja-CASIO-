package com.don.don.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.don.don.model.Cliente;
import com.don.don.model.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    List<Pedido> findByCliente(Cliente cliente);

    @Query("SELECT p FROM Pedido p JOIN FETCH p.itens WHERE p.cliente = :cliente")
    List<Pedido> findByClienteWithItems(@Param("cliente") Cliente cliente);
}
