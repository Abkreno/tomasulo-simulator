package nan.tomasulo.common_data_bus;

import java.util.LinkedList;

import nan.tomasulo.processor.Processor;
import nan.tomasulo.reservation_stations.ReservationStation;

public class CommonDataBus {
	private static int maxNumOfWritesPerCycle;
	private static int currCycleWrites;

	public static void setMaxNumOfWritesPerCycle(int max) {
		maxNumOfWritesPerCycle = max;
	}

	public static boolean deliver(int robEntry, short data) {
		if (currCycleWrites == 0)
			return false;
		currCycleWrites--;
		LinkedList<ReservationStation> stations = Processor
				.getReservationStationsQueue();
		ReservationStation currStation;
		for (int i = 0; i < stations.size(); i++) {
			currStation = stations.get(i);
			if (!currStation.isBusy())
				continue;
			if (currStation.getQj() == robEntry) {
				currStation.setQj(-1);
				currStation.setVj(data);
				System.out.println("here");
			}
			if (currStation.getQk() == robEntry) {
				currStation.setQk(-1);
				currStation.setVk(data);
			}
		}

		return true;
	}

	public static void resetCommonDataBus() {
		currCycleWrites = maxNumOfWritesPerCycle;
	}
}
