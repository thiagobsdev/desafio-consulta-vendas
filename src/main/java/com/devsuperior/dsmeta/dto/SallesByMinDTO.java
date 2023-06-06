package com.devsuperior.dsmeta.dto;

import com.devsuperior.dsmeta.projections.SellerMinProjection;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SallesByMinDTO {
	
	private String sellerName;
	private Double total;
	
	public SallesByMinDTO() {
	}

	public SallesByMinDTO(String name, Double amount) {
		this.sellerName = name;
		this.total = amount;
	}
	
	
	public SallesByMinDTO(SellerMinProjection projection) {
		sellerName = projection.getSellerName();
		total = projection.getTotal();
	}
	
	@JsonProperty("sellerName")
	public String getName() {
		return sellerName;
	}
	
	@JsonProperty("total")
	public Double getAmount() {
		return total;
	}

	@Override
	public String toString() {
		return "SallesByMinDTO [sellerName=" + sellerName + ", total=" + total + "]";
	}
	
	
	

}
