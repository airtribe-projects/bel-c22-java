package org.airtribe.AsyncApiApplicationBelC22.entity;

import java.util.List;


public class ProductResult {
  private List<ProductDimension> products;

  public ProductResult(List<ProductDimension> products) {
    this.products = products;
  }

  public List<ProductDimension> getProducts() {
    return products;
  }

  public void setProducts(List<ProductDimension> products) {
    this.products = products;
  }
}
