package br.com.uece.frameworks.stockfy.repository;

import br.com.uece.frameworks.stockfy.enums.TipoOperacao;
import br.com.uece.frameworks.stockfy.model.Estabelecimento;
import br.com.uece.frameworks.stockfy.model.Operacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Calendar;
import java.util.List;

/**
 * Create by Bruno Barbosa - 13/10/2018
 */
@Repository
public interface OperacaoRepository extends JpaRepository<Operacao, Long> {

    List<Operacao> findByReferenciaAndTipoOperacao(Long referencia, TipoOperacao tipoOperacao);

    List<Operacao> findByEstabelecimento(Estabelecimento estabelecimento);

    List<Operacao> findByDataEntradaBetween(Calendar inicio, Calendar fim);
}
