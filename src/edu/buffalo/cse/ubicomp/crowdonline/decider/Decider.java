package edu.buffalo.cse.ubicomp.crowdonline.decider;

import edu.buffalo.cse.ubicomp.crowdonline.asker.Question;

public abstract class Decider {
	abstract public char decide(Question q);
}
