package practica7;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;


//import ejer1.EDListGraph.Node;

public class MetroJUnit {

	String fileName = "metro.txt";

	Metro metro = new Metro(fileName);
	
	
	@Test
	public void testReadMetro()
	{
		metro.printInfo();

		assertEquals("numero of lines... ",metro.numberOfLines,10);
		assertEquals("line 1, stations... ",metro.lines[0].numberOfStations,32);
		assertEquals("line 2, stations... ",metro.lines[1].numberOfStations,20);
		assertEquals("line 3, stations... ",metro.lines[2].numberOfStations,18);
		assertEquals("line 4, stations... ",metro.lines[3].numberOfStations,23);
		assertEquals("line 5, stations... ",metro.lines[4].numberOfStations,32);
		assertEquals("line 6, stations... ",metro.lines[5].numberOfStations,29);
		assertEquals("line 7, stations... ",metro.lines[6].numberOfStations,30);
		assertEquals("line 8, stations... ",metro.lines[7].numberOfStations,8);
		assertEquals("line 9, stations... ",metro.lines[8].numberOfStations,29);
		assertEquals("line 10, stations... ",metro.lines[9].numberOfStations,31);
	}   

	@Test
	public void testStations()
	{
		ArrayList<String> list = metro.getStationsList();
		metro.printStations(list);

		assertEquals("numer of stations... ",list.size(),207);
		assertEquals("first station... ",list.get(0),"pinar de chamart�n");
		assertEquals("last station... ",list.get(list.size()-1),"puerta del sur");
		assertEquals("station 50... ",list.get(50),"villaverde alto");
		assertEquals("station 100... ",list.get(100),"pir�mides");
		assertEquals("station 150... ",list.get(150),"valdezarza");
		assertEquals("station 200... ",list.get(200),"lago");
	}
	

}
