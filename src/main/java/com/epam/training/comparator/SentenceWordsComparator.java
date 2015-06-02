package com.epam.training.comparator;

import java.util.Comparator;

import com.epam.training.entity.ComponentType;
import com.epam.training.entity.IComponent;

/* 
 * this comparator sorts sentences in the text according to the number of
 * words in them, starting from the least number of words
 */
public class SentenceWordsComparator implements Comparator<IComponent> {

	@Override
	public int compare(IComponent sentence1, IComponent sentence2) {
		return countWordsInSentence(sentence1) - countWordsInSentence(sentence2);
	}

	/* supplementary method that counts words in a sentence */
	private int countWordsInSentence(IComponent sentence) {
		int count = 0;
		for (int i = 0; i < sentence.listOfComponentsSize(); i++) {
			ComponentType type = sentence.getComponent(i).getComponentType();
			if (type.equals(ComponentType.WORD)) {
				count++;
			}
		}
		return count;
	}
}
