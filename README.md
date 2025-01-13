
isNumber: Checks if a string can be converted to a number by trying to parse it.if it's successful, returns true, else, returns false.
isText: Determines if a string is a regular text (not a number or formula) by using isNumber and isForm to confirm it.
isForm: Checks if a string starts with '=' and has at least two characters, identifying it as a formula.
value: Returns values based on given (x, y) coordinates.
computeForm: Evaluates a formula- Resolves references (in case of another cell refrence) by using value.
Handles parentheses and nested expressions.
performs arithmetic operations (*, /, +, -) in order of "Order account operations".
Recursively computes until the result is a single number.
computeByOrder: A helper function Processes specific mathematical operations in a formula:
Finds operators (*, /, +, -).
Extracts operands, computes the result, and replaces the operation in the formula.
repeats until no operators remain.
get(x, y): Returns the cell at (x, y) in the table, throws an exception if the coordinates are out of bounds.
set(x, y, c): Sets the cell c at (x, y) in the grid, throws an exception if the coordinates are invalid.


