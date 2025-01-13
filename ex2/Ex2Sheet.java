package assignments.ex2;
import java.io.IOException;
// Add your documentation below:

public class Ex2Sheet implements Sheet {
    private Cell[][] table;
    private int _width, _height;


    public Ex2Sheet(int x, int y) {
        this._width = x;
        this._height = y;
        table = new SCell[_width][_height];
        for(int i=0;i<x;i++) {
            for(int j=0;j<y;j++) {
                table[i][j] = new SCell(Ex2Utils.EMPTY_CELL);
            }
        }
        eval();

    }

    public Ex2Sheet() {

        this(Ex2Utils.WIDTH, Ex2Utils.HEIGHT);
    }

    // returning a string of the value of the cell
    @Override
    public String value(int x, int y) {
        if(!isIn(x,y)) {
            return "Invalid cell coordinates";
        }
        String ans  = eval(x,y);
        if(ans.isEmpty()) {
            return Ex2Utils.EMPTY_CELL;
        }

        return ans;
    }

    @Override
    public Cell get(int x, int y) {
        if (!isIn(x,y)) {
            throw new IllegalArgumentException("Invalid cell coordinates.");
        }
        if(table[x][y]==null) {
            return null;
        }
        return table[x][y];
    }

    /**
     in order to recognize the string cords as width and height I'll use the xCell and yCell methods
     int x= xCell(cords)
     int y= yCell(cords)
     once we've got the x and y we can just return get[x][y]
     */

    @Override
    public Cell get(String cords) {
        if (cords.isEmpty() || !isValid(cords)) {
            return null;
        }
        cords = cords.toUpperCase();
        int x= xCell(cords);
        int y= yCell(cords);
        Cell c = get(x,y);

        return c;

    }


    @Override
    public int width() {

        return table.length;
    }
    @Override
    public int height() {

        return table[0].length;
    }
    @Override
    public void set(int x, int y, String s) {
        if(!isIn(x,y)) {
            throw new IllegalArgumentException("Invalid cell coordinates.");
        }
        Cell c = new SCell(s);
        table[x][y] = c;
        c.setData(s);
    }
    @Override
    public void eval() {
        Double[][] dd = new Double[width()][height()];
        for(int i=0;i<dd.length;i++) {
            for(int j=0;j<dd[i].length;j++) {
                String evalCell = eval(i,j);
                if(evalCell.isEmpty()) {
                    dd[i][j] = null;
                } else if (!isText(evalCell)) {
                    dd[i][j] = Double.parseDouble(evalCell);

                } else {
                   continue;
                }
            }
        }
    }


    @Override
    public boolean isIn(int xx, int yy) {
        return xx >= 0 && xx < _width && yy >= 0 && yy < _height;
    }



    @Override
    public int[][] depth() {
        int[][] ans = new int[width()][height()];
        for(int i=0;i<width();i++) {
            for(int j=0;j<height();j++) {
                int counter=0;
                Cell c = get(i,j);
                int cellType = c.getType();
                String valueCell = value(i,j);
               while (valueCell.matches(".*[a-z].*") && cellType == 3){
                   counter++;

               }
               ans[i][j]=counter;
            }
        }
        return ans;
    }


    @Override
    public void load(String fileName) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))){
            String line;
            while ((line = reader.readLine()) != null) {
            }
        } catch (IOException E) {
            E.printStackTrace();
        }
    }

    @Override
    public void save(String fileName) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            // Write the header line
            writer.write("I2CS ArielU: SpreadSheet (Ex2) assignment - this line should be ignored in the load method");
            writer.newLine();

            // Iterate through all cells and save only non-empty ones
            for (int i = 0; i < width(); i++) {
                for (int j = 0; j < height(); j++) {
                    String cellValue = value(i, j);
                    if (!cellValue.equals(Ex2Utils.EMPTY_CELL)) {  // Save non-empty cells only
                        writer.write(i + "," + j + "," + cellValue);
                        writer.newLine();
                    }
                }
            }
        }
    }
    /** we'll use the @value method above to get the string form of this cell
     * then, in order to compute we can use the computeForm method we've created on Cell
     * **remember that the @computeForm method's returning a double ans NOT a String
     * so we also need to convert the outcome into string
    */
    @Override
    public String eval(int x, int y) {
        if(!isIn(x,y)) {
            throw new IllegalArgumentException("Invalid cell coordinates.");
        }
        Cell c = get(x,y);
        String cellValue= c.toString();
        int cellType = c.getType();
        String ans = cellValue;

        if(c==null || cellValue.isEmpty()) {
            return Ex2Utils.EMPTY_CELL;
        }
        if(cellType== Ex2Utils.FORM) {
            Double res = computeForm(cellValue);
            ans = " " + res;
        }

        return ans;
    }

    private boolean isValid(String cords) {
        return cords.matches("[A-Z][0-9]+") && (cords.length() == 2 || cords.length() == 3) ;
    }


    private int depth(int x, int y, Cell c) {
        int counter =0;
        int letterIndex=-1;
        String cellVal = value(x,y);
        while (cellVal.matches(".*[a-z].*")){
            for(int j=0;j<cellVal.length();j++) {
                if("A-Z".indexOf(cellVal.charAt(j))!=-1) {
                    letterIndex=j;

                }
            }
            counter++;
        }
        return counter;
    }






    //computes the x coordinate of the string
    public int xCell(String c){
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
    // computes the y coordinate of the string
    public int yCell(String c){
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


    public double computeForm(String form) {
        if(form.charAt(0) == '='){
            form = form.substring(1);
        }
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

                    int x= xCell(letterPart);
                    int y= yCell(letterPart);

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


    private static String computeByOrder(String form, String operators) {
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
    private static boolean isNumber (String text){
        try {
            Double.parseDouble(text);
            return true;
        }
        catch (NumberFormatException e){
            return false;
        }
    }
    public static boolean isForm(String text){
        if(text ==null || text.length()<2 || text.charAt(0) != '=' ){
            return false;
        }
        return true;
    }
    public boolean isText(String text){

        return !isNumber(text) && !isForm(text) ;
    }

}
