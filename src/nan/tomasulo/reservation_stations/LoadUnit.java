package nan.tomasulo.reservation_stations;

import nan.tomasulo.cache.Caches;
import nan.tomasulo.common_data_bus.CommonDataBus;
import nan.tomasulo.exceptions.InvalidReadException;
import nan.tomasulo.exceptions.InvalidWriteException;
import nan.tomasulo.instructions.Instruction;
import nan.tomasulo.memory.Memory;
import nan.tomasulo.processor.Processor;
import nan.tomasulo.registers.RegisterFile;
import nan.tomasulo.registers.RegisterStat;
import nan.tomasulo.reorderbuffer.ROBEntry;
import nan.tomasulo.reorderbuffer.ReorderBuffer;

public class LoadUnit extends ReservationStation {
	private boolean calculateAddress;

	public LoadUnit() {
		super();
	}

	@Override
	public void update() {
		if (getCurrStage() == ISSUED) {
			if (getQj() == -1) {
				if (!calculateAddress)
					calculateAddress = true;
				else {
					setAddress((short) (getAddress() + getVj()));
					setTimer(Caches.calculateReadAccessDelay(getAddress(), 0,
							Caches.getDataCaches()));
					setCurrStage(EXECUTE);
				}
			}
		} else if (getCurrStage() == EXECUTE) {
			setTimer(getTimer() - 1);
			if (getTimer() == 0) {
				short data;
				try {
					data = Caches.fetchData(getAddress());
					setResult(data);
					setCurrStage(WRITE_BACK);
					getInstruction().setExecutedTime(Processor.getClock());
				} catch (InvalidReadException e) {
					e.printStackTrace();
				} catch (InvalidWriteException e) {
					e.printStackTrace();
				}
			}
		} else if (getCurrStage() == WRITE_BACK) {
			if (CommonDataBus.deliver(getDst(), getResult())) {
				ReorderBuffer.getEntries()[getDst()].setValue(getResult());
				ReorderBuffer.getEntries()[getDst()].setReady(true);
				setCurrStage(COMMIT);
				getInstruction().setWrittenTime(Processor.getClock());
			}
		} else if (getCurrStage() == COMMIT) {
			if (ReorderBuffer.emptySlot(getDst())) {
				RegisterFile.setRegisterData(getInstruction().getRd(),
						getResult());
				getInstruction().setCommitedTime(Processor.getClock());
				ReorderBuffer.getEntries()[getDst()].resetEntry();
				if (RegisterStat.getRegisterROBEntryNumber(getInstruction()
						.getRd()) == getDst()) {
					RegisterStat.updateRegisterStats(getInstruction().getRd(),
							-1);
				}
				setCurrStage(FINISHED);
			}
		}
	}

	@Override
	public void reserve(Instruction instruction, int robEntry) {
		int vj = 0;
		int srcRegROBEntry = RegisterStat.getRegisterROBEntryNumber(instruction
				.getRs());
		if (srcRegROBEntry == -1) {
			vj = RegisterFile.getRegisterData(instruction.getRs());
			setVj(vj);
			setQj(-1);
		} else {
			ROBEntry srcRegROB = ReorderBuffer.getEntries()[srcRegROBEntry];
			vj = srcRegROB.getCorrectValue();
			if (srcRegROB.isReady()) {
				setVj(srcRegROB.getValue());
				setQj(-1);
			} else {
				setQj(srcRegROBEntry);
			}
		}
		setQk(-1);
		setAddress((short) instruction.getImm());
		calculateAddress = getQj() == -1;
		setBusy(true);
		setDst(robEntry);
		setOperation(instruction.getType());
		setInstruction(instruction);
		setCurrStage(ISSUED);
		RegisterStat.updateRegisterStats(instruction.getRd(), robEntry);
		try {
			short correctData = Memory
					.readDataWord((short) (getAddress() + vj));
			ReorderBuffer.getEntries()[getDst()].setCorrectValue(correctData);
		} catch (InvalidReadException e) {
			e.printStackTrace();
		}

	}

}