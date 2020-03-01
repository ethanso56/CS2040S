///////////////////////////////////
// This is the main shift register class.
// Notice that it implements the ILFShiftRegister interface.
// You will need to fill in the functionality.
///////////////////////////////////

/**
 * class ShiftRegister
 * @author
 * Description: implements the ILFShiftRegister interface.
 */
public class ShiftRegister implements ILFShiftRegister {
    ///////////////////////////////////
    // Create your class variables here
    ///////////////////////////////////
    // TODO:
    int[] shiftRegister;
    int tap;

    public int[] getShiftRegister() {
        return this.shiftRegister;
    }

    ///////////////////////////////////
    // Create your constructor here:
    ///////////////////////////////////
    ShiftRegister(int size, int tap) {
        // TODO:
        if (tap < 0 || tap > size -1) {
            System.out.println("Invalid tap.");
            return;
        } else {
            shiftRegister = new int[size];
            this.tap = tap;
        }

    }

    ///////////////////////////////////
    // Create your class methods here:
    ///////////////////////////////////
    /**
     * setSeed
     * @param seed
     * Description:
     */
    @Override
    public void setSeed(int[] seed) {
        // TODO:
        for (int i = 0; i < seed.length; i++) {
            if (seed[i] < 0 || seed[i] > 1) {
                System.out.println("Error! Seed entered is invalid");
                return;
            }
        }
        int[] newSeed = new int[seed.length];

        for (int i = 0; i < seed.length; i++) {
            newSeed[i] = seed[seed.length - 1 - i];
        }
        shiftRegister = newSeed;
    }

    /**
     * shift
     * @return
     * Description:
     */
    @Override
    public int shift() {
        // TODO:
        int tapBit = shiftRegister.length - tap - 1;
        int feedbackBit = shiftRegister[0] ^ shiftRegister[tapBit];

        for (int i = 0; i < shiftRegister.length-1; i++) {
            shiftRegister[i] = shiftRegister[i+1];
        }
        shiftRegister[shiftRegister.length-1] = feedbackBit;
        return feedbackBit;
    }

    /**
     * generate
     * @param
     * @return
     * Description:
     */
    @Override
    public int generate(int k) {
        // TODO:
//        int[] bitsReturned = int[k];
        String hold = "";

        for (int i = 0; i < k; i++) {
            hold = hold + Integer.toString(shift());
        }
        int decimal = Integer.parseInt(hold,2);
        return decimal;
    }

    /**
     * Returns the integer representation for a binary int array.
     * @param array
     * @return
     */
    private int toBinary(int[] array) {
        // TODO:
        return 0;
    }
}
