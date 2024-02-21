/**
 * 
 */
package edu.uiowa.cs.warp;

import java.util.ArrayList;
import java.io.*;  
import java.util.Scanner;
import java.util.Collections; 

/**
 * Reads the input file, whose name is passed as input parameter to the constructor, and builds a
 * Description object based on the contents. Each line of the file is an entry (string) in the
 * Description object.
 * 
 * @author sgoddard
 * @version 1.4 Fall 2022
 */
public class WorkLoadDescription extends VisualizationObject {

  private static final String EMPTY = "";
  private static final String INPUT_FILE_SUFFIX = ".wld";

  private Description description;
  private String inputGraphString;
  private FileManager fm;
  private String inputFileName;
  private ArrayList<String> inputFileList; 

  WorkLoadDescription(String inputFileName) throws FileNotFoundException {
    super(new FileManager(), EMPTY, INPUT_FILE_SUFFIX); // VisualizationObject constructor
    this.fm = this.getFileManager();
    initialize(inputFileName);
  }

  @Override
  public Description visualization() {
    return description;
  }

  @Override
  public Description fileVisualization() {
    return description;
  }

  // @Override
  // public Description displayVisualization() {
  // return description;
  // }

  @Override
  public String toString() {
    return inputGraphString;
  }

  public String getInputFileName() {
	inputFileName = inputFileName.substring(0, inputFileName.length()-4);
    return inputFileName;
  }
  
  public ArrayList<String> getInputFileList() {
	  return inputFileList;
  }

  private void initialize(String inputFile) throws FileNotFoundException {
    // Get the input graph file name and read its contents
    InputGraphFile gf = new InputGraphFile(fm);
    inputGraphString = gf.readGraphFile(inputFile);
    this.inputFileName = gf.getGraphFileName();
    description = new Description(inputGraphString);
    
    //Set Up Input Graph as ArrayList
    inputFileList = new ArrayList<String>();
    FileInputStream fis = new FileInputStream(inputFile);  
    Scanner sc=new Scanner(fis); 
    sc.nextLine(); //Remove title line
	  while(sc.hasNextLine()) {  
		  inputFileList.add(sc.nextLine());
	  }  
	  sc.close();
	  inputFileList.remove(inputFileList.size()-1); //Remove end bracket line
  } 
  
  public static void main(String[] args) throws FileNotFoundException {
	  WorkLoadDescription stress = new WorkLoadDescription("StressTest.txt");
	  ArrayList<String> fileList = stress.getInputFileList();
	  
	  //Sort alphabetically using collections sort function
	  Collections.sort(fileList);
	  
	  System.out.println(stress.getInputFileName());
	  for (int i = 0; i<fileList.size(); i++) {
		  System.out.println("Flow " + (i+1) + ": " + fileList.get(i));
	  }
  }
}
