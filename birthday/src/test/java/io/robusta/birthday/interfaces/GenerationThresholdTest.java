package io.robusta.birthday.interfaces;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import io.robusta.birthday.implementations.Generation;
import io.robusta.birthday.implementations.GenerationThreshold;

public class GenerationThresholdTest {

	@Test
	public void findSmallestNumberOfPeopleRequiredToHave50() {
		GenerationThreshold generationThresholdTest = new GenerationThreshold();
		assertTrue(generationThresholdTest.findSmallestNumberOfPeopleRequiredToHave50() == 23);
	}

	@Test
	public void calculateProbabilityOfSame(int size) {
		GenerationThreshold generationThresholdTest = new GenerationThreshold();
		assertTrue(generationThresholdTest.calculateProbabilityOfSame(1) == 0);
		assertTrue(generationThresholdTest.calculateProbabilityOfSame(366) == 1);
		assertTrue(generationThresholdTest.calculateProbabilityOfSame(3) <.3);
		assertTrue(generationThresholdTest.calculateProbabilityOfSame(100) <0.9);
		
	}
	
	
}
