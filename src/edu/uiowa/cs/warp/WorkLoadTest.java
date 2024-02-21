package edu.uiowa.cs.warp;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

class WorkLoadTest {
	
	@Test
	@Timeout (value = 1000, unit = TimeUnit.MILLISECONDS)
	//Tests getFlowNames to ensure it returns the correct flow names given a text file of flows
	public void testGetFlowNamesMultipleFlows() throws FileNotFoundException {
	  	WorkLoad workLoad = new WorkLoad(.9, .99, "TestFlows.txt");
	  	String[] expectedFlowNames = {"F1", "F5", "F2", "F4", "F3", "F6"};
	  	var actualFlowNames = workLoad.getFlowNames();
	  	for(int i = 0; i< actualFlowNames.length; i++) {
	  		String expectedName = expectedFlowNames[i];
	  		String actualName = actualFlowNames[i];
	  		assertEquals(expectedName, actualName, "Does not return correct flow names when input is multiple flows");
	  	}
	}
	
	@Test
	@Timeout (value = 1000, unit = TimeUnit.MILLISECONDS)
	//Tests getFlowNames to ensure it returns an empty list when the input is one flow
	public void testGetFlowNamesOneFlow() throws FileNotFoundException {
		WorkLoad workLoad = new WorkLoad(.9, .99, "OneFlow.txt");
		String expectedFlowName = "F3";
	  	var actualFlowNames = workLoad.getFlowNames();
	  	var actualFlowName = actualFlowNames[0];
		assertEquals(expectedFlowName, actualFlowName, "Does not return empty list when input is one flow");
	}
	
	//getNodeIndex tests
	@Test
	@Timeout (value = 1000, unit = TimeUnit.MILLISECONDS)
	//Tests getNodeIndex to ensure it returns the correct node indexes given a text file of multiple flows
	public void TestGetNodeIndexMultipleFlows() throws FileNotFoundException {
	  	WorkLoad workLoad = new WorkLoad(.9, .99, "TestIndexes.txt");
	  	int[] expectedIndexes = {3, 0, 1, 2, 4};
	  	NodeMap nodes = workLoad.getNodes();
	  	int i = 0;
	  	for (String name : nodes.keySet()) {
	  		int expectedIndex = expectedIndexes[i];
	  		int actualIndex = workLoad.getNodeIndex(name);
	  		assertEquals(expectedIndex, actualIndex, "Does not return correct indexes when input is multiple flows");
	  		i++;
	  	}
	}
	
	@Test
	@Timeout (value = 1000, unit = TimeUnit.MILLISECONDS)
	//Tests getNodeIndex to ensure it returns 0 when the input is one flow
	public void testGetNodeIndexOneFlow() throws FileNotFoundException {
		WorkLoad workLoad = new WorkLoad(.9, .99, "OneFlow.txt");
		int[] expectedIndexes = {0, 1, 2, 3, 4, 5};
	  	NodeMap nodes = workLoad.getNodes();
	  	int i = 0;
	  	for (String name : nodes.keySet()) {
	  		int expectedIndex = expectedIndexes[i];
	  		int actualIndex = workLoad.getNodeIndex(name);
	  		assertEquals(expectedIndex, actualIndex, "Does not return correct indexes when input is one flow");
	  		i++;
	  	}
	}
	
	//getNodesInFlow tests
	@Test
	@Timeout (value = 1000, unit = TimeUnit.MILLISECONDS)
	//Tests getNodesInFlow to ensure it returns the correct nodes when the input is a text file of multiple flows
	public void testGetNodesInFlowMultipleFlows() throws FileNotFoundException {
		WorkLoad workLoad = new WorkLoad(.9, .99, "TestIndexes.txt");
	  	String[] workLoadFlows = workLoad.getFlowNames();
	  	ArrayList<String> actualNodes = new ArrayList<String>();
	  	String[] expectedNodes = {"B", "C", "D", "A", "B","C", "D", "E"};
	  	//Initializing ArrayList for nodes in the flow
	  	for (String flowName : workLoadFlows) {
	  		String[] nodesInFlow = workLoad.getNodesInFlow(flowName);
	  		for (String node : nodesInFlow){
	  			actualNodes.add(node);
	  		}
	  	}
	  	//Comparing each expected node to each actual node
	  	for (int i = 0; i < expectedNodes.length; i++) {
	  		var expectedNode = expectedNodes[i];
	  		var actualNode = actualNodes.get(i);
	  		assertEquals(expectedNode, actualNode, "Does not return correct nodes when input is multiple flows");
	  	}
	}
	
