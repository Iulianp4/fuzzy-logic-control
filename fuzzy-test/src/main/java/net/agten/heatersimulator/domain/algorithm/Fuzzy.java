package net.agten.heatersimulator.domain.algorithm;

import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.FunctionBlock;
import net.sourceforge.jFuzzyLogic.plot.JFuzzyChart;
import net.sourceforge.jFuzzyLogic.rule.Variable;

public class Fuzzy implements ControllerAlgorithm {
    public final static int INITIAL_TARGET_ADC = 830;
    public final static int MAX_RESULT = 255;
    private int currentAdc;
    private int targetAdc;
    private int power;
    private FIS fis;

    public Fuzzy() {
        this.currentAdc = 100;
        this.targetAdc = INITIAL_TARGET_ADC;

        String filename = "heater.fcl";
        this.fis = FIS.load(filename, true);

        if (fis == null) {
            System.err.println("Can't load file: '" + filename + "'");
            System.exit(1);
        }
    }

    @Override
    public short nextValue(short curAdc) {
        // Get default function block
        FunctionBlock fb = fis.getFunctionBlock(null);

        // Set inputs
        fb.setVariable("currentADC", this.currentAdc);
        fb.setVariable("targetADC", this.targetAdc);

        // Evaluate
        fb.evaluate();

        // Show output variable's chart
        fb.getVariable("power").defuzzify();

        // Print output
        System.out.println("Power: " + fb.getVariable("power").getValue());

//        // Show
//        JFuzzyChart.get().chart(fb);
//
//        // Set inputs
//        fis.setVariable("currentADC", 3);
//        fis.setVariable("lastADC", 7);
//
//        // Evaluate
//        fis.evaluate();
//
//        // Show output variable's chart
//        Variable power = fb.getVariable("power");
//        JFuzzyChart.get().chart(power, power.getDefuzzifier(), true);

        this.power = (int) fb.getVariable("power").getValue();
        return (short) Math.max(Math.min(this.power,MAX_RESULT), 0);
    }

    @Override
    public void setTargetAdc(short targetAdc) {
        this.targetAdc = targetAdc;
    }
}
