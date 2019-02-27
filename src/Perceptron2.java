import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class Perceptron2 {
	
	private static File train = new File("training.txt");
	private static File test = new File("test.txt");
	private static ArrayList<String[]> trainList = new ArrayList<>();
	private static ArrayList<String[]> testList = new ArrayList<>();
	private static double threshold = 1;
	private static ArrayList<Double> weights = new ArrayList<>();
	private static double alfa = 0.5; 
	private static int iterations = 200; 
	private static double thresholdError = 4;
	//private static double globalerror = 0;
	
	
	public static void fillWeights(){
		for(int i =0; i < 4; i++){
			weights.add(1.0);
		}
	}
	
	public static void extract() throws IOException{
		BufferedReader brTrain = null;
		BufferedReader brTest = null;
	
		try {
			brTrain = new BufferedReader(new FileReader(train));
			brTest = new BufferedReader(new FileReader(test));
			
			String currentLineTest;
			String currentLineTrain;
			
			while ((currentLineTrain = brTrain.readLine()) != null) {
				String[] iris = currentLineTrain.split(",");
				trainList.add(iris);
			}
			while((currentLineTest = brTest.readLine()) != null){
				String[] testIris = currentLineTest.split(",");
				testList.add(testIris);
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
			
	}
	

	
	public static int Output(String[] iris){
		 int output = -1;
		 if(iris[4].equals("Iris-virginica")) output = 1;
		 if(iris[4].equals("Iris-versicolor")) output = 0;
		 return output;
	}
	
	public static void train(){
		int iteration = 0;
		double globalError = 0;
		double result = 0;
		ArrayList<Double> inputs = new ArrayList<>();
		
		do {
			globalError = 0;
			iteration++;
		
			for (int i = 0; i < trainList.size(); i++) {
				//get iris
				String[] iris = trainList.get(i);
				//calculate inputs*weights result
				
				double localerror = modify(iris);
				
				globalError = globalError + localerror;
				//System.out.println("globalError" + globalError);
			
			}
			//globalError++;
			System.out.println("globalError" + globalError);
		} while (globalError >= thresholdError && iteration<=iterations);
		
	}
	
	public static double modify(String[] iris){

		ArrayList<Double> inputs = new ArrayList<>();
		double result = 0;
		double localerror = 0;
		for(int i=0; i<4; i++){
			String inputString = iris[i];
			double input = Double.parseDouble(inputString);
			result = result + input * weights.get(i);
			inputs.add(input);
		}
		if(iris[4].equals("Iris-virginica")){
			if(result > threshold ){
				//System.out.println("DONE");
			}
			else{
				for(int i = 0; i < weights.size(); i++){
					double weight = weights.get(i);
					weight = weight + (1-0)*alfa*inputs.get(i);
					weights.set(i, weight);
				}
				threshold = threshold + alfa*(0-1);
				localerror++;
				//modify(iris); 
			}
		}
		if(iris[4].equals("Iris-versicolor")){
			if(result < threshold ){
				//System.out.println("DONE");
			}
			else{
				for(int i = 0; i < weights.size(); i++){
					double weight = weights.get(i);
					weight = weight + (0-1)*alfa*inputs.get(i);
					weights.set(i, weight);
				}
				threshold = threshold + alfa*(1-0);
				localerror++;
				//modify(iris); 
			}
		}
		return localerror;
	}
	
	public static void test(String[] iris){
		ArrayList<Double> inputs = new ArrayList<>();
		double result = 0;
		String predicted = "";
		for(int i=0; i<4; i++){
			String inputString = iris[i];
			double input = Double.parseDouble(inputString);
			result = result + input * weights.get(i);
			inputs.add(input);
		}
		if(result > threshold){
			predicted = "Iris-virginica";
		}
		else{
			predicted = "Iris-versicolor";
		}
		System.out.println("Actual: " + iris[4] + " Predicted: " + predicted );
		if(iris[4].equals(predicted)){
			System.out.println("CORRECT");
		}
		else{
			System.err.println("WRONG");
		}
	}
	
	public static void main(String[] args) throws IOException{
		extract();
		fillWeights();
		
		try{
			String enterthreshold = JOptionPane.showInputDialog(null, 
					"Please, enter threshold value");
			threshold = Double.parseDouble(enterthreshold);
			String enterAlfa = JOptionPane.showInputDialog(null, 
					"Please, enter alfa value");
			alfa = Double.parseDouble(enterAlfa);
			String enteritter = JOptionPane.showInputDialog(null, 
					"Please, enter iterations value");
			iterations = Integer.parseInt(enteritter);
			String entererrorthres = JOptionPane.showInputDialog(null, 
					"Please, enter error threshold value");
			thresholdError = Integer.parseInt(entererrorthres);
			}catch(Exception e){
				
			}
		train();
		for(int i=0; i < testList.size(); i++){
			test(testList.get(i));
			
		}
		
		while(true){
			try{
			String enterIris = JOptionPane.showInputDialog(null, 
				"Please, enter your Iris values and name separeted by comas");
			String[] enterIrisNums = enterIris.split(",");
			test(enterIrisNums);
			}catch(Exception e){
				break;
			}
		}
	}

}
