/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package net.agten.heatersimulator;

import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.FunctionBlock;
import net.sourceforge.jFuzzyLogic.plot.JFuzzyChart;
import net.sourceforge.jFuzzyLogic.rule.Variable;

public class TippingClass {
    public static void main(String[] args) throws Exception {
        String filename = "tipper.fcl";
        FIS fis = FIS.load(filename, true);

        if (fis == null) {
            System.err.println("Can't load file: '" + filename + "'");
            System.exit(1);
        }

        // Get default function block
        FunctionBlock fb = fis.getFunctionBlock(null);

        // Set inputs
        fb.setVariable("food", 9.25);
        fb.setVariable("service", 8.5);

        // Evaluate
        fb.evaluate();

        // Show output variable's chart
        fb.getVariable("tip").defuzzify();

        // Print ruleSet
        System.out.println(fb);
        System.out.println("Tip: " + fb.getVariable("tip").getValue());


        // Show
        JFuzzyChart.get().chart(fb);

        // Set inputs
        fis.setVariable("service", 3);
        fis.setVariable("food", 7);

        // Evaluate
        fis.evaluate();

        // Show output variable's chart
        Variable tip = fb.getVariable("tip");
        JFuzzyChart.get().chart(tip, tip.getDefuzzifier(), true);

        // Print ruleSet
        System.out.println(fis);


    }

}
