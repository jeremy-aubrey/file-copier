//********************************************************************
//
//  Author:        Jeremy Aubrey
//
//  File Name:     FileCopier.java
//
//  Description:   A program that copies a the contents of one file to a destination
//                 file. The program prompts a user for the paths of the source and
//                 destination files.
//
//********************************************************************

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class FileCopier
{
	
    private Scanner userInput;
	private String errMsg = "";
	
    //***************************************************************
    //
    //  Method:       main
    // 
    //  Description:  The main method of the project
    //
    //  Parameters:   String array
    //
    //  Returns:      N/A 
    //
    //**************************************************************
	public static void main(String[] args)
	{
		// Create an object of the main class and use it to call
		// the non-static developerInfo and other non-static methods
		FileCopier copier = new FileCopier();
		copier.developerInfo();
		
		try {
			copier.copyFile();
		} catch(Exception e) {
			System.out.println("\nError copying file: " + e.getMessage());
		} finally {
			System.out.println("\n[ EXITING PROGRAM ]");
		}
		
	} // End of the main method
	
	//***************************************************************
    //
    //  Method:       developerInfo (Non Static)
    // 
    //  Description:  The developer information method of the program.
    //
    //  Parameters:   None
    //
    //  Returns:      N/A 
    //
    //**************************************************************
    public void developerInfo()
    {
       System.out.println("Name:    Jeremy Aubrey");
       System.out.println("Course:  COSC 4302 Operating Systems");
       System.out.println("Program: 1");

    } // End of the developerInfo method 
    
	//***************************************************************
    //
    //  Method:       setErrorMsg (Non Static)
    // 
    //  Description:  Sets an error message to be reported to the user.
    //
    //  Parameters:   String
    //
    //  Returns:      N/A 
    //
    //**************************************************************
	private void setErrorMsg(String msg) {
		
		errMsg = msg;
		
	}//End of the setErrorMsg method
	
	//***************************************************************
    //
    //  Method:       getInput (Non Static)
    // 
    //  Description:  Prompts user for input.
    //
    //  Parameters:   None
    //
    //  Returns:      String 
    //
    //**************************************************************
	private String getInput() {

		String input = userInput.nextLine().trim();
		return input;
		
	};//End of the getInput method

	//***************************************************************
    //
    //  Method:       isReadablePath (Non Static)
    // 
    //  Description:  Validates that a file is readable or updates the
    //                error message if not.
    //
    //  Parameters:   String
    //
    //  Returns:      boolean 
    //
    //**************************************************************
	private boolean isReadablePath(String path) {
		
		boolean isReadable = false;
		
		//if legal path, verify that it exists and is readable or update error message
		try {
			Path srcPath = Paths.get(path);
			boolean readAllowed = Files.isReadable(srcPath);
			boolean notExists = Files.notExists(srcPath);
			boolean isDirectory = Files.isDirectory(srcPath);
			if(isDirectory) {
				setErrorMsg("File is a directory");
			} else if(!notExists && !readAllowed) {
				setErrorMsg("File is not readable (missing read permissions)");
			} else if(notExists) {
				setErrorMsg("File does not exist");
			} else if(!notExists && readAllowed) {
				isReadable = true;
			}
		} catch (Exception e) {
			setErrorMsg("Invalid file path");
		}
		
		return isReadable;
		
	};//End of the isReadablePath method
	
	//***************************************************************
    //
    //  Method:       getReadableFile (Non Static)
    // 
    //  Description:  Attempts to get a readable file path from the user
    //                until successful or user chooses to quit.
    //                Reports the error if unsuccessful.
    //
    //  Parameters:   None
    //
    //  Returns:      File 
    //
    //**************************************************************
	private File getReadableFile() {
		
		boolean exit = false;
		File readable = null;
		
		//until the user decides to quit
		while(!exit) {
			//get file path
			System.out.println("\nEnter the source file path: ");
			String path = getInput();
			
			//instantiate File if valid, retry, or quit
			if(isReadablePath(path)) {
				readable = new File(path);
				exit = true;
			} else {
				System.out.println("-------------------------------------------------");
				System.out.println("Error reading source file path:");
				System.out.println("> " + errMsg);
				System.out.println("-------------------------------------------------");
				exit = quitProcess();
			}
		}
		
		return readable;
		
	};//End of the getReadableFile method
	
	//***************************************************************
    //
    //  Method:       isWritable (Non Static)
    // 
    //  Description:  Validates that a file is writable or updates the 
    //                error message if not.
    //
    //  Parameters:   String
    //
    //  Returns:      boolean 
    //
    //**************************************************************
	private boolean isWritablePath(String path) {
		
		boolean isWritable = false;
		
		//if legal path, verify that it is writable or update error message
		try {
			Path destPath = Paths.get(path);
			boolean writeAllowed = Files.isWritable(destPath);
			boolean notExists = Files.notExists(destPath);
			boolean isDirectory = Files.isDirectory(destPath);
			if(isDirectory) {
				setErrorMsg("File is a directory");
			} else if(!notExists && !writeAllowed) {
				setErrorMsg("File is not writable (missing write permissions)");
			} else if(notExists || (!notExists && writeAllowed)) {
				isWritable = true;
			}
		} catch (Exception e) {
			setErrorMsg("Invalid file path");
		}

		return isWritable;
		
	}//End of the isWritablePath method
	
	//***************************************************************
    //
    //  Method:       getWritableFile (Non Static)
    // 
    //  Description:  Attempts to get a writable file path from the user
    //                until successful or user chooses to quit.
    //                Reports the error if unsuccessful.
    //
    //  Parameters:   None
    //
    //  Returns:      File
    //
    //**************************************************************
	private File getWritableFile() {
		
		boolean exit = false;
		File writable = null;
		
		//until the user decides to quit
		while(!exit) {
			//get file path
			System.out.println("\nEnter the destination file path: ");
			String path = getInput();
			
			//instantiate File if valid, retry, or quit
			if(isWritablePath(path)) {
				writable = new File(path);
				exit = true;
			} else {
				System.out.println("-------------------------------------------------");
				System.out.println("Error reading destination file path:");
				System.out.println("> " + errMsg);
				System.out.println("-------------------------------------------------");
				exit = quitProcess();
			}
		}
		
		return writable;
		
	};//End of the getWritableFile method
	
	//***************************************************************
    //
    //  Method:       quitProcess (Non Static)
    // 
    //  Description:  Prompts the user to continue or quit an input process.
    //
    //  Parameters:   None
    //
    //  Returns:      boolean
    //
    //**************************************************************
	private boolean quitProcess() {
		
		boolean quit = false;
		boolean isValidSelection = false;
		String selection = "";
		
		//prompt user for a valid selection of 'c' or 'q'
		while(!isValidSelection) {
			System.out.println("\nEnter 'c' to try again or 'q' to quit");
			selection = getInput().trim().toLowerCase();
			if (selection.equals("q")) {
				isValidSelection = true;
				quit = true;
			} else if(selection.equals("c")) {
				isValidSelection = true;
			}
		}
		
		return quit;
		
	};//End of the quitProcess method
	
	//***************************************************************
    //
    //  Method:       getFileContents (Non Static)
    // 
    //  Description:  Retrieves the content of a file.
    //
    //  Parameters:   File
    //
    //  Returns:      String
    //
    //**************************************************************
	private String getFileContents(File src) {
		
		String content = "";
		Scanner fileScan;
		
		try {
			//copy entire contents of source file
			fileScan = new Scanner(src);
			while(fileScan.hasNextLine()) {
				content = content.concat(fileScan.nextLine() + "\n");
			}

			fileScan.close();

		} catch (FileNotFoundException e) {
			setErrorMsg("File not found");
		}
	
		return content;
		
	}//End of the getFileContents method
	
	//***************************************************************
    //
    //  Method:       copyFile (Non Static)
    // 
    //  Description:  Copies the contents of a readable file to a writable
    //                file. Actions performed can be either write, overwrite,
    //                or append. Ensures scanner is opened and closed as needed.
    //
    //  Parameters:   None
    //
    //  Returns:      N/A
    //
    //**************************************************************
	public void copyFile() {
		
		File srcFile = null;
		File destFile = null;
		userInput = new Scanner(System.in);
		
		//get readable file
		srcFile = getReadableFile(); 
		
		//get writable file only if readable source file exists
		if(srcFile != null) {
			destFile = getWritableFile();
		}
		
		//copy contents only if source and destination are valid
		if(srcFile != null && destFile != null) {
			System.out.println("\n[ READY ]");
			try {
				FileWriter writer = null;
				
				//if destination file exists, must append or overwrite 
				if(destFile.exists()) {
					//get user choice (append or overwrite) & execute
					String action = appendOrOverwrite();
					if(action.equals("a")) {
						//append
						writer = new FileWriter(destFile, true);
						writer.append(getFileContents(srcFile));
						System.out.println("\n[ APPENDING ]");
						System.out.println("-------------------------------------------------");
						System.out.println(srcFile.getName() + " >> " + destFile.getName());
						System.out.println("-------------------------------------------------");
					} else if(action.equals("o")) {
						//overwrite
						writer = new FileWriter(destFile);
						writer.write(getFileContents(srcFile));
						System.out.println("\n[ OVERWRITING ]");
						System.out.println("-------------------------------------------------");
						System.out.println(srcFile.getName() + " >> " + destFile.getName());
						System.out.println("-------------------------------------------------");
					}
					System.out.println("\n[ COMPLETE ]");
					System.out.println("-------------------------------------------------");
					System.out.println("> " + destFile.getAbsolutePath());
					System.out.println("-------------------------------------------------");

				//destination file does not exist, write file
				} else {
					writer = new FileWriter(destFile);
					System.out.println("\n[ COPYING ]");
					System.out.println("-------------------------------------------------");
					System.out.println(srcFile.getName() + " >> " + destFile.getName());
					System.out.println("-------------------------------------------------");
					writer.write(getFileContents(srcFile));
					System.out.println("\n[ COMPLETE ]");
					System.out.println("-------------------------------------------------");
					System.out.println("> " + destFile.getAbsolutePath());
					System.out.println("-------------------------------------------------");
				}
				
				//close resources (file writer) if necessary
				if(writer != null) {
					writer.close();
				}
			
			//to catch any exceptions thrown by FileWriter
			} catch (IOException e) {
				System.out.println("-------------------------------------------------");
				System.out.println("Error copying the file");
				System.out.println("> " + e.getMessage());
				System.out.println("-------------------------------------------------");
			}
			
			userInput.close();
		}
		
	}//End of the copyFile method
	
	//***************************************************************
    //
    //  Method:       appendOrOverwite (Non Static)
    // 
    //  Description:  Prompts the user to select append to or overwrite 
    //                a file. 
    //
    //  Parameters:   None
    //
    //  Returns:      String
    //
    //**************************************************************
	private String appendOrOverwrite() {
		
		boolean isValidSelection = false;
		String selection = "";
		
		System.out.println("-----------------------------------------------------------");
		System.out.println("File already exists, would you like to append or overwrite?");
		System.out.println("-----------------------------------------------------------");
		
		while(!isValidSelection) {
			System.out.println("Enter 'a' to append or 'o' to overwrite");
			selection = getInput().trim().toLowerCase();
			if(selection.equals("a") || selection.equals("o")) {
				isValidSelection = true;
			} 
		}
		
		return selection;
		
	}//End of the appendOrOverwrite method
	
}//End of the FileCopier class