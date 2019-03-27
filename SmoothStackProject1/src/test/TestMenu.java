package test;

import static org.junit.Assert.assertTrue;


import org.junit.jupiter.api.Test;
import driver.Main;

public class TestMenu {
	
	@Test
	public void testGetSelection() {
		int selection = Main.getSelection(5);
		assertTrue((selection>0 && selection<=5)); 
	}
}