	@Test
	@Timeout (value = 1000, unit = TimeUnit.MILLISECONDS)
	//Tests getNodesInFlow to ensure it returns the correct nodes given a text file of one flow
	public void testGetNodesInFlowOneFlow() throws FileNotFoundException {
	  	WorkLoad workLoad = new WorkLoad(.9, .99, "OneFlow.txt");
	  	String[] workLoadFlows = workLoad.getFlowNames();
	  	ArrayList<String> actualNodes = new ArrayList<String>();
	  	String[] expectedNodes = {"C", "D", "E", "J", "K","L"};
	  	//Initializing ArrayList for nodes in the flow
	  	for (String flowName : workLoadFlows) {
	  		String[] nodesInFlow = workLoad.getNodesInFlow(flowName);
	  		for (String node : nodesInFlow){
	  			actualNodes.add(node);
	  		}
	  	}
	  	//Comparing each expected node to each actual node
	  	for (int i = 0; i < expectedNodes.length; i++) {
	  		var expectedNode = expectedNodes[i];
	  		var actualNode = actualNodes.get(i);
	  		assertEquals(expectedNode, actualNode, "Does not return correct nodes when input is one flow");
	  	}
	}
	
	//getHyperPeriod tests
	@Test
	@Timeout (value = 1000, unit = TimeUnit.MILLISECONDS)
	//Tests getHyperPeriod to ensure it returns the correct hyper period given a text file of multiple flows
	public void testGetHyperPeriodMultipleFlows() throws FileNotFoundException {
	  	WorkLoad workLoad = new WorkLoad(.9, .99, "TestFlows.txt");
	  	int expectedHyperPeriod = 300; //LCM of period, which in this case is 300
	  	int actualHyperPeriod = workLoad.getHyperPeriod();
	  	assertEquals(expectedHyperPeriod, actualHyperPeriod, "Does not return correct hyper period when input is multiple flows");
	}
	
	@Test
	@Timeout (value = 1000, unit = TimeUnit.MILLISECONDS)
	//Tests getHyperPeriod to ensure it returns correct hyper period when the input is a file of one flow
	public void testGetHyperPeriodOneFlow() throws FileNotFoundException {
		WorkLoad workLoad = new WorkLoad(.9, .99, "OneFlow.txt");
		int expectedHyperPeriod = 50;
		int actualHyperPeriod = workLoad.getHyperPeriod();
		assertEquals(expectedHyperPeriod, actualHyperPeriod, "Does not return correct hyper period when input is one flow");
	}
	
	//getNumTxAttemptsPerLink test
	@Test
	@Timeout (value = 1000, unit = TimeUnit.MILLISECONDS)
	//Tests getNumTxAttemptsPerLink to ensure it returns the correct hyper period given a text file of multiple flows
	public void testGetNumTxAttemptsPerLinkMultipleFlows() throws FileNotFoundException {
	  	WorkLoad workLoad = new WorkLoad(.9, .99, "TestIndexes.txt");
	  	String[] workLoadFlows = workLoad.getFlowNames();
	  	Integer[] expectedNumTxAttempts1 = {3, 3, 0};//expected num tx attempts for first flow
	  	Integer[] actualNumTxAttempts1 = workLoad.getNumTxAttemptsPerLink(workLoadFlows[0]);
	  	Integer[] expectedNumTxAttempts2 = {3, 4, 4, 4, 0};//expected num tx attempts for second flow
	  	Integer[] actualNumTxAttempts2 = workLoad.getNumTxAttemptsPerLink(workLoadFlows[1]);
	  	for (int j = 0; j<actualNumTxAttempts1.length; j++) {
	  		int expectedNumTxAttempt = expectedNumTxAttempts1[j];
	  		int actualNumTxAttempt = actualNumTxAttempts1[j];
	  		assertEquals(expectedNumTxAttempt, actualNumTxAttempt, "Does not return correct num tx attempts for input of multiple flows");
	  	}
	  	for (int j = 0; j<actualNumTxAttempts2.length; j++) {
	  		int expectedNumTxAttempt = expectedNumTxAttempts2[j];
	  		int actualNumTxAttempt = actualNumTxAttempts2[j];
	  		assertEquals(expectedNumTxAttempt, actualNumTxAttempt, "Does not return correct num tx attempts for input of multiple flows");
	  	}
	}
	
