package bitBoardApproachV1;

public class Human extends Player {
	
	/* Test */
	
	public Human(ChipType chipType){
		super(chipType, genOwnChips(chipType), genOtherChips(chipType));
	}
	
}
