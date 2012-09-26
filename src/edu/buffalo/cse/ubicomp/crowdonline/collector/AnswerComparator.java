package edu.buffalo.cse.ubicomp.crowdonline.collector;

import java.util.Comparator;

public class AnswerComparator implements Comparator<Answer>{
 
    @Override
    public int compare(Answer a1, Answer a2) {
        return (a1.getQuestionId() >a2.getQuestionId() ? 1 : (a1.getQuestionId()==a2.getQuestionId() ? 0 : -1));
    }
}
