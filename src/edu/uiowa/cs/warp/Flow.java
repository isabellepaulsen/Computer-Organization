package edu.uiowa.cs.warp;

import java.util.ArrayList;

/**
 * A Flow is a list of Nodes with Edges connecting them, coming from the Graph file. It begins 
 * with a source Node and is a path through the Nodes and Edges that are saved in Flow, 
 * ending at a sink Node. It's index is the order in which the node was read from the graph. 
 * And it has a value determining its priority.
 * 
 * @author sgoddard
 *
 */
public class Flow extends SchedulableObject implements Comparable<Flow>{

	private static final Integer UNDEFINED = -1;
	private static final Integer DEFAULT_FAULTS_TOLERATED = 0; 
	private static final Integer DEFAULT_INDEX = 0;
	private static final Integer DEFAULT_PERIOD = 100; 
	private static final Integer DEFAULT_DEADLINE = 100;
	private static final Integer DEFAULT_PHASE = 0;
	

    Integer initialPriority = UNDEFINED;
    Integer index;  // order in which the node was read from the Graph file
    Integer numTxPerLink; //  determined by fault model
    ArrayList<Node> nodes; // Flow src is 1st element and flow snk is last element in array
    /*
     *  nTx needed for each link to reach E2E reliability target. Indexed by src node of the link. 
     *  Last entry is total worst-case E2E Tx cost for schedulability analysis
     */
    ArrayList<Integer> linkTxAndTotalCost; 
    ArrayList<Edge> edges; //used in Partition and scheduling
    Node nodePredecessor;
    Edge edgePredecessor;
    
    /*
     * Constructor that sets name, priority, and index
     */
    /**
     * Constructor that sets name, priority, and index of Flow.
     * @param name  name of Flow
     * @param priority  determines order visited
     * @param index  order of which the node was read from the Graph file
     */
    Flow (String name, Integer priority, Integer index){
    	super(name, priority, DEFAULT_PERIOD, DEFAULT_DEADLINE, DEFAULT_PHASE);
    	this.index = index;
        /*
         *  Default numTxPerLink is 1 transmission per link. Will be updated based
         *  on flow updated based on flow length and reliability parameters
         */
        this.numTxPerLink = DEFAULT_FAULTS_TOLERATED + 1; 
        this.nodes = new ArrayList<>();
        this.edges  = new ArrayList<>();
        this.linkTxAndTotalCost = new ArrayList<>();
        this.edges = new ArrayList<>();	
        this.nodePredecessor = null;
        this.edgePredecessor = null;
    }
    
    /*
     * Constructor
     */
    /**
     * Constructor that sets index to default index,
     * increments numTxPerLink, sets nodes, linkTxAndTotalCost,
     * and edges to empty arraylists, and sets nodePredecessor and edgePredecessor to null.
     * Default numTxPerLink is 1 transmission per link. It will be updated based on flow
     * length and reliability parameters.  
     */
    Flow () {
    	super();
    	this.index = DEFAULT_INDEX;
    	/*
    	 *  Default numTxPerLink is 1 transmission per link. Will be updated based
    	 *  on flow updated based on flow length and reliability parameters
    	 */
    	this.numTxPerLink = DEFAULT_FAULTS_TOLERATED + 1; 
    	this.nodes = new ArrayList<>();
    	this.linkTxAndTotalCost = new ArrayList<>();
    	this.edges = new ArrayList<>();
    	this.nodePredecessor = null;
        this.edgePredecessor = null;
    }

	/**
	 * Returns the initial priority of the Flow.
	 * 
	 * @return the initialPriority
	 */
	public Integer getInitialPriority() {
		return initialPriority;
	}

	/**
	 * Returns the index of the Flow.
	 * 
	 * @return the index
	 */
	public Integer getIndex() {
		return index;
	}

	/**
	 * Returns the numTxPerLink, determined by fault model, of the Flow.
	 * 
	 * @return the numTxPerLink
	 */
	public Integer getNumTxPerLink() {
		return numTxPerLink;
	}

	/**
	 * Returns the nodes of the Flow.
	 * 
	 * @return the nodes
	 */
	public ArrayList<Node> getNodes() {
		return nodes;
	}
	
	/**
	 * Returns the edges of the Flow
	 * 
	 * @return the edges
	 */
	public ArrayList<Edge> getEdges() {
		return edges;
	}

	/**
	 * Adds an edge to the Flow.
	 * 
	 * @param edge edge to add to the Flow.
	 */
	public void addEdge(Edge edge) {
		/* set predecessor and add edge to flow */
		edge.setPredecessor(edgePredecessor);
		edges.add(edge);
		/* update predecessor for next edge added */
		edgePredecessor = edge;
	}
	
	/**
	 * Adds a node to the Flow.
	 * 
	 * @param node node to add to the Flow.
	 */
	public void addNode(Node node) {
		/* set predecessor and add edge to flow */
		node.setPredecessor(nodePredecessor);
		nodes.add(node);
		/* update predecessor for next edge added */
		nodePredecessor = node;
	}
	/**
	 * Returns the linkTxAndTotalCost of the Flow
	 * 
	 * @return the linkTxAndTotalCost
	 */
	public ArrayList<Integer> getLinkTxAndTotalCost() {
		return linkTxAndTotalCost;
	}

	/**
	 * Sets the initialPriority of the Flow.
	 * 
	 * @param initialPriority the initialPriority to set
	 */
	public void setInitialPriority(Integer initialPriority) {
		this.initialPriority = initialPriority;
	}

	/**
	 * Sets the index of the Flow.
	 * 
	 * @param index the index to set
	 */
	public void setIndex(Integer index) {
		this.index = index;
	}

	/**
	 * Sets the numTxPerLink of the Flow.
	 * 
	 * @param numTxPerLink the numTxPerLink to set
	 */
	public void setNumTxPerLink(Integer numTxPerLink) {
		this.numTxPerLink = numTxPerLink;
	}

	/**
	 * Sets the nodes of the Flow.
	 * 
	 * @param nodes the nodes to set
	 */
	public void setNodes(ArrayList<Node> nodes) {
		this.nodes = nodes;
	}

	/**
	 * Sets the linkTxAndTotalCost of the Flow. 
	 * 
	 * @param linkTxAndTotalCost the linkTxAndTotalCost to set
	 */
	public void setLinkTxAndTotalCost(ArrayList<Integer> linkTxAndTotalCost) {
		this.linkTxAndTotalCost = linkTxAndTotalCost;
	}
	
	/**
	 * Compares two flows to see which has the higher priority. If the input flow
	 * has the higher priority, it returns -1. If the other flow does, it returns 1.
	 * 
	 * @param flow the flow to compare
	 * @return integer -1 or 1 depending on which flow has a higher priority
	 */
	@Override
    public int compareTo(Flow flow) {
    	// ascending order (0 is highest priority)
        return flow.getPriority() > this.getPriority() ? -1 : 1;
    }
    
	/**
	 * Returns the Flow's name as a String.
	 * 
	 * @return name
	 */
    @Override
    public String toString() {
        return getName();
    }
    
}
