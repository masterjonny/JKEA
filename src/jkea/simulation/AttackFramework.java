package jkea.simulation;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

class AttackFramework {

	AESDevice device;
	TemplateAttack attack;
	ArrayList<HammingTemplate> templates;
	short[] key;
	double[][] prior;

	AttackFramework(double variance) {
		if (variance < 0) {
			throw new IllegalArgumentException("Invalid variance");
		}
		initAttack(variance);
	}

	void initAttack(double variance) {
		if (variance < 0) {
			throw new IllegalArgumentException("Invalid variance");
		}
		templates = new ArrayList<HammingTemplate>(0x10);
		for (int i = 0; i < 0x10; i++) {
			templates.add(new HammingTemplate(variance));
		}
		device = new AESDevice(templates);
		attack = new TemplateAttack(templates);
		prior = new double[0x10][0x100];
		key = device.getKey();
	}

	void runAttack(long numberMessages) {
		if (numberMessages < 0) {
			throw new IllegalArgumentException("Invalid number of messages");
		}
		for (int i = 0; i < 0x10; i++) {
			for (int j = 0; j < 0x100; j++) {
				prior[i][j] = 1. / 0x100;
			}
		}

		for (long i = 0; i < numberMessages; i++) {
			device.newPlain();
			short[] plain = device.getPlain();
			double[] trace = device.getTrace();
			prior = attack.updatePrior(prior, plain, trace);
		}
	}

	void writeToFile(String fileName) {
		try (Writer writer = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(fileName)))) {
			writer.write("[");
			for (int i = 0; i < 0x10; i++) {
				writer.write(Integer.toHexString(key[i] & 0xffff));
				if (i != 0x10 - 1)
					writer.write(",");
			}
			writer.write("]");
			writer.write(System.getProperty("line.separator"));
			for (int i = 0; i < 0x10; i++) {
				writer.write("[");
				for (int j = 0; j < 0x100; j++) {
					writer.write(Double.toString(prior[i][j]));
					if (j != 0x100 - 1)
						writer.write(",");
				}
				writer.write("]");
				writer.write(System.getProperty("line.separator"));
			}

		} catch (IOException ex) {
			throw new IllegalArgumentException(
					"Filename provided cannot be opened for writing!");
		}
	}

	public short[] getKey() {
		return key;
	}

	public double[][] getPrior() {
		return prior;
	}
}
