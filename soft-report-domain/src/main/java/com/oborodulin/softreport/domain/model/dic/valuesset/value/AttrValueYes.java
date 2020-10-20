package com.oborodulin.softreport.domain.model.dic.valuesset.value;

/**
 * Класс описывает логическое {@code true} атрибута значения
 * 
 * @author Oleg Borodulin
 * @version 1.0
 */
public class AttrValueYes {
	private final String val = "Y";

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		String val = (String) o;
		return this.val.equals(val);
	}

	@Override
	public String toString() {
		return this.val;
	}
}
