package br.com.edubarbieri.guasca.ingestion.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.edubarbieri.guasca.ingestion.dto.UpdateEventStatus;
import br.com.edubarbieri.guasca.ingestion.service.ForceUpdateService;

@RestController
@RequestMapping("/event")
public class EventController {

	@Autowired
	private ForceUpdateService forceUpdateService;

	@PutMapping("/force/{productId}")
	public UpdateEventStatus forceUpdate(@PathVariable String productId) {
		return UpdateEventStatus.of(forceUpdateService.forceUpdate(productId).toString());
	}
}
