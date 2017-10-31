package practica7;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Metro
{
	
	protected class LineInfo
	{
		protected String id;
		protected String color;
		int numberOfStations;
		String [] stations;
	}

	String fileName;
	int numberOfLines;
	LineInfo [] lines;
	
	public Metro(String fileName)
	{
		this.fileName = fileName;
		readLinesAndStatons(fileName);
	}

	//Lee el fichero con la informacion de las lineas de metro
	private void readLinesAndStatons(String fileName)
	{
		try 
		{ 
			Scanner inf = new Scanner (new FileInputStream(fileName));
			
			numberOfLines = inf.nextInt();
			lines = new LineInfo[numberOfLines];
			
		
			for (int i=0; i<numberOfLines;i++){
				lines[i]=new LineInfo();
				lines[i].id=inf.next();
				lines[i].color=inf.next();
				lines[i].numberOfStations=inf.nextInt();
				inf.nextLine(); //Salto de linea
				lines[i].stations=new String[lines[i].numberOfStations];
				for (int j=0; j<lines[i].numberOfStations;j++){
					lines[i].stations[j]=inf.nextLine();
				}
			}
			
			inf.close();
		}
		catch(FileNotFoundException e)
		{
			System.out.println("Error opening file");
			System.exit(0);
		}
	}

	//devuleve lista de estaciones de metro sin repetir ninguna
	public ArrayList<String> getStationsList()
	{
		ArrayList<String> list = new ArrayList<String>();
		
		for (int i=0; i<numberOfLines; i++){
			for (int j=0; j<lines[i].numberOfStations;j++){
				if(!list.contains(lines[i].stations[j]))
					list.add(lines[i].stations[j]);
			}
		}
		return list;
	}

	//Devuelve el numero de lineas de metro
	public int getNumberOfLines()
	{
		return numberOfLines;
	}
	
	//Escribe informacion completa del metro
	public void printInfo()
	{
		for (int i=0; i<numberOfLines; i++)
		{
			printLineInfo(i);
			System.out.println();
		}
	}
	
	//Escribe el identificador, color, numero de estaciones y lista de estaciones
	public void printLineInfo(int lineNumber)
	{
		if (lineNumber>=0 && lineNumber<numberOfLines)
		{
			System.out.println("line id: " + lines[lineNumber].id);
			System.out.println("line color: " + lines[lineNumber].color);
			System.out.println("line numer of stations: " + lines[lineNumber].numberOfStations);
			
			for (int j=0; j<lines[lineNumber].numberOfStations; j++)
			{
				System.out.println("station: " + lines[lineNumber].stations[j]);
			}
		}
	}

	//Escribe la lista de estaciones
	public void printStations(ArrayList<String> list)
	{
		System.out.println("List of stations (" + list.size() + "):");
		for (String str : list) System.out.println(str);
		System.out.println();
	}
	
}