	@Test
	@Timeout (value = 1000, unit = TimeUnit.MILLISECONDS)
	//Tests getNumTxAttemptsPerLink to ensure it returns the correct hyper period given a text file of one flow
	public void testGetNumTxAttemptsPerLinkOneFlow() throws FileNotFoundException {
	  	WorkLoad workLoad = new WorkLoad(.9, .99, "OneFlow.txt");
	  	String[] workLoadFlows = workLoad.getFlowNames();
	  	Integer[] expectedNumTxAttempts = {3, 4, 4, 5, 4, 0};//expected num tx attempts for this particular flow
	  	Integer[] actualNumTxAttempts = workLoad.getNumTxAttemptsPerLink(workLoadFlows[0]);
	  	for (int j = 0; j<actualNumTxAttempts.length; j++) {
	  		int expectedNumTxAttempt = expectedNumTxAttempts[j];
	  		int actualNumTxAttempt = actualNumTxAttempts[j];
	  		assertEquals(expectedNumTxAttempt, actualNumTxAttempt, "Does not return correct num tx attempts when input is one flow");
	  	}
	}
	
	//maxFlowLength tests
	@Test
	@Timeout (value = 1000, unit = TimeUnit.MILLISECONDS)
	//Tests maxFlowLength to ensure it returns the correct max flow length given a text file of multiple flows
	public void testMaxFlowLengthMultipleFlows() throws FileNotFoundException {
	  	WorkLoad workLoad = new WorkLoad(.9, .99, "TestFlows.txt");
	  	int expectedMaxFlowLength = 8; //max flow length, which in this case is 8
	  	int actualMaxFlowLength = workLoad.maxFlowLength();
	  	assertEquals(expectedMaxFlowLength, actualMaxFlowLength, "Does not return correct max flow length when input is multiple flows");
	}
	
	@Test
	@Timeout (value = 1000, unit = TimeUnit.MILLISECONDS)
	//Tests maxFlowLength to ensure it returns the correct max flow length given a text file of one flow
	public void testMaxFlowLengthOneFlow() throws FileNotFoundException {
	  	WorkLoad workLoad = new WorkLoad(.9, .99, "OneFlow.txt");
	  	int expectedMaxFlowLength = 6;
	  	int actualMaxFlowLength = workLoad.maxFlowLength();
	  	assertEquals(expectedMaxFlowLength, actualMaxFlowLength, "Does not return correct max flow length when input is one flow");
	}
	
	@Test
	@Timeout (value = 1000, unit = TimeUnit.MILLISECONDS)
	//Tests maxFlowLength to ensure that the flow lengths don't change when finding the max length
	public void testMaxFlowLengthLengthsRemain() throws FileNotFoundException {
		WorkLoad workLoad = new WorkLoad(.9, .99, "TestFlows.txt");
		int x = workLoad.maxFlowLength();
		FlowMap flows = workLoad.getFlows();
		Integer[] expectedSizes = {3, 3, 7, 6, 8, 5};//expected sizes based on input file
		int i = 0;
		for (Flow flow : flows.values()) {
			int expectedSize = expectedSizes[i];
			int actualSize = flow.nodes.size();
			assertEquals(expectedSize, actualSize, "Flows changed length when maxFlowLength was run");
			i++;
		}
	}
	
