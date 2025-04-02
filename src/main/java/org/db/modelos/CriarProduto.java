package org.db.modelos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CriarProduto {
    private String title;
    private String description;
    private Double price;
    private Double discountPercentage;
    private Double rating;
    private Integer stock;
    private String brand;
    private String category;
    private String thumbnail;

    public static CriarProduto criarProdutoValido() {
        return CriarProduto.builder()
                .title("Perfume")
                .description("Produto teste")
                .price(13.0)
                .discountPercentage(8.4)
                .rating(4.26)
                .stock(65)
                .brand("Impression of Acqua Di Gio")
                .category("fragrances")
                .thumbnail("https://i.dummyjson.com/data/products/11/thumnail.jpg")
                .build();
    }

    public static CriarProduto criarProdutoComCamposFaltando() {
        return CriarProduto.builder()
                .build();
    }

}