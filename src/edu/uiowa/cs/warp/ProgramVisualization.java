/**
 * 
 */
package edu.uiowa.cs.warp;

/**
 * Takes the current program being used and converts it to
 * state of the art WARP technology. After the data is pushed through
 * and converted, the user is able to see within the new visualization.
 * 
 * 
 * 
 * @author sgoddard
 * @version 1.5
 * 
 */
public class ProgramVisualization extends VisualizationObject {

  private static final String SOURCE_SUFFIX = ".dsl";
  private ProgramSchedule sourceCode;
  private Program program;
  private Boolean deadlinesMet;

  /**
   * Takes the file, checks deadines, the schedule, and converts into WARP.
   * 
   * @param warp 
   */
ProgramVisualization(WarpInterface warp) {
    super(new FileManager(), warp, SOURCE_SUFFIX);
    this.program = warp.toProgram();
    this.sourceCode = program.getSchedule();
    this.deadlinesMet = warp.deadlinesMet();
  }

  /**
 * Returns a visual by creating a template, header, and current data.
 */
@Override
  public GuiVisualization displayVisualization() {
    return new GuiVisualization(createTitle(), createColumnHeader(), createVisualizationData());
  }

  /**
 * Creates a header.
 */
@Override
  protected Description createHeader() {
    Description header = new Description();

    header.add(createTitle());
    header.add(String.format("Scheduler Name: %s\n", program.getSchedulerName()));

    /* The following parameters are output based on a special schedule or the fault model */
    if (program.getNumFaults() > 0) { // only specify when deterministic fault model is assumed
      header.add(String.format("numFaults: %d\n", program.getNumFaults()));
    }
    header.add(String.format("M: %s\n", String.valueOf(program.getMinPacketReceptionRate())));
    header.add(String.format("E2E: %s\n", String.valueOf(program.getE2e())));
    header.add(String.format("nChannels: %d\n", program.getNumChannels()));
    return header;
  }

  /**
 * Creates a footer.
 */
@Override
  protected Description createFooter() {
    Description footer = new Description();
    String deadlineMsg = null;

    if (deadlinesMet) {
      deadlineMsg = "All flows meet their deadlines\n";
    } else {
      deadlineMsg = "WARNING: NOT all flows meet their deadlines. See deadline analysis report.\n";
    }
    footer.add(String.format("// %s", deadlineMsg));
    return footer;
  }


  /**
 * Creates a header for the workload.
 */
@Override
  protected String[] createColumnHeader() {
    var orderedNodes = program.toWorkLoad().getNodeNamesOrderedAlphabetically();
    String[] columnNames = new String[orderedNodes.length + 1];
    columnNames[0] = "Time Slot"; // add the Time Slot column header first
    /* loop through the node names, adding each to the header */
    for (int i = 0; i < orderedNodes.length; i++) {
      columnNames[i + 1] = orderedNodes[i];
    }
    return columnNames;
  }

  /**
 * Creates the rows and columns needed for visualize the data
 * then returns the formatted data.
 */
@Override
  protected String[][] createVisualizationData() {
    if (visualizationData == null) {
      int numRows = sourceCode.getNumRows();
      int numColumns = sourceCode.getNumColumns();
      visualizationData = new String[numRows][numColumns + 1];

      for (int row = 0; row < numRows; row++) {
        visualizationData[row][0] = String.format("%s", row);
        for (int column = 0; column < numColumns; column++) {
          visualizationData[row][column + 1] = sourceCode.get(row, column);
        }
      }
    }
    return visualizationData;
  }

  /**
   * Returns string of programs name.
   * 
 * @return name as a string
 */
private String createTitle() {
    return String.format("WARP program for graph %s\n", program.getName());
  }
}
