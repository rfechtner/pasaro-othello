package bitBoardApproachV1;

public class TestRunner {
	
	static long aa = 68920803328L;
	

	public static void main(String[] args){
		Human h = new Human(ChipType.WHITE);
		AI a = new AI(ChipType.BLACK);
		
//		long ab = aa|a.getOwnChips();
//		
//		h.spielfeldAusgeben(h.getOwnChips(), a.getOwnChips());
//		System.out.println("--------");
		

		long tmp = (long)Math.pow(2, 63);
		
//		long tmp2 = 68853694464L;
//		
//		h.spielfeldAusgeben(tmp2, 0L);
//		
//		System.out.println("--------");
		
//		tmp2 = tmp2 & ~(1 << 36);
//		
//		h.spielfeldAusgeben(tmp2, 0L);
//		
//		System.out.println("--------");
		
		long weiss = 0L;
		
		weiss = weiss | (1L << 63);
		
		
		h.spielfeldAusgeben(weiss, 0L);
		
		System.out.println("--------");
		
//		tmp = tmp & ~(1 << 8);
//		
//		h.spielfeldAusgeben( tmp, 0L);
//		
//		h.spielfeldAusgeben(16L, 0L);
//		/*2^x-1*/
//		
//		System.out.println("--------");
//		
//		h.spielfeldAusgeben(17729692631040L, 0L);
//
//		System.out.println("--------");
//		h.possibleMoves();
		
		

	}

}
