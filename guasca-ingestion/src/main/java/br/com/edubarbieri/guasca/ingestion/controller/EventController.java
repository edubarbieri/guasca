package br.com.edubarbieri.guasca.ingestion.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.edubarbieri.guasca.ingestion.dto.UpdateEventStatus;
import br.com.edubarbieri.guasca.ingestion.service.ForceUpdateService;
import br.com.edubarbieri.guasca.ingestion.service.Status;

@RestController
@RequestMapping("/event")
public class EventController {

	@Autowired
	private ForceUpdateService forceUpdateService;

	@PutMapping("/force/{productId}")
	public ResponseEntity<UpdateEventStatus> forceUpdate(@PathVariable String productId) {
		Status forceUpdate = forceUpdateService.forceUpdate(productId);
		if(forceUpdate == Status.SUCCESS) {
			return ResponseEntity.ok(UpdateEventStatus.of(forceUpdate.toString()));
		}else if(forceUpdate == Status.ERROR) {
			return ResponseEntity.status(500).body(UpdateEventStatus.of(forceUpdate.toString()));
		}
		return ResponseEntity.status(404).body(UpdateEventStatus.of(forceUpdate.toString()));
	}
}
