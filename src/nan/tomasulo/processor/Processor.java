package nan.tomasulo.processor;

import java.util.LinkedList;

import nan.tomasulo.cache.Cache;

public class Processor {
	LinkedList<Process> processes;
	LinkedList<ReservationStation> reservationStations;
	RegisterResultStatus registerResultStatus;

	Cache dataCacheL1, instructionCacheL1;
	LinkedList<Cache> caches;
	public Processor() {
	}
}