	@Test
	@Timeout (value = 1000, unit = TimeUnit.MILLISECONDS)
	//Tests maxFlowLength to ensure that the flow names and order don't change when finding the max length
	public void testMaxFlowLengthFlowNames() throws FileNotFoundException {
		WorkLoad workLoad = new WorkLoad(.9, .99, "TestFlows.txt");
		int x = workLoad.maxFlowLength();
		String[] expectedFlowNames = {"F1", "F5", "F2", "F4", "F3", "F6"};
	  	var actualFlowNames = workLoad.getFlowNames();
	  	for(int i = 0; i< actualFlowNames.length; i++) {
	  		String expectedName = expectedFlowNames[i];
	  		String actualName = actualFlowNames[i];
	  		assertEquals(expectedName, actualName, "Flow names change when running maxFlowLength");
	  	}
	}
	
	
	//getFlowDeadline tests
	@Test
	@Timeout (value = 1000, unit = TimeUnit.MILLISECONDS)
	//Tests getFlowDeadline to ensure it returns the correct flow deadlines given a text file of multiple flows
	public void testGetFlowDeadlineMultipleFlows() throws FileNotFoundException {
	  	WorkLoad workLoad = new WorkLoad(.9, .99, "TestFlows.txt");
	  	String[] workLoadFlows = workLoad.getFlowNames();
	  	Integer[] expectedFlowDeadlines = {20, 75, 50, 75, 50, 75}; //Expected flow deadlines from the input file
	  	for (int i = 0; i<workLoadFlows.length; i++) {
	  		int expectedFlowDeadline = expectedFlowDeadlines[i];
	  		int actualFlowDeadline = workLoad.getFlowDeadline(workLoadFlows[i]);
	  		assertEquals(expectedFlowDeadline, actualFlowDeadline, "Does not return correct flow deadline when input is multiple flows");
	  	}
	}
	
	@Test
	@Timeout (value = 1000, unit = TimeUnit.MILLISECONDS)
	//Tests getFlowDeadline to ensure it returns the correct flow deadlines given a text file of one flow
	public void testGetFlowDeadlineOneFlow() throws FileNotFoundException {
	  	WorkLoad workLoad = new WorkLoad(.9, .99, "OneFlow.txt");
	  	String[] workLoadFlows = workLoad.getFlowNames();
	  	int expectedFlowDeadline = 50; //Expected flow deadline from the input file
	  	int actualFlowDeadline = workLoad.getFlowDeadline(workLoadFlows[0]);
	  	assertEquals(expectedFlowDeadline, actualFlowDeadline, "Does not return correct flow deadline when input is one flow");
	}
	
	//setFlowDeadline tests
	@Test
	@Timeout (value = 1000, unit = TimeUnit.MILLISECONDS)
	//Tests setFlowDeadline to ensure it sets the correct flow deadline given a text file of one flow
	public void testSetFlowDeadline() throws FileNotFoundException {
	  	WorkLoad workLoad = new WorkLoad(.9, .99, "OneFlow.txt");
	  	int expectedFlowDeadline = 1; //Flow deadline to change to
	  	workLoad.setFlowDeadline("F3", expectedFlowDeadline);
	  	int actualFlowDeadline = workLoad.getFlowDeadline("F3");
	  	assertEquals(expectedFlowDeadline, actualFlowDeadline, "Does not set correct flow deadline");
	}
	
	@Test
	@Timeout (value = 1000, unit = TimeUnit.MILLISECONDS)
	//Tests setFlowDeadline to ensure the flow name stays the same
	public void testSetFlowDeadlineName() throws FileNotFoundException {
	  	WorkLoad workLoad = new WorkLoad(.9, .99, "OneFlow.txt");
	  	workLoad.setFlowDeadline("F3", 1);
	  	String expectedFlowName = "F3";
	  	String actualFlowName = workLoad.getFlowNames()[0];
	  	assertEquals(expectedFlowName, actualFlowName, "setFlowDeadline changes the name of the flow");
	}
	
	@Test
	@Timeout (value = 1000, unit = TimeUnit.MILLISECONDS)
	//Tests setFlowDeadline to ensure the nodes stay the same
	public void testSetFlowDeadlineNodes() throws FileNotFoundException {
	  	WorkLoad workLoad = new WorkLoad(.9, .99, "OneFlow.txt");
	  	workLoad.setFlowDeadline("F3", 1);
	  	String[] actualNodes = workLoad.getNodesInFlow("F3");
	  	String[] expectedNodes = {"C", "D", "E", "J", "K", "L"};
	  	for (int i = 0; i<actualNodes.length; i++) {
	  		String actualNode = actualNodes[i];
	  		String expectedNode = expectedNodes[i];
	  		assertEquals(expectedNode, actualNode, "setFlowDeadline changes the nodes of the flow");
	  	}
	}

