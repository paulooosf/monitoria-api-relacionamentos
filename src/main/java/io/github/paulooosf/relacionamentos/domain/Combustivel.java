package io.github.paulooosf.relacionamentos.domain;

public enum Combustivel {
    ALCOOL(1, "Álcool"),
    GASOLINA(2, "Gasolina"),
    DIESEL(3, "Diesel"),
    FLEX(4, "Flex");

    private Integer codigo;
    private String tipo;

    Combustivel(Integer codigo, String tipo) {
        this.codigo = codigo;
        this.tipo = tipo;
    }

    public Integer getCodigo() { return codigo; }
    public String getTipo() { return tipo; }
}
