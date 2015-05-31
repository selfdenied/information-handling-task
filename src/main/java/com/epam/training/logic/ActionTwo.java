package com.epam.training.logic;

import java.util.ArrayList;
import java.util.List;

import com.epam.training.entity.ComponentType;
import com.epam.training.entity.IComponent;
import com.epam.training.handler.HandlingChain;

public class ActionTwo {
	private HandlingChain chain = new HandlingChain();

	public List<String> removeMaxSubstringFromSentences(IComponent text,
			char startChar, char endChar) {
		List<String> listOfModifiedSentences = new ArrayList<String>();
		List<IComponent> listOfSentences = chain.formListOfComponents(text,
				ComponentType.SENTENCE);

		for (IComponent sentence : listOfSentences) {
			listOfModifiedSentences.add(returnStringWithGivenSubstringRemoved(
					sentence.toString(), startChar, endChar));
		}
		return listOfModifiedSentences;
	}

	private String returnStringWithGivenSubstringRemoved(String sentence,
			char startChar, char endChar) {
		StringBuilder builder = new StringBuilder(sentence);

		if (checkIfCharsArePresent(sentence, startChar, endChar)
				&& checkCharsPosition(sentence, startChar, endChar)) {
			int startIndex = sentence.indexOf(startChar);
			int endIndex = sentence.lastIndexOf(endChar);
			return builder.delete(startIndex, endIndex + 1).toString();
		} else {
			return sentence;
		}
	}

	private boolean checkIfCharsArePresent(String sentence, char startChar,
			char endChar) {
		String start = String.valueOf(startChar);
		String end = String.valueOf(endChar);
		boolean present = false;

		if (sentence.contains(start) && sentence.contains(end)) {
			present = true;
		}
		return present;
	}

	private boolean checkCharsPosition(String sentence, char startChar,
			char endChar) {
		boolean rightOrder = false;

		if (sentence.indexOf(startChar) <= sentence.lastIndexOf(endChar)) {
			rightOrder = true;
		}
		return rightOrder;
	}
}
