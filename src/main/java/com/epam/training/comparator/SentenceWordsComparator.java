package com.epam.training.comparator;

import java.util.Comparator;

import com.epam.training.entity.IComponent;

public class SentenceWordsComparator implements Comparator<IComponent> {

	@Override
	public int compare(IComponent sentence1, IComponent sentence2) {
		return countWordsInSentence(sentence1) - countWordsInSentence(sentence2);
	}

	private int countWordsInSentence(IComponent sentence) {
		int count = 0;
		for (int i = 0; i < sentence.listOfComponentsSize(); i++) {
			String type = sentence.getComponent(i).getComponentType().name();
			if (type.equals("WORD")) {
				count++;
			}
		}
		return count;
	}
}
