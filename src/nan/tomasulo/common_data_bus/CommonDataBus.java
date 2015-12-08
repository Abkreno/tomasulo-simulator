package nan.tomasulo.common_data_bus;

import nan.tomasulo.reservation_stations.FunctionalUnits;
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
		ReservationStation[] stations = FunctionalUnits
				.getReservationStations();
		for (int i = 0; i < stations.length; i++) {
			if (!stations[i].isBusy())
				continue;
			if (stations[i].getQj() == robEntry) {
				stations[i].setQj(-1);
				stations[i].setVj(data);
			}
			if (stations[i].getQk() == robEntry) {
				stations[i].setQk(-1);
				stations[i].setVk(data);
			}
		}

		return true;
	}

	public static void resetCommonDataBus() {
		currCycleWrites = maxNumOfWritesPerCycle;
	}
}