	@Test
	@Timeout (value = 1000, unit = TimeUnit.MILLISECONDS)
	public void testAddFlow() throws FileNotFoundException {
		String addFlow = "F10"; // The flow that is added
		WorkLoad workLoad = new WorkLoad(.9, .99, "Example4.txt");
		workLoad.addFlow(addFlow);
		
		String[] expectedFlows = {"F0", "F1", "F10"}; // Expected flows with added flow
		var expectedFlowNames = workLoad.getFlowNames();
		
		for(int i = 0; i< expectedFlowNames.length; i++) { // Checks all flows, including new one
	  		String currentFlow = expectedFlows[i];
	  		String currentName = expectedFlowNames[i];	
	  		assertEquals(currentFlow, currentName, "Flow was not added"); 
	  	}
		
		
		
		
	}
	@Test
	@Timeout (value = 1000, unit = TimeUnit.MILLISECONDS)
	public void testAddNodeToFlow() throws FileNotFoundException {
		String addNode = "D"; // Adds node D to end
		WorkLoad workLoad = new WorkLoad(.9, .99, "Example4.txt");
		workLoad.addNodeToFlow("F1", addNode);
		
		String[] workLoadFlows = workLoad.getFlowNames();
	  	ArrayList<String> Nodes = new ArrayList<String>(); // Creates Array list
	  	String[] expectedNodes = {"A", "B", "C", "D", "C", "B", "A", "D"}; // Adds the Node D to the end when checked
	  	for (String flowName : workLoadFlows) {
	  		String[] flowNodes = workLoad.getNodesInFlow(flowName);
	  		for (String node : flowNodes){ // Adds the node string to array
	  			Nodes.add(node);
	  		}
	  	}
	  	for (int i = 0; i < expectedNodes.length; i++) { // Begins checking nodes after add
	  		var expectedNode = expectedNodes[i];
	  		var checkNode = Nodes.get(i);
	  		assertEquals(expectedNode, checkNode, "Node was not added to flow");
	  	}

		
		
	}
	
