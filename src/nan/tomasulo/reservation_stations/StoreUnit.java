package nan.tomasulo.reservation_stations;

import nan.tomasulo.cache.Caches;
import nan.tomasulo.exceptions.InvalidReadException;
import nan.tomasulo.exceptions.InvalidWriteException;
import nan.tomasulo.instructions.Instruction;
import nan.tomasulo.processor.Processor;
import nan.tomasulo.registers.RegisterFile;
import nan.tomasulo.registers.RegisterStat;
import nan.tomasulo.reorderbuffer.ROBEntry;
import nan.tomasulo.reorderbuffer.ReorderBuffer;

public class StoreUnit extends ReservationStation {
	private boolean calculateAddress;

	public StoreUnit() {
		super();
	}

	@Override
	public void update() {
		if (getCurrStage() == ISSUED) {
			if (getQj() == -1 && getQk() == -1) {
				if (!calculateAddress)
					calculateAddress = true;
				else {
					setAddress((short) (getAddress() + getVk()));
					setTimer(Caches.calculateWriteAccessDelay(getAddress(), 0,
							Caches.getDataCaches()));
					setCurrStage(EXECUTE);
				}
			}
		} else if (getCurrStage() == EXECUTE) {
			setTimer(getTimer() - 1);
			if (getTimer() == 0) {
				try {
					Caches.writeCacheBlock(getAddress(), 0, getVj(),
							Caches.getDataCaches());
					setCurrStage(WRITE_BACK);
					getInstruction().setExecutedTime(Processor.getClock());
				} catch (InvalidReadException e) {
					System.err.println(e.getMessage());
				} catch (InvalidWriteException e) {
					System.err.println(e.getMessage());
				}
			}
		} else if (getCurrStage() == WRITE_BACK) {
			ReorderBuffer.getEntries()[getDst()].setReady(true);
			setCurrStage(COMMIT);
			getInstruction().setWrittenTime(Processor.getClock());
		} else if (getCurrStage() == COMMIT) {
			if (ReorderBuffer.emptySlot(getDst())) {
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

		int vk = 0;
		int tmpRegROBEntry = RegisterStat.getRegisterROBEntryNumber(instruction
				.getRt());
		if (tmpRegROBEntry == -1) {
			vk = RegisterFile.getRegisterData(instruction.getRt());
			setVk(vk);
			setQk(-1);
		} else {
			ROBEntry tmpRegROB = ReorderBuffer.getEntries()[tmpRegROBEntry];
			vk = tmpRegROB.getCorrectValue();
			if (tmpRegROB.isReady()) {
				setVk(tmpRegROB.getValue());
				setQk(-1);
			} else {
				setQk(tmpRegROBEntry);
			}
		}

		setAddress((short) instruction.getImm());
		calculateAddress = getQj() == -1 && getQk() == -1;
		setBusy(true);
		setDst(robEntry);
		setOperation(instruction.getType());
		setInstruction(instruction);
		setCurrStage(ISSUED);
	}
}
