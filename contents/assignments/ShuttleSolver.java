package team16410;

import lpsolve.LpSolve;

/**
 * @author ttemple
 * this class encapsulates a method that solves Problem Set 5 #3
 * Has some handy-dandy Matlab output.
 */
public class ShuttleSolver {
	
	public static void main(String[] args) throws Exception
	{
		
		// Initial condition
		double X_0[];/*** you fill in here ***/
		// Goal condition
		double X_G[];/*** you fill in here ***/
				
		// Period of the orbit
		double T = 90*60;
		// Frequency of the orbit
		double w = 2*Math.PI/T;
		
		// Solve the docking problem for each step size.

		for (//*** various step sizes ***//)
		{
			// Time step
			double dt = T/stepCountPerPeriod;
			
			// A matrix of x(t + 1) = Ax(t) + Bu(t)
			double A[][];
			/*** you fill in here ***/
			
			// B matrix of x(t + 1) = Ax(t) + Bu(t)
			double B[][];
			/*** You fill in here ***/
			
			results[stepCountPerPeriod] = solveDockingProblem(A,B,X_0,X_G,stepCountPerPeriod + 2);
		}
		
		printResults(results);
	}
	
	/**
	 * Object for storing the results.
	 * @author chung
	 */
	static class Results
	{
		public double 		solutionTime;
		public double 		optimalCost;
		public int 			variableCount;
		/**
		 * @param X  the sequence of states
		 */
		public double[][]	X;
		/**
		 * @param U  the sequence of inputs
		 */
		public double[][]	U;
	}
	
	/**
	 * Solve the spacecraft docking problem for the given step size.
	 * This method does not convert variables with all real domain to
	 * variables with positive real domain.
	 * 
	 * @param A						A matrix of x(t+1) = Ax(t) + Bu(t)
	 * @param B						B matrix of x(t+1) = Ax(t) + Bu(t)
	 * @param X_0					Initial state
	 * @param X_G					Goal state
	 * @param stepCount	Number of steps per period.
	 * @return						Result of the LP problem
	 * @throws Exception
	 */
	protected static Results solveDockingProblem(double[][] A, double[][] B, double[] X_0, double[] X_G, int stepCount) throws Exception
	{
		/*** You fill in here ***/
	}
	
	protected static void printResults(Results[] results) 
	{
		int minStepCountPerPeriod = 0;
		for (int i = 0; i < results.length; i++)
		{
			if (results[i] != null)
			{
				minStepCountPerPeriod = i;
				break;
			}
		}
		
		///////////////////////////////////////////////////////////////
		// Print the result in a Matlab format
		///////////////////////////////////////////////////////////////
		System.out.println("clear all;");
		System.out.println("close all;");
		// Print step size.
		System.out.print("stepCountPerPeriod = [");
		for (int i = minStepCountPerPeriod; i < results.length; i++)
		{
				System.out.print(i + " ");
		}
		System.out.println("];");
		// Print solution time.
		System.out.print("solutionTime = [");
		for (int i = minStepCountPerPeriod; i < results.length; i++)
		{
				System.out.print(results[i].solutionTime + " ");
		}
		System.out.println("];");
		// Print optimal cost.
		System.out.print("optimalCost = [");
		for (int i = minStepCountPerPeriod; i < results.length; i++)
		{
			System.out.print(results[i].optimalCost + " ");
		}
		System.out.println("];");
		// Print number of design variables.
		System.out.print("variableCount = [");
		for (int i = minStepCountPerPeriod; i < results.length; i++)
		{
			System.out.print(results[i].variableCount + " ");
		}
		System.out.println("];");
		
		
		// Print X and DeltaV for two step sizes.		
		int resultIndicies[] = new int[]{minStepCountPerPeriod,results.length - 1};
		for (int i = 0; i < resultIndicies.length; i++)
		{
			int stepCountPerPeriod = resultIndicies[i];
			System.out.print("X6_1{" + (i + 1) + "} = [");
			for (int j = 0; j < results[stepCountPerPeriod].X.length; j++)
			{
				System.out.print("\n");
				for (int row = 0; row < results[stepCountPerPeriod].X[j].length; row++)
				{
					System.out.print(" " + results[stepCountPerPeriod].X[j][row]);
				}
			}
			System.out.println("]';");
			System.out.print("U{" + (i + 1) + "} = [");
			for (int j = 0; j < results[stepCountPerPeriod].U.length; j++)
			{
				System.out.print("\n");
				for (int row = 0; row < results[stepCountPerPeriod].U[j].length; row++)
				{
					System.out.print(" " + results[stepCountPerPeriod].U[j][row]);
				}
			}
			System.out.println("]';");
		}
		// Print plot for the performance vs. step size.
		System.out.println("figure;");
		System.out.println("subplot(3,1,1);");
		System.out.println("plot(stepCountPerPeriod,solutionTime,'-o');");
		System.out.println("ylabel('Time');");
		System.out.println("subplot(3,1,2);");
		System.out.println("plot(stepCountPerPeriod,optimalCost,'-o');");
		System.out.println("ylabel('J');");
		System.out.println("subplot(3,1,3);");
		System.out.println("plot(stepCountPerPeriod,variableCount,'-o');");
		System.out.println("ylabel('|U|');");
		System.out.println("xlabel('Number of Steps per Period');");
		// Print trajectories for the two step sizes.
		System.out.println("figure;");
		System.out.print("plot3(");
		for (int i = 1; i <= resultIndicies.length; i++)
		{
			System.out.print("X6_1{" + i + "}(1,:),X6_1{" + i + "}(2,:),X6_1{" + i + "}(3,:),'-o'");
			if (i != resultIndicies.length)
			{
				System.out.print(",");
			}
		}
		System.out.println(");");
		System.out.print("legend(");
		for (int i = 1; i <= resultIndicies.length; i++)
		{
			System.out.print("'Step Size: " + resultIndicies[i - 1] + "',");
		}
		System.out.println("'Location','NorthWest');");
		System.out.println("xlabel('X_x');");
		System.out.println("ylabel('X_y');");
		System.out.println("zlabel('X_z');");
		System.out.println("grid on;");
		System.out.println("axis equal;");
		System.out.println("axis([-1 1 -4 0 -1 1]);");
	}
}