	@Test
	@Timeout (value = 1000, unit = TimeUnit.MILLISECONDS)
	public void testGetTotalTxAttemptsInFlows() throws FileNotFoundException {
		ArrayList<Integer> expectedTotalTx = new ArrayList<>(Arrays.asList(5,4)); // Expected Tx values
		WorkLoad workLoad = new WorkLoad(.9, .99, "Example4.txt");
		ArrayList<Integer> totalTx = new ArrayList<Integer>(); // Tracks Tx values from file in an array
		
		String[] flowNames = workLoad.getFlowNames(); 
		
			for(String flowName : flowNames) {
				totalTx.add(workLoad.getTotalTxAttemptsInFlow(flowName)); // Adds Tx values from file
			}
			assertEquals(expectedTotalTx, totalTx, "Tx total was incorect");
		}

		
	@Test
	@Timeout (value = 1000, unit = TimeUnit.MILLISECONDS)
	public void testGetTotalTxAttemptsInFlow () throws FileNotFoundException {
		ArrayList<Integer> expectedTotalTx2 = new ArrayList<>(Arrays.asList(33)); // Expected Tx values
		WorkLoad workLoad2 = new WorkLoad(.9, .99, "LongChain.txt");
		ArrayList<Integer> totalTx = new ArrayList<Integer>(); // Tracks Tx values from file in an array
		
		String[] flowNames = workLoad2.getFlowNames(); 
		
		for(String flowName : flowNames) {
			totalTx.add(workLoad2.getTotalTxAttemptsInFlow(flowName)); // Adds Tx values from file
		}
		assertEquals(expectedTotalTx2, totalTx, "Tx total was incorect");
		
		
	}
	@Test
	@Timeout (value = 1000, unit = TimeUnit.MILLISECONDS)
	public void testGetFlowPrioritys() throws FileNotFoundException {
		WorkLoad workLoad = new WorkLoad(.9, .99, "Example4.txt");
		ArrayList<Integer> expectedFlowPrio = new ArrayList<>(Arrays.asList(0,1)); // Expected priority values
		ArrayList<Integer> flowPrio = new ArrayList<Integer>(); // Tracks priority values from file in an array
		String[] flowNames = workLoad.getFlowNames();
		
		for(String flowName : flowNames) {
			flowPrio.add(workLoad.getFlowPriority(flowName)); // Adds priority values from file
		}
		assertEquals(expectedFlowPrio, flowPrio, "Priority value was incorect");
	}
	@Test
	@Timeout (value = 1000, unit = TimeUnit.MILLISECONDS)
	public void testGetFlowPriority() throws FileNotFoundException {
		WorkLoad workLoad = new WorkLoad(.9, .99, "LongChain.txt");
		ArrayList<Integer> expectedFlowPrio = new ArrayList<>(Arrays.asList(0)); // Expected priority values
		ArrayList<Integer> flowPrio = new ArrayList<Integer>(); // Tracks priority values from file in an array
		String[] flowNames = workLoad.getFlowNames();
		
		for(String flowName : flowNames) {
			flowPrio.add(workLoad.getFlowPriority(flowName)); // Adds priority values from file
		}
		assertEquals(expectedFlowPrio, flowPrio, "Priority value was incorect");
		
		
	}
	@Test
	@Timeout (value = 1000, unit = TimeUnit.MILLISECONDS)
	public void testSetFlowPriority() throws FileNotFoundException {
		WorkLoad workLoad = new WorkLoad(.9, .99, "Example4.txt");
		Integer expectedPrio = 10; 
		Integer changePriority = 10;// Change to priority number
		workLoad.setFlowPriority("F0", changePriority);
		
		assertEquals(expectedPrio, changePriority, "Priority value was incorect");
		
		
	}
	@Test
	@Timeout (value = 1000, unit = TimeUnit.MILLISECONDS)
	public void testGetFlowTxAttemptsPerLinks() throws FileNotFoundException {
		ArrayList<Integer> expectedTotalTx = new ArrayList<>(Arrays.asList(3,3)); // Expected Tx values
		WorkLoad workLoad = new WorkLoad(.9, .99, "Example4.txt");
		ArrayList<Integer> totalTx = new ArrayList<Integer>(); // Tracks Tx values from file in an array
		
		String[] flowNames = workLoad.getFlowNames(); 
		
			for(String flowName : flowNames) {
				totalTx.add(workLoad.getFlowTxAttemptsPerLink(flowName)); // Adds Tx values from file
			}
			assertEquals(expectedTotalTx, totalTx, "Tx total was incorect");
		
	}
	@Test
	@Timeout (value = 1000, unit = TimeUnit.MILLISECONDS)
	public void testGetFlowTxAttemptsPerLink() throws FileNotFoundException {
		ArrayList<Integer> expectedTotalTx = new ArrayList<>(Arrays.asList(4)); // Expected Tx values
		WorkLoad workLoad = new WorkLoad(.9, .99, "LongChain.txt");
		ArrayList<Integer> totalTx = new ArrayList<Integer>(); // Tracks Tx values from file in an array
		
		String[] flowNames = workLoad.getFlowNames(); 
		
			for(String flowName : flowNames) {
				totalTx.add(workLoad.getFlowTxAttemptsPerLink(flowName)); // Adds Tx values from file
			}
			assertEquals(expectedTotalTx, totalTx, "Tx total was incorect");
	}	
	@Test
	@Timeout (value = 1000, unit = TimeUnit.MILLISECONDS)
	public void testSetFlowsInRMorder() throws FileNotFoundException {
		WorkLoad workLoad = new WorkLoad(.9, .99, "Example2.txt");
		String[] expectedFlows = {"F0", "F2", "F1", "F3", "F4","F5"}; // Expected flows
		workLoad.setFlowsInRMorder();
		
		var expectedFlowNames = workLoad.getFlowNames();
		
		for(int i = 0; i< expectedFlowNames.length; i++) { // Checks all flows, including new one
	  		String currentFlow = expectedFlows[i];
	  		String currentName = expectedFlowNames[i];	
	  		assertEquals(currentFlow, currentName, "Flows were not sorted"); 
		
		
		}
	}
	@Test
	@Timeout (value = 1000, unit = TimeUnit.MILLISECONDS)
	public void testGetNodeNamesOrderedAlphabetically() throws FileNotFoundException {
		String[] fileSort;
		String [] expectedNodes = {"A", "B", "C"};
		WorkLoad workLoad = new WorkLoad(.9, .99,"Example.txt");
		
		for(int i = 0; i< expectedNodes.length; i++) {
			fileSort = workLoad.getNodeNamesOrderedAlphabetically();
			String currentNode = expectedNodes[i];
	  		String nodeCheck = fileSort[i];	
			assertEquals(currentNode, nodeCheck, "Items were not sorted");
		}
	}
}
