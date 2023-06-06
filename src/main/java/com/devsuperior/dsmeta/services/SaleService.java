package com.devsuperior.dsmeta.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.devsuperior.dsmeta.dto.ReportDto;
import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.dto.SallesByMinDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.projections.ReportMinProjection;
import com.devsuperior.dsmeta.projections.SellerMinProjection;
import com.devsuperior.dsmeta.repositories.SaleRepository;
import com.devsuperior.dsmeta.services.exceptions.ResourceNotFoundException;

@Service
public class SaleService {

	@Autowired
	private SaleRepository repository;

	LocalDate today = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
	LocalDate oneYearAgo = today.minusYears(1L);

	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	LocalDate dateInicial = null;
	LocalDate dateFinal = null;

	public SaleMinDTO findById(Long id) {
		Optional<Sale> result = repository.findById(id);
		Sale entity = result.get();
		return new SaleMinDTO(entity);
	}

	public List<SallesByMinDTO> getSummary(String minDate, String maxDate) {

		try {

			if (minDate == null || minDate.isEmpty()) {
				dateInicial = oneYearAgo;
			}
			if (maxDate == null || maxDate.isEmpty()) {
				dateFinal = today;
			} else {
				dateInicial = LocalDate.parse(minDate, formatter);
				dateFinal = LocalDate.parse(maxDate, formatter);
			}

			List<SellerMinProjection> list = repository.searchByDate(dateInicial, dateFinal);
			List<SallesByMinDTO> result = list.stream().map(x -> new SallesByMinDTO(x)).toList();
			return result;

		} catch (Exception e) {
			throw new ResourceNotFoundException("Data inválida");
		}
	}

	public Page<ReportDto> getReport(Pageable page, String minDate, String maxDate, String name) {

		try {
			if (minDate == null || minDate.isEmpty()) {
				dateInicial = oneYearAgo;
			}
			if (maxDate == null || maxDate.isEmpty()) {
				dateFinal = today;
			} else {
				dateInicial = LocalDate.parse(minDate, formatter);
				dateFinal = LocalDate.parse(maxDate, formatter);
			}
			
			Page<ReportMinProjection> list = repository.searchReport(page, dateInicial, dateFinal, name);
			Page<ReportDto> result = list.map(x -> new ReportDto(x));

			return result;

		} catch (Exception e) {
			throw new ResourceNotFoundException("Data inválida");
		}

	
	}

}
