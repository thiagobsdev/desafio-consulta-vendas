package com.devsuperior.dsmeta.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.projections.ReportMinProjection;
import com.devsuperior.dsmeta.projections.SellerMinProjection;

public interface SaleRepository extends JpaRepository<Sale, Long> {
	
	
	@Query(nativeQuery = true, value="SELECT TB_SELLER.NAME AS sellerName, SUM(TB_SALES.AMOUNT) AS total "
			+ "FROM TB_SALES "
			+ "INNER JOIN TB_SELLER "
			+ "ON TB_SALES.SELLER_ID = TB_SELLER.ID "
			+ "WHERE TB_SALES.DATE BETWEEN :minDate AND :maxDate "
			+ "GROUP BY TB_SELLER.NAME " 
			+ "ORDER BY TB_SELLER.NAME")
	List<SellerMinProjection> searchByDate(LocalDate minDate, LocalDate maxDate);
	
	
	
	@Query(nativeQuery = true, value="SELECT TB_SALES.ID, TB_SALES.DATE, TB_SALES.AMOUNT, TB_SELLER.NAME AS sellerName "
			+ "FROM TB_SALES "
			+ "INNER JOIN TB_SELLER ON TB_SALES.SELLER_ID = TB_SELLER.ID "
			+ "WHERE TB_SALES.DATE BETWEEN :minDate AND :maxDate "
			+ "and UPPER(TB_SELLER.NAME) LIKE UPPER(CONCAT('%', :name, '%'))", 
			countQuery = "SELECT COUNT(TB_SALES.ID) FROM TB_SALES INNER JOIN TB_SELLER ON TB_SALES.SELLER_ID = TB_SELLER.ID")
	Page<ReportMinProjection> searchReport(Pageable page,LocalDate minDate, LocalDate maxDate, String name );
	
	

}
