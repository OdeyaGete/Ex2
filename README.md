
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

public Cell get(int x, int y) {
        if (x < 0 || x >= _width || y < 0 || y >= _height) {
            throw new IllegalArgumentException("Invalid cell coordinates.");
        }
        return grid[x][y];
    }

    public void set(int x, int y, Cell c) {
    if (x < 0 || x >= _width || y < 0 || y >= _height) {
    throw new IllegalArgumentException("Invalid cell coordinates.");
    }
      grid[x][y] = c;
    }



    //A=0....Z=25, AA= ERR
    public static int xCell(String c){
        if(c == null  || c.isEmpty()) {
            return -1;
        }
        c= c.toUpperCase();
        int index = 0;
        int letterCount=0;
        for(int i=0; i<c.length(); i++) {
            char ch = c.charAt(i);
            if(ch>= 'A'&& ch<= 'Z') {
                index = index*26+(ch-'A'+1);
                letterCount++;
            }
            if(letterCount >1) {
                return -1;
            }
            else{
                continue;
            }
        }
        return index-1;
    }
    public static int yCell(String c){
        if(c == null  || c.isEmpty()) {
            return -1;
        }
        c=c.toUpperCase();
        for(int i=0; i<c.length(); i++) {
            char ch = c.charAt(i);
            if(ch>= 'A' && ch<= 'Z') {
                c= c.replace(ch, '0');
            }
            else {
                continue;
            }
        }
        return Integer.parseInt(c);
    }
    public  String eval(int x, int y) {
        if (x < 0 || x >= _width || y < 0 || y >= _height) {
            throw new IllegalArgumentException("Invalid cell coordinates.");
        }
        Cell cell = grid[x][y];

        if(cell == null) {
            return " ";
        }
        String input = cell.toString();
        if (Cell.isNumber(input)) {
            return input;
        } else if (Cell.isText(input)) {
            return input;
        } else if (Cell.isForm(input)) {
            try{
                return String.valueOf(Cell.computeForm(input));
            }
            catch (Exception e) {
                return "ERR";
            }
        }
        return "ERR";

    }
 public static boolean isNumber (String text){
        try {
            Double.parseDouble(text);
            return true;
        }
        catch (NumberFormatException e){
            return false;
        }
    }

    public static boolean isText(String text){

        return !isNumber(text) && !isForm(text);
    }

    public static boolean isForm(String text){
        if(text ==null || text.length()<2 || text.charAt(0) != '=' ){
            return false;
        }
        return true;
    }
    public static String value(int x, int y){
        if(x==0 && y==1){
            return "5";
        }
        else if(x==1 && y==2){
            return "A1+C5";
        }
        return "55";
    }

    public static double computeForm(String form) {
        form = form.trim();
        form = form.toUpperCase();
        System.out.println("currentForm " +form);
        if (form.matches(".*[A-Z].*")) {
            for(int i=0; i<form.length(); i++) {
                char ch = form.charAt(i);
                if(Character.isLetter(ch)) {
                    int letterIndex=i;
                    int digitIndex = letterIndex+1;

                    if(digitIndex+1<form.length()) {
                        if(Character.isDigit(form.charAt(digitIndex+1))) {
                            digitIndex++;
                        }
                    }
                    String letterPart = " ";
                    if(digitIndex +1 ==form.length()) {
                         letterPart = form.substring(letterIndex);
                    }
                    else if(digitIndex+1 <form.length()) {
                         letterPart = form.substring(letterIndex,digitIndex+1);
                    }

                    int x= SpreadSheet.xCell(letterPart);
                    int y= SpreadSheet.yCell(letterPart);
                    String innerCell = value(x, y);
                    Double innerCell1 = computeForm(innerCell);

                    form = form.replace(letterPart, String.valueOf(innerCell1));

                }
            }
        }
        System.out.println("first time form: " + form);
        form = form.trim();
        while (form.contains("(") || form.contains(")")) {
            int start = form.lastIndexOf("(") + 1;
            int end = form.indexOf(")", start);
            if (start > 0 && end > start) {
                String innerForm = form.substring(start, end);
                System.out.println("Inner form: " + innerForm);
                Double innerRes = computeForm(innerForm);
                form = form.replace("(" + innerForm + ")", String.valueOf(innerRes));
                System.out.println("form is: " + form);
            }
        }
        form = computeByOrder(form, "*/");
        System.out.println("form after multi is: " + form);

        form = computeByOrder(form, "+-");
        System.out.println("form after addsub is: " + form);

        if(!isNumber(form)) {
            computeForm(form);
        }

        return Double.parseDouble(form);

    }



    public static String computeByOrder(String form, String operators) {
        char op = ' ';
        int opIndx = -1;
        for (int i = 0; i < form.length(); i++) {
            op = form.charAt(i);
            if (operators.indexOf(op) != -1) {
                int leftIndex = i - 1;
                int rightIndex = i + 1;
                System.out.println("leftIndex: " + leftIndex);
                System.out.println("rightIndex: " + rightIndex);


                while (leftIndex >= 0 && (Character.isDigit(form.charAt(leftIndex)) || form.charAt(leftIndex) == '.')) {
                    leftIndex--;
                }
                leftIndex++;

                while (rightIndex < form.length() && (Character.isDigit(form.charAt(rightIndex)) || form.charAt(rightIndex) == '.')) {
                    rightIndex++;
                }

                System.out.println("leftIndex: " + leftIndex);
                System.out.println("rightIndex: " + rightIndex);
                String leftPart = form.substring(leftIndex, i);
                String rightPart = form.substring(i + 1, rightIndex);
                double leftOperand = Double.parseDouble(leftPart);
                double rightOperand = Double.parseDouble(rightPart);
                double result=1;
                if (op == '*') {
                    result= leftOperand * rightOperand;
                }
                if (op == '/') {
                    result= leftOperand / rightOperand;
                }
                if (op == '+') {
                    result= leftOperand + rightOperand;
                }
                if (op == '-') {
                    result= leftOperand - rightOperand;
                }
                String operation = form.substring(leftIndex, rightIndex);
                form = form.replace(operation, String.valueOf(result));

                i=-1;

            }
        }
        return form;
    }
