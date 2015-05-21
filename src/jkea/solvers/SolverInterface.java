package jkea.solvers;

public interface SolverInterface {

	long rank();
	long enumerateParallel(int nProcessors);
	long enumerate();

}
