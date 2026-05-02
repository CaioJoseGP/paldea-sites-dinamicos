package com.jardim.paldea.model;

public class PlantForm {

    private String id;
    private String nome;
    private String descricao;
    private String categoria;
    private String preco;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getPreco() {
        return preco;
    }

    public void setPreco(String preco) {
        this.preco = preco;
    }

    public String validatePlantData() {
        if (cleanNome().isBlank()) {
            return "O nome da planta precisa ser preenchido.";
        }

        if (cleanCategoria().isBlank()) {
            return "Selecione uma categoria para o cadastro.";
        }

        if (cleanDescricao().isBlank()) {
            return "Informe uma descricao para apresentar a planta no catalogo.";
        }

        String priceText = cleanPreco();
        if (priceText.isBlank()) {
            return "Informe um preco para concluir a operacao.";
        }

        try {
            double price = Double.parseDouble(priceText);
            if (price <= 0) {
                return "O preco deve ser maior que zero.";
            }
            return null;
        } catch (NumberFormatException exception) {
            return "O preco precisa estar em formato numerico.";
        }
    }

    public long idAsLong() {
        return parseId(id);
    }

    public double priceAsDouble() {
        return Double.parseDouble(cleanPreco());
    }

    public Plant toPlant(long id) {
        return new Plant(id, cleanNome(), cleanDescricao(), cleanCategoria(), priceAsDouble());
    }

    public String cleanNome() {
        return safe(nome);
    }

    public String cleanDescricao() {
        return safe(descricao);
    }

    public String cleanCategoria() {
        return safe(categoria);
    }

    public static long parseId(String value) {
        String cleaned = safe(value);
        if (cleaned.isBlank()) {
            return 0L;
        }

        try {
            long parsed = Long.parseLong(cleaned);
            return parsed > 0 ? parsed : 0L;
        } catch (NumberFormatException exception) {
            return 0L;
        }
    }

    private String cleanPreco() {
        return safe(preco).replace(",", ".");
    }

    private static String safe(String value) {
        return value == null ? "" : value.trim();
    }
}
