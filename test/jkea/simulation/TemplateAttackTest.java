package jkea.simulation;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class TemplateAttackTest {

	TemplateAttack testClass;

	@Before
	public void setUp() throws Exception {
		ArrayList<HammingTemplate> testTemplates = new ArrayList<HammingTemplate>(
				16);
		for (int i = 0; i < 16; i++) {
			testTemplates.add(new HammingTemplate(1));
		}
		testClass = new TemplateAttack(testTemplates);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testIncorrectSize() {
		ArrayList<HammingTemplate> testTemplates = new ArrayList<HammingTemplate>(
				5);
		for (int i = 0; i < 5; i++) {
			testTemplates.add(new HammingTemplate(1));
		}
		new TemplateAttack(testTemplates);
	}

	@Test
	public void testUpdatePrior() {
		short[] plain = new short[0x10];
		double[] leak = new double[0x10];
		double[][] prior = new double[0x10][0x100];
		testClass.updatePrior(prior, plain, leak);

	}
	
	@Test(expected = IllegalArgumentException.class)
	public void updatePriorIncorrectPriorOne() {
		short[] plain = new short[0x10];
		double[] leak = new double[0x10];
		double[][] prior = new double[15][0x100];
		testClass.updatePrior(prior, plain, leak);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void updatePriorIncorrectPriorTwo() {
		short[] plain = new short[0x10];
		double[] leak = new double[0x10];
		double[][] prior = new double[0x10][255];
		testClass.updatePrior(prior, plain, leak);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void updatePriorIncorrectPlain() {
		short[] plain = new short[15];
		double[] leak = new double[0x10];
		double[][] prior = new double[0x10][0x100];
		testClass.updatePrior(prior, plain, leak);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void updatePriorIncorrectLeak() {
		short[] plain = new short[0x10];
		double[] leak = new double[15];
		double[][] prior = new double[0x10][0x100];
		testClass.updatePrior(prior, plain, leak);
	}


}
