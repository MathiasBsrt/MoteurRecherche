package testconsole;

import controleur.ControlIndexation;
import vueconsole.BoundaryIndexer;

public class TestIndexation {
	public static void main(String[] args)
	{
		ControlIndexation controlIndexation=new ControlIndexation();
		BoundaryIndexer boundaryIndexer=new BoundaryIndexer(controlIndexation);
		boundaryIndexer.lancerIndexation();
	}
}
