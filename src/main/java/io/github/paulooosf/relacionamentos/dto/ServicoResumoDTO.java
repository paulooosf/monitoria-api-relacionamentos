package io.github.paulooosf.relacionamentos.dto;

//Interface DTO — projeção para query nativa
//Os nomes dos métodos devem bater com os aliases do SELECT
public interface ServicoResumoDTO {
 String getFaixa();
 Long getTotalServicos();
 Double getMediaValor();
 Double getMenorValor();
 Double getMaiorValor();
}
