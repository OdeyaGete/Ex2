package assignments.ex2;// Add your documentation below:

public class CellEntry  implements Index2D {
    private String cellNum;


    //the string length can be only 2 or 3 cause x can be only one letter (AA/AC... is invalid)
    // and y can be 1 or 2 digits(0-99)
    @Override
    public boolean isValid() {
        return cellNum.matches("[A-Z][0-9]+") && (cellNum.length() == 2 || cellNum.length() == 3) ;
    }

    @Override
    public int getX() {
        if(!isValid()){
            return Ex2Utils.ERR;
        }
        char letter = cellNum.charAt(0);
        return letter - 'A';

    }

    @Override
    public int getY() {
        if(!isValid()){
            return Ex2Utils.ERR;
        }
        String digit = cellNum.substring(1);

        return Integer.parseInt(digit);
    }

    @Override
    public String toString() {

      return "CellEntry [x=" + getX() + ", y=" + getY() + "]";
    }



}
