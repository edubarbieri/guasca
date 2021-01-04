package br.com.edubarbieri.guasca.ingestion.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateEventStatus {
	private String status;
	
	public static UpdateEventStatus of(String status) {
		UpdateEventStatus s = new UpdateEventStatus();
		s.setStatus(status);
		return s;
	}
}
