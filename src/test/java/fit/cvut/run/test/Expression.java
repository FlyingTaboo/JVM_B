package fit.cvut.run.test;

public class Expression {
	char name;
	Expression left;
	Expression right;
	int operator;

	public boolean evaluate(boolean[] instance) {
		if (name > 0) {
			return instance[name - 97];
		}
		if (operator == Sat.NOT) {
			return !left.evaluate(instance);
		}
		if (operator == Sat.AND) {
			return left.evaluate(instance) && right.evaluate(instance);
		}
		if (operator == Sat.OR) {
			return left.evaluate(instance) || right.evaluate(instance);
		}
		return false;
	}

	@Override
	public String toString() {
		if (name > 0) {
			return Character.toString(name);
		}
		if (operator == Sat.NOT) {
			return "!" + left.toString();
		}
		return "(" + left.toString() + (operator == Sat.AND ? " & " : " | ") + right.toString() + ")";
	}
}
